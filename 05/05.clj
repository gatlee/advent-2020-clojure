(ns boarding
  (:require [clojure.string :as s]
            [clojure.set :as set]))


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

(defn reducePossibilities
  [ranges letter]
  (case letter
    \F (assoc ranges :numRows (/ (:numRows ranges) 2))
    \B (assoc ranges :rowMin (+ (:rowMin ranges) (/ (:numRows ranges) 2)) :numRows (/ (:numRows ranges) 2))
    \L (assoc ranges :numCol (/ (:numCol ranges) 2))
    \R (assoc ranges :colMin (+ (:colMin ranges) (/ (:numCol ranges) 2)) :numCol (/ (:numCol ranges) 2))))


(defn parseSeatSpec
  ([spec]
   (parseSeatSpec spec initialRanges))
  ([spec ranges]
   (if (<= (count spec) 0)
     ranges
     (parseSeatSpec
      (rest spec)
      (reducePossibilities ranges (first spec))))))

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


;;Solve p2

(def maxID (+ (* 127 8) 7))
(def seats
  (map #(-> %
            parseSeatSpec
            getSeatID)
       data))

(def missingSeats
  (set/difference (set (range 0 1023)) (set seats)))

(defn filter-front
  [missing]
  (let [sorted (sort-by identity missing)] ;; Sort list
    (loop [prev -1
           currList sorted]
      (if (= (inc prev) (first currList))
        (recur (first currList) (rest currList))
        (first currList)))))

(filter-front missingSeats)
