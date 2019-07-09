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

(def ^:private default-mappings
  {:title (fn [doc] (.text (.select doc "h1[itemprop=name]")))
   :description (fn [doc]
                  (-> doc
                      (.select "meta[name=description]")
                      (.attr "content")
                      (clojure.string/split #"\r|\n")
                      first))
   ;;Other developer links exist on page, notably in the "Similar" panel
   :publisher (fn [doc] (.text (.first (.select doc "a[href*=/developer]"))))
   :rating (fn [doc] (-> minecraft-document
                         (.select "[aria-label^=Rated]:not([role=img])")
                         .text
                         Double/parseDouble))
   :price (fn [doc] (-> minecraft-document
                        (.select "button[aria-label$=Buy]")
                        (.text)
                        (clojure.string/split #" ")
                        first))})
   
(comment
  (def minecraft-document (parse-url "https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&hl=en-US"))
  
  (extract minecraft-document default-mappings))

  
  
