(ns clojure-prototype.core
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
   
(comment
  (def minecraft-document (parse-url "https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US"))
  
  (extract minecraft-document default-mappings)

  (let [test-urls ["https://play.google.com/store/apps/details?id=com.lego.city.my_city2&hl=en-US"
                   "https://play.google.com/store/apps/details?id=com.roblox.client&hl=en-US"]]
    (map #(-> %
              parse-url
              (extract default-mappings))
         test-urls)))
         
  
  
  
