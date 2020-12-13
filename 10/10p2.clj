(ns adapter
  (:require [clojure.string :as s]))

:input [1 2 3]
:output [0 1 2 3 6]
(defn add-built-in
  "Adds 0-jolt adapter and +3 built-in outlet "
  [joltages]
  (cons 0 (conj joltages (+ 3 (apply max joltages)))))


(def data
  (->> (slurp "input.txt")
       s/split-lines
       (mapv #(Integer/parseInt %))
       add-built-in
       sort))


(defn attachable?
  [female male]
  (<=  (- male female) 3))

:input [10 11 12] 0
:output [1 2]
(defn get-attachable
  "Get index of consequent adapters in list that can be attached to nth adapter"
  [jolts n]
  (for [i (range (inc n) (count jolts))
        :while (attachable? (nth jolts n) (nth jolts i))] i))


(def get-configs
  (memoize (fn [jolts n]
            (if (= n (dec (count jolts)))
              1
              (let [attachable (get-attachable jolts n)
                    permutations (map #(get-configs jolts %) attachable)]
                (apply + permutations))))))




(defn get-answer
  []
  (get-configs data 0))

(get-answer)
