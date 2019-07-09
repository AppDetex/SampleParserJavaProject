(ns clojure-prototype.core
  (:require [cheshire.core :as cheshire])
  (:import [org.jsoup Jsoup]
           [java.net URL]))
           

(def ^:private timeout-millis 2000)

(defn- parse-url [url-string]
  (Jsoup/parse (URL. url-string) 2000))

(defn- extract [document mappings]
  (reduce (fn [acc [k extractor]]
            (assoc acc k (extractor document)))
          {}
          mappings))

(defn- get-publisher [doc]
  (let [app-id (-> doc
                   (.select "meta[name=appstore:bundle_id]")
                   (.attr "content"))
        ;;the root panel that includes the "Similar" bit doesn't have jsshadow, but all the app-specific ones seem to
        app-panel-selector (str "c-wiz[jsshadow][data-p*=" app-id "]")]
    (-> doc
        ;;Other developer links exist on page, notably in the "Similar" panel
        ;;Sometimes the link is just dev rather than developer
        (.select (str app-panel-selector " a[href*=/dev]"))
        first
        .text)))
    
(def ^:private default-mappings
  {:title (fn [doc] (.text (.select doc "h1[itemprop=name]")))
   :description (fn [doc]
                  (-> doc
                      (.select "meta[name=description]")
                      (.attr "content")
                      (clojure.string/split #"\r|\n")
                      first))
   :publisher get-publisher
   :rating (fn [doc] (-> doc
                         (.select "[aria-label^=Rated]:not([role=img])")
                         .text
                         Double/parseDouble))
   :price (fn [doc] (-> doc
                        (.select "button[aria-label$=Buy]")
                        (.text)
                        (clojure.string/split #" ")
                        first))})

(defn- ld-json-extract [document]
  (-> document
      (.select "script[type=application/ld+json]")
      .html
      cheshire/decode))

(defn- ld-json-mapping [item]
  {:title (get item "name")
   :description (-> (get item "description")
                    (clojure.string/split #"\r|\n")
                    first)
   :publisher (get-in item ["author" "name"])
   :rating (get-in item ["aggregateRating" "ratingValue"])
   :price (->> (get item "offers")
               (map #(get % "price"))
               first)})
  
   
(comment
  (def minecraft-document (parse-url "https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US"))
  
  (extract minecraft-document default-mappings)

  (def test-urls ["https://play.google.com/store/apps/details?id=com.lego.city.my_city2&hl=en-US"
                  "https://play.google.com/store/apps/details?id=com.roblox.client&hl=en-US"
                  "https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US"
                  "https://play.google.com/store/apps/details?id=com.Slack"])
  (clojure.pprint/pprint
   (map #(-> %
             parse-url
             (extract default-mappings))
        test-urls))

  (clojure.pprint/pprint
   (map #(-> %
             parse-url
             ld-json-extract
             ld-json-mapping)
        test-urls))
  
  (ld-json-extract minecraft-document))

  
