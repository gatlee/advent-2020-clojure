(ns four
  (:require [clojure.string :as s]
            [clojure.spec.alpha :as spec]))

;; Create specification

(defn parseInt
  [s]
  (Integer/parseInt (re-find #"\d+" s)))

(defn between
  [low hi num]
  (<= low (parseInt num) hi))



(defn validHeight?
  [s]
  (let [[_ digits unit] (re-find #"(\d+)(in|cm)" s)]
    (case unit
      "cm" (between 150 193 digits)
      "in" (between 59 76 digits)
      false)))

(spec/def ::byr (spec/and string? #(between 1920 2002 %)))
(spec/def ::iyr (spec/and string? #(between 2010 2020 %)))
(spec/def ::eyr (spec/and string? #(between 2020 2030 %)))
(spec/def ::hgt (spec/and string? validHeight?))
(spec/def ::hcl (spec/and string? #(re-find #"#[0-9a-f]{6}" %)))
(spec/def ::ecl (spec/and string? #(re-find #"(amb|blu|brn|gry|grn|hzl|oth)" %)))
(spec/def ::pid (spec/and string? #(re-find #"^[0-9]{9}$" %)))
(spec/def ::cid string?)

(spec/def ::passport (spec/keys :req [::ecl ::pid ::eyr ::hcl ::byr ::iyr ::hgt]
                                :opt [::cid]))


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


(count
 (filter
  (partial spec/valid? ::passport)
  (parse-clean-inputs
   (clean-input "./input.txt"))))

(spec/valid? ::passport (first (parse-clean-inputs
                                (clean-input "./input.txt"))))
