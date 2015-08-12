(ns scavenjr.geometry
  (:require [scavenjr.stats :as stats]))


(defn height [{bottom :bottom
               top :top}]
  (- bottom top))


(defn span
  [textnodes]
  (let [left   (apply min (map :left   textnodes))
        top    (apply min (map :top    textnodes))
        right  (apply max (map :right  textnodes))
        bottom (apply max (map :bottom textnodes))]
    {:left left :top top :right right :bottom bottom}))


(defn left-aligned?
  "Determines whether two text nodes are left aligned"
  [{tn1_left :left :as tn1}
   {tn2_left :left :as tn2}]
  (let [tn1_height (:height tn1)
        pixel-horiz-dist (* 0.1 tn1_height)]
    (< (Math/abs (- tn1_left tn2_left))
       pixel-horiz-dist)))


(defn internode-distances
  "given a cluster (coll) of textnodes returns distances of consectutive
  textnodes after sorting them top to bottom"
  [cluster]
  (let [sorted (sort-by :top cluster)
        pairs (partition 2 1 sorted)
        vert-dist (fn [[{top1 :top}
                        {top2 :top}]]
                    (- top2 top1))
        distances (map vert-dist pairs)]
    distances
    ))


(defn internode-distance-std
  "returns standard deviation of internode distance of textnodes in cluster"
  [cluster]
  (stats/standard-deviation (internode-distances cluster)))


