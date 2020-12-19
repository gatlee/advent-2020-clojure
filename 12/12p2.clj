(ns rain-risk
  (:require [clojure.string :as s]))

(def input ["F10 N3 F7 R90 F11"])

(def directions ["N" "E" "S" "W"])

(def initial-pos {:direction 1 :east 0 :north 0 :w-east 10 :w-north 1})

(defn move-waypoint
  "[curr-pos amount direction] moves waypoint"
  ([curr-pos amount direction]
   (case direction
     0 (update curr-pos :w-north #(+ amount %)) ;; North
     1 (update curr-pos :w-east #(+ amount %)) ;; East
     2 (update curr-pos :w-north #(- % amount)) ;; South
     3 (update curr-pos :w-east #(- % amount))))) ;; West

(defn move-towards-waypoint
  [curr-pos n-times]
  (-> curr-pos
      (update :east #(+ (* n-times (:w-east curr-pos)) %))
      (update :north #(+ (* n-times (:w-north curr-pos)) %))))

(defn cos
  [deg]
  (case deg
    0 1
    90 0
    180 -1
    270 0))

(defn sin
  [deg]
  (case deg
    0 0
    90 1
    180 0
    270 -1))

(defn rotate-left
  [curr-pos degrees]
  (let [x (:w-east curr-pos)
        y (:w-north curr-pos)
        x' (- (* x (cos degrees) ) (* y (sin degrees)))
        y' (+ (* x (sin degrees)) (* y (cos degrees)))]
    (assoc curr-pos :w-east x' :w-north y')))

(defn rotate-right
  [curr-pos degrees]
  (rotate-left curr-pos (- 360 degrees )))

(defn next-pos
  [curr-pos instruction]
  (let [[_ letter number] (re-find #"([a-zA-Z]*)([0-9]*)" instruction)]
    (println letter)
    (case letter
      "F" (move-towards-waypoint curr-pos (Integer/parseInt number))
      "L" (rotate-left curr-pos (Integer/parseInt number))
      "R" (rotate-right curr-pos (Integer/parseInt number))
      ("N" "S" "E" "W") (move-waypoint curr-pos (Integer/parseInt number) (.indexOf directions letter)))))

(def data
  (->> (slurp "input.txt")
       s/split-lines))

(def answer
  (let [final-pos (reduce #(next-pos %1 %2) initial-pos data)]
    (+ (Math/abs (:east final-pos)) (Math/abs (:north final-pos)))))
answer
