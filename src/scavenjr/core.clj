(ns scavenjr.core
  (:gen-class)
  (:require [clj-webdriver.taxi :as taxi]
            [scavenjr.browser :as browser]
            [scavenjr.parse :as parse]))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]

  (def url "https://news.ycombinator.com/")
  (browser/start-browser)
  (data-for-url url)
  (print "done")
  )



(defn data-for-url
  "returns a relational data set (set of maps)
  corresponding to the most salient data at url"
  [url]
  (browser/load-page url)
  (browser/get-textnodes)
  )

(-main)


