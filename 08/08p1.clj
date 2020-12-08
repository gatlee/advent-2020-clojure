(ns handheld
  (:require [clojure.string :as s]))

(def data
  (->> (slurp "input.txt")
       s/split-lines))


(defn parse-instruction
  [line]
  (let [[instruction val] (s/split line #" ")
        parsedVal (Integer/parseInt val)]
    {:ins instruction :val parsedVal}))


(def instructions
  (map parse-instruction data))


(defn run-instructions
  [instructions]
  (loop [curr 0
         visited #{}
         accum 0]
    (if (contains? visited curr)
      accum
      (let [instruction (nth instructions curr)]
        (case (:ins instruction)
          "nop" (recur (inc curr) (conj visited curr) accum)
          "acc" (recur (inc curr) (conj visited curr) (+ accum (:val instruction)))
          "jmp" (recur (+ curr (:val instruction)) (conj visited curr) accum)
          9999)))))



(run-instructions instructions)
