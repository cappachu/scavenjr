(ns scavenjr.core
  (:gen-class)
  (:require [clj-webdriver.taxi :as taxi]
            [scavenjr.browser :as browser]
            [scavenjr.parse :as parse]))

(defn data-from-url
  "returns a relational data set (set of maps)
  corresponding to the most salient data at url"
  [url]
  (browser/load-page url)
  (let [textnodes (browser/get-textnodes)]
    (parse/data-from-textnodes textnodes))
  )

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (def url "https://news.ycombinator.com/")
  (browser/start-browser)

  (println (map :text (first (data-from-url url))))
  (print "done")

  )



(-main)


