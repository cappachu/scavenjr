(ns scavenjr.drawing
  (:require [clj-webdriver.taxi :as taxi]))


;; TODO remove repeated slurping - memoize?
(defn prepare-canvas []
  (let [jsscript (slurp "src/scavenjr/prepare_canvas.js")]
    (taxi/execute-script jsscript)))


(defn clear-canvas []
  (let [jsscript (slurp "src/scavenjr/clear_canvas.js")]
    (taxi/execute-script jsscript)))


(defn draw-rect [has-rect color];;[x y width height :as rect]]
  (let [jsscript (slurp "src/scavenjr/draw_rect.js")
        left   (:left has-rect)
        top    (:top has-rect)
        right  (:right has-rect)
        bottom (:bottom has-rect)
        args [left top right bottom color]]
    ;; TODO is use of *driver* is appropriate?
    (taxi/execute-script taxi/*driver* jsscript args)))

(defn draw-textnode [tn color]
  (draw-rect tn color))


