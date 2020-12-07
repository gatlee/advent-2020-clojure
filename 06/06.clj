(ns advent.06
  (:require [clojure.string :as s]))


(def data
  (-> (slurp "input.txt")
      (s/split #"\n\n")))

(defn count-unique
  [s]
  (count (set (s/replace s "\n" ""))))

(defn solve
  []
  (apply +  (map count-unique data)))

(solve)
