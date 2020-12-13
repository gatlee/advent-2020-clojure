(ns adapter
  (:require [clojure.string :as s]))


(def data
  (->> (slurp "input.txt")
       s/split-lines
       (mapv #(Integer/parseInt %))))
(def joltages [16 10 15 5 1 11 7 19 6 12 4])

(defn add-built-in
  [joltages]
  (conj joltages (+ 3 (apply max joltages))))


(defn attachable?
  [female male]
  (<=  (- male female) 3))

(sort joltages)

(defn execute
  [joltages]
  (let [sorted (sort (add-built-in joltages))]
    (loop [prev-jolt 0
           diffs {:1 0
                  :2 0
                  :3 0}
           jolts sorted]
      (if (empty? jolts)
          diffs
          (let [new-jolt (first jolts)
                diff (str (- new-jolt prev-jolt))]
            (recur new-jolt
                   (update diffs (keyword diff) inc)
                   (rest jolts)))))))



(def ans1
  (let [diffs (execute data)]
    (* (:1 diffs) (:3 diffs))))

ans1
