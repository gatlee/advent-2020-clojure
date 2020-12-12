(ns handheld
  (:require [clojure.string :as s]))

(def data
  (->> (slurp "./input.txt")
       s/split-lines
       (into [] )))



(defn parse-instruction
  [line]
  (let [[instruction val] (s/split line #" ")
        parsedVal (Integer/parseInt val)]
    {:ins instruction :val parsedVal}))


(def instructions
  (into [] (map parse-instruction data)))


(defn instructions-terminate?
  [instructions]
  (loop [curr 0
         visited #{}
         accum 0]
    (cond
      (contains? visited curr) nil ;; Back in a loop
      (= curr (count instructions)) accum
      :else (let [instruction (nth instructions curr)]
        (case (:ins instruction)
          "nop" (recur (inc curr) (conj visited curr) accum)
          "acc" (recur (inc curr) (conj visited curr) (+ accum (:val instruction)))
          "jmp" (recur (+ curr (:val instruction)) (conj visited curr) accum)
          (throw (Exception. "Unexpected instruction")))))))



(instructions-terminate? instructions)



(defn toggle-nth-instruction
  [instructions n]
  (case (get-in instructions [n :ins])
    "jmp" (assoc-in instructions [n :ins] "nop")
    "nop" (assoc-in instructions [n :ins] "jmp")
    instructions))


(defn generate-instructions
  [instructions]
  (for [x (range 0 (count instructions))
        :let  [toggled-instructions (toggle-nth-instruction instructions x)
               accum-value (instructions-terminate? toggled-instructions)]
        :when (some? accum-value)]
    accum-value))

(instructions-terminate? (toggle-nth-instruction instructions 4))

(generate-instructions instructions)
