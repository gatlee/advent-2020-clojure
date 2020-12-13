(ns encoding-error
  (:require [clojure.string :as s]))

(def data
  (->> (slurp "input.txt")
       (s/split-lines)
       (mapv #(Long/parseLong %))))





(defn valid-num?
  [preamble num]
  (first
   (for [i (range 0 (identity (count preamble)))
         j (range (inc i) (identity (count preamble)))
         :when (= num (+ (get preamble i) (get preamble j)))]
     [i j])))

(valid-num? (subvec nums 0 5) 40)

(def prev-length 25)


(defn get-invalid-num
  [nums]
  (loop [i prev-length]
    (let [curr-num (get nums i)
          valid? (valid-num?  (subvec nums (- i prev-length) i) curr-num)]
      (if valid?
        (recur (inc i))
        curr-num))))



(def x [35 20 15 25 47 40 62 55 65 95 102 117 150 182 127 219 299 277 309 576])

(def goal 127)

(defn execute
  [nums]
  (let [goal (get-invalid-num nums)]
    (println goal)
    (loop [low 0
           hi 1]
      (let [run (subvec nums low hi)
            total (apply + run)]
        (println (str total " " low " " hi))
        (cond
          (= total goal) [(apply min run) (apply max run)]
          (< total goal) (recur low (inc hi))
          (> total goal) (recur (inc low) hi))))
    ))



(def answer
  (apply + (execute data)))
