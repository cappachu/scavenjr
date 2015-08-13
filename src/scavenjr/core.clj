(ns scavenjr.core
  (:gen-class)
  (:require [clj-webdriver.taxi :as taxi]
            [scavenjr.browser :as browser]
            [scavenjr.parse :as parse]
            [scavenjr.drawing :as draw]
            [scavenjr.geometry :as geom]))

(defn data-from-url
  "returns a relational data set (set of maps)
  corresponding to the most salient data at url"
  [url]
  ;; TODO wait until page loads
  (browser/load-page url)
  (let [textnodes (browser/get-textnodes)]
    (parse/data-from-textnodes textnodes))
  )

(def urls {
           :hacker-news "https://news.ycombinator.com/"
           :menu-pages "http://www.menupages.com/restaurants/10th-avenue-pizza/menu"
           :amazon "http://www.amazon.com/s/ref=nb_sb_noss_2?url=search-alias%3Daps&field-keywords=lisp"
           :zappos "http://www.zappos.com/men-shoes~1y"
           :craigslist  "https://newyork.craigslist.org/search/aap"
           :peru-movies  "http://carteleraperu.com"
           :presidents "http://presidential-candidates.insidegov.com"
           :nycdata "https://data.cityofnewyork.us/City-Government/NYC-Jobs/kpav-sd4t"
           :secforms "http://www.sec.gov/forms"
})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]

  (browser/start-browser)
  ;;(browser/load-page (urls :secforms))

  (let [url (urls :hacker-news)
        data (data-from-url url)]
    (println (map :text (first data)))
    ;; TODO prepare-canvas should be called once - singleton pattern?
    (draw/prepare-canvas)
    (doseq [header data]
      ;;(println  (map :text header))
      ;; TODO: fix bug with span (extends to left edge)
      ;;(draw/draw-textnode (geom/span header) "blue"))
      ;;(draw/draw-textnode header "red"))
      (doseq [tn header]
        (draw/draw-textnode tn "blue")))


    (print "done")
    )
  )


(-main)


