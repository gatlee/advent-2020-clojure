(ns rain-risk
  (:require [clojure.string :as s]))

(def input ["F10 N3 F7 R90 F11"])

(def directions ["N" "E" "S" "W"])

(def initial-pos {:direction 1 :east 0 :north 0})

(defn move-direction
  "[curr-pos amount] moves in direction ship is facing

  [curr-pos amount direction] moves in specified direction "
  ([curr-pos amount]
   (move-direction curr-pos amount (:direction curr-pos)))
  ([curr-pos amount direction]
   (case direction
     0 (update curr-pos :north #(+ amount %)) ;; North
     1 (update curr-pos :east #(+ amount %)) ;; East
     2 (update curr-pos :north #(- % amount)) ;; South
     3 (update curr-pos :east #(- % amount))))) ;; West

(defn rotate
  [curr-pos amount]
  (update curr-pos :direction #(mod (+ amount %) 4)))

(defn rotate-left
  [curr-pos degrees]
  (rotate curr-pos (/ degrees -90)))

(defn rotate-right
  [curr-pos degrees]
  (rotate curr-pos (/ degrees 90)))

(defn next-pos
  [curr-pos instruction]
  (let [[_ letter number] (re-find #"([a-zA-Z]*)([0-9]*)" instruction)]
    (println letter)
    (case letter
      "F" (move-direction curr-pos (Integer/parseInt number))
      "L" (rotate-left curr-pos (Integer/parseInt number))
      "R" (rotate-right curr-pos (Integer/parseInt number))
      ("N" "S" "E" "W") (move-direction curr-pos (Integer/parseInt number) (.indexOf directions letter)))))

(def data
  (->> (slurp "input.txt")
       s/split-lines))

(def answer
  (let [final-pos (reduce #(next-pos %1 %2) initial-pos data)]
    (+ (Math/abs (:east final-pos)) (Math/abs (:north final-pos)))))
answer
