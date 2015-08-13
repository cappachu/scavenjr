(ns scavenjr.parse
  (:require [scavenjr.geometry :as geom]))



(defn- blank? [str]
  (every? #(Character/isWhitespace %) str))

(defn- visible-textnode? [tn]
  (let [dimensions (vals (select-keys tn [:cheight :cwidth]))]
    (some #(> % 0) dimensions)))

(defn- massage-textnodes [textnodes]
  (let [nonblank (filter #((complement blank?) (:text %)) textnodes)
        ;; TODO use update-in?
        trimmed (map #(assoc % :text (clojure.string/trim (:text %)))
                     nonblank)
        visible (filter visible-textnode? trimmed)
        sorted (sort-by #(vector (:ctop %) (:cleft %))
                        visible)]
    sorted))

(defn- textnode-style-signature
  "returns values corresponding to styles of a textnode"
  [tn]
  (select-keys tn
               [:color
                :backgroundColor
                :fontFamily
                :fontSize
                :fontStyle
                :fontWeight
                :textAlign]))


(defn- style-siblings?
  "returns true if two textnodes have the same styles
  and are left-aligned"
  [tn1 tn2]
  (and (= (textnode-style-signature tn1)
          (textnode-style-signature tn2))
       ;; TODO: support languages that read right to left?
       (or (geom/left-aligned? tn1 tn2) 
           false)
       ))

;; not tail recursive
(defn- cluster [pred]
  ;; TODO update doc string
  "returns a function appropriate for use with reduce"
  (fn reducer-fn [colls elem]
    (if (empty? colls)
      (conj colls [elem])
      (let [coll (first colls)]
        ;; TODO: modify predicate to take the whole collection
        (if (pred (first coll) elem) ;; coll
          (cons (conj coll elem) (rest colls))
          (cons coll (reducer-fn (rest colls) elem)))))))


(defn- parse-int [s]
  (Integer. (re-find #"\d+" s)))


(defn headernodes-from-textnodes
  ;; TODO break up function
  [textnodes]
  (let [clustered-by-style (reduce (cluster style-siblings?) [] textnodes)
        ;; TODO remove magic num
        large-clusters (filter #(> (count %) 5) clustered-by-style)
        ;;ratio of span of cluster to span of largest candidate cluster
        max-height (apply max (map #(geom/height (geom/span %)) large-clusters))
        ;; TODO remove magic ratio
        height-spanning-clusters (filter #(> (/ (geom/height (geom/span %))
                                                max-height)
                                             0.80)
                                         large-clusters)
        ;; TODO remove magic num
        consistent-internode-distances (filter #(< (geom/internode-distance-std %) 80)
                                               height-spanning-clusters)
        ;; TODO use computed pixel font sizes to normalize em, pixels, points
        font-size-sorted  (sort-by
                           #(vector
                             (- (parse-int (:fontSize (first %))))
                             (:left (first %))
                             (:top (first %)))
                           consistent-internode-distances)
        header-nodes (first font-size-sorted)
        ]
    header-nodes
  ))


(defn- textnodes-between-textnodes
  [tn1 tn2 textnodes]
  ;; TODO use ctop instead of top?
  (let [tn1top (:top tn1)
        tn2top (:top tn2)
        padding (* 0.2 (:height tn2))]
    (filter #(and (>= (:top %) tn1top)
                  (< (:bottom %) tn2top))
            textnodes)))

(defn- textnodes-for-headers
  "groups textnodes between headers"
  ;; TODO return map in which headers are keys
  [headernodes textnodes]
  (map (fn [[tn1 tn2]]
         (textnodes-between-textnodes tn1 tn2 textnodes))
       (partition 2 1 headernodes)))



(defn data-from-textnodes
  [textnodes]
  (let [massaged-textnodes (massage-textnodes textnodes)
        headernodes (headernodes-from-textnodes massaged-textnodes)
        textnodes-grouped-by-headers (textnodes-for-headers headernodes massaged-textnodes)]
    textnodes-grouped-by-headers
    ;;headernodes
    ))

