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


(defn execute
  [nums]
  (loop [i prev-length]
    (let [curr-num (get nums i)
          valid? (valid-num?  (subvec nums (- i prev-length) i) curr-num)]
      (if valid?
        (recur (inc i))
        curr-num))))

(println (execute data))
