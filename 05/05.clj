(ns boarding
  (:require [clojure.string :as s]))


(defn raw->lines
  [str]
  (s/split str #"\n"))


(def data
  (-> (slurp "input.txt")
      raw->lines))


;;Example

(def initialRanges {
                  :rowMin 0
                  :numRows 128
                  :colMin 0
                  :numCol 8})

(defn parseSeatSpec
  ([spec]
   (parseSeatSpec spec initialRanges))
  ([spec ranges]
   (if (<= (count spec) 0)
     ranges
     (parseSeatSpec
      (rest spec)
      (reducePossibilities ranges (first spec))))))

(defn reducePossibilities
  [ranges letter]
  (case letter
    \F (assoc ranges :numRows (/ (:numRows ranges) 2))
    \B (assoc ranges :rowMin (+ (:rowMin ranges) (/ (:numRows ranges) 2)) :numRows (/ (:numRows ranges) 2))
    \L (assoc ranges :numCol (/ (:numCol ranges) 2))
    \R (assoc ranges :colMin (+ (:colMin ranges) (/ (:numCol ranges) 2)) :numCol (/ (:numCol ranges) 2))))

(defn getSeatID
  [ranges]
  (let [row (:rowMin ranges)
        col (:colMin ranges)]
    (+ (* row 8) col)))

(defn solvep1
  []
  (apply max
         (map #(-> %
                   parseSeatSpec
                   getSeatID)
              data)))
