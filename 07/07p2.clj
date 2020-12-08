(ns luggage
  (:require [clojure.string :as s]))
(def line "light red bags contain 1 bright white bag, 2 muted yellow bags.")
(def empty "faded blue bags contain no other bags.")


(def data
  (-> (slurp "input.txt")
      (s/replace #"\sbags" "")
      (s/replace #"\sbag" "")
      (s/split-lines)))

(defn parseBag
  "'1 bright white' -> {'bright white' 1}"
  [line]
  (let [[_ count name] (re-find #"(\d+)([a-zA-Z\s]+)" line)
        trimmed-name (s/trim name)]
    {trimmed-name (Integer/parseInt count)}))


(defn add-entry
  [dict line]
  (let [[outer & innerBags] (s/split line #"(\.|, |\scontain\s)")] ; {a {b 2})}}
    (if (s/ends-with? line "no other.")
      (assoc dict outer [])
      (assoc dict outer (apply merge (map parseBag innerBags))))))

(add-entry {} (first data))

(defn generate-map
  [lines]
  (loop [line lines
         result {}]
    (if (empty? line)
      result
      (recur (rest line) (add-entry result (first line))))))


(defn count-bags
  "counts number of bags recursively including self"
  [bag bagMap]
  (let [innerBags (get bagMap bag)]
    (apply + 1 (map #(* (second %) (count-bags (first %) bagMap)) innerBags))))

(def memo-count-bags (memoize count-bags))
(def bagMap (generate-map data))

(dec (count-bags "shiny gold" bagMap))
