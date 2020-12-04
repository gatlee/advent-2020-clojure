
(require '[clojure.string :as str])

(defn xor
  [a b]
  (or (and a (not b))
      (and (not a) b)))



(defn parse-line
  "Create data structure for singular spec + password combo"
  [passwordRecord]
  (let [splitted (str/split passwordRecord #"(\s|-|:)\s*")
        low (Integer/parseInt (nth splitted 0))
        high (Integer/parseInt (nth splitted 1))
        policyCharacter (nth splitted 2)
        password (nth splitted 3)]
    {:low low :high high :char policyCharacter :password password}))



(defn isValidPassword?
  [{ind1 :low
    ind2 :high
    policyChar :char
    password :password}]
  (let [value1 (get password (dec ind1))
        value2 (get password (dec ind2))]
    (xor
     (= value1 (first policyChar))
     (= value2 (first policyChar)))))

(defn read-lines [filename]
  "Convert file to clojure data structure"
  (with-open [rdr (clojure.java.io/reader filename)]
    (->> rdr
         line-seq
         (map #(parse-line %))
         (vec))))


(let [lines (read-lines "input.txt")]
  (count (filter true? (map #(isValidPassword? %) lines))))
