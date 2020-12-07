(ns four
  (:require [clojure.string :as s]
            [clojure.spec.alpha :as spec]))

(def example
  {
   ::ecl "gry"
   ::pid 860033327
   ::eyr 2020
   ::hcl "#fffffd"
   ::byr 1937
   ::iyr 2017
   ::cid 147
   ::hgt "183cm"})


;; Create specification
(spec/def ::ecl string?)
(spec/def ::pid string?)
(spec/def ::eyr string?)
(spec/def ::hcl string?)
(spec/def ::byr string?)
(spec/def ::iyr string?)
(spec/def ::cid string?)
(spec/def ::hgt string?)

(spec/def ::passport (spec/keys :req [::ecl ::pid ::eyr ::hcl ::byr ::iyr ::hgt]
                                :opt [::cid]))

(spec/valid? ::passport example)


(defn clean-input
  [filename]
  (let [dirtyPassportStrings (s/split (slurp filename) #"\n\n")]
    (map #(s/split % #"\s+") dirtyPassportStrings )))

(defn parsePropString
  [map str]
  (let [[key value] (s/split str #":")
        kw (keyword "four" key)]
    (assoc map kw value)))

(defn parse-clean-inputs
  [clean-inputs]
  (map #(reduce parsePropString {} %) clean-inputs))

(def x
  ["ecl:gry"
   "pid:860033327"
   "eyr:2020"
   "hcl:#fffffd"
   "byr:1937"
   "iyr:2017"
   "cid:147"
   "hgt:183cm"])

(count (filter (partial spec/valid? ::passport) (parse-clean-inputs (clean-input "./input.txt"))))
