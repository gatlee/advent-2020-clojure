(ns advent.06
  (:require [clojure.string :as s]
            [clojure.set :as set]))


(def data
  (-> (slurp "input.txt")
      (s/split #"\n\n")))

(defn count-unique
  [s]
  (count (set (s/replace s "\n" ""))))


(defn count-intersection
  [s]
  (count (apply set/intersection (map set (s/split s #"\n")))))

(defn solve-p1
  []
  (apply +  (map count-unique data)))

(defn solve-p2
  []
  (apply + (map count-intersection data)))


(solve-p1)
(solve-p2)
