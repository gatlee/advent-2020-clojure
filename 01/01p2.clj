(ns advent.01
  [:require [clojure.string :as s]]
  (:require [clojure.java.io :as io]))


(def nums
  (->>
   (slurp "input.txt")
   (s/split-lines)
   (mapv #(Integer/parseInt %))))


(def sumGoal 2020)

(defn findSum
  [[head & rest] aggr]
  (let [sum (reduce + aggr)]
    (cond
      (nil? head)     nil
      (= sum sumGoal) aggr
      (> sum sumGoal) nil
      (< sum sumGoal) (or (findSum rest (conj aggr head))
                          (findSum rest aggr)))))


(defn solve-p2
  []
  (reduce * (findSum nums [])))

(solve-p2)
