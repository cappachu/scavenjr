(ns scavenjr.browser
  (:require [clj-webdriver.taxi :as taxi]
            [scavenjr.parse :as parse]))


(defn start-browser
  "Starts a browser instance"
  []
  (taxi/set-driver! {:browser :chrome}))


(defn load-page
  ;; TODO: indication of when page is completely loaded
  "Loads a page in the browser"
  [url]
  (taxi/get-url url))


(defn get-textnodes
  ;; TODO: ignore script, meta, etc.
  ;; TODO: modify script path
  ;; TODO: enumerate expected keys
  "returns visible text nodes from DOM tree"
  []
  (let [jsscript (slurp "src/scavenjr/get_textnodes.js")
        jsmap_to_map (fn [jsmap] (into {} (for [[k v] jsmap] [(keyword k) v])))
        jstextnodes (taxi/execute-script jsscript)
        textnodes (map jsmap_to_map jstextnodes)]
    textnodes))



