(ns luggage
  (:require [clojure.string :as s]))


(def data
  (-> (slurp "input.txt")
      (s/replace #"\sbags" "")
      (s/replace #"\sbag" "")
      (s/split-lines)))

(defn parse-bag
  "'1 bright white' -> {'bright white' 1}"
  [line]
  (let [[_ count name] (re-find #"(\d+)([a-zA-Z\s]+)" line)
        trimmed-name (s/trim name)]
    {trimmed-name (Integer/parseInt count)}))

(defn parse-line
  "'shiny gold contains 2 dark red' -> {'shiny gold' {'dark red' 2}}"
  [line]

  (let [[outer & innerBags] (s/split line #"(\.|, |\scontain\s)")] ;; {"bag" ["baga 1" "bagb 2"]}
        (if (s/ends-with? line "no other.")
          {outer {}}
          {outer (apply merge (map parse-bag innerBags))}))) ;; {"bag" {"baga" 1 "bagb 2"}}

(defn generate-map
  [lines]
  (loop [line lines
         result {}]
    (if (empty? line)
      result
      (recur (rest line) (merge result (parse-line (first line)))))))

(defn count-bags
  "counts number of bags recursively including self"
  [bag bagMap]
  (let [innerBags (get bagMap bag)]
    (apply + 1 (map #(* (second %) (count-bags (first %) bagMap)) innerBags))))

(def memo-count-bags (memoize count-bags))

(def bagMap (generate-map data))

(def p2-solution
  (dec (count-bags "shiny gold" bagMap)))
