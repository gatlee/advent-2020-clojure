(ns luggage
  (:require [clojure.string :as s]))

(def data
  (-> (slurp "input.txt")
      (s/replace #"\sbags" "")
      (s/replace #"\sbag" "")
      (s/replace #"\d " "")
      (s/split-lines)))

(defn add-entry
  [dict line]
  (let [[outer & inner] (s/split line #"(\.|, |\scontain\s)")]
    (if (s/ends-with? line "no other.")
      (assoc dict outer [])
      (assoc dict outer inner))))


(defn generate-map
  [lines]
  (loop [line lines
         result {}]
    (if (empty? line)
      result
      (recur (rest line) (add-entry result (first line))))))



(defn contains-gold?
  [bag bagMap]
  (let [innerBags (get bagMap bag)]
    (cond
      (some #(= "shiny gold" %) innerBags) true
      (some #(contains-gold? % bagMap) innerBags) true
      :else false)))


(def bagMap (generate-map data))

(def memo-contains-gold? (memoize contains-gold?))

(defn solve-p1
  []
  (->> (map #(memo-contains-gold? % bagMap) (keys bagMap))
       (filter identity)
       count))


(solve-p1)
