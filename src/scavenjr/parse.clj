(ns scavenjr.parse)



(defn -blank? [str]
  (every? #(Character/isWhitespace %) str))

(defn -visible-textnode? [tn]
  (let [dimensions (vals (select-keys tn [:cheight :cwidth]))]
    (some #(> % 0) dimensions)))

(defn massage-text-nodes [textnodes]
  (let [nonblank-textnodes (filter #((complement -blank?) (:text %)) textnodes)
        trimmed-textnodes (map #(assoc % :text (clojure.string/trim (:text %)))
                               nonblank-textnodes)
        visible-textnodes (filter -visible-textnode? trimmed-textnodes)
        sorted-textnodes (sort-by #(vector (:ctop %) (:cleft %))
                                  visible-textnodes)]
    sorted-textnodes))
