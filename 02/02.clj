(require '[clojure.string :as str])

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
  [{low :low
    high :high
    policyChar :char
    password :password}]
  (let [freqs (frequencies password)
        count (or (get freqs (first policyChar)) 0)]
    (<= low count high)))

(defn read-lines [filename]
    (with-open [rdr (clojure.java.io/reader filename)]
      (->> rdr
           line-seq
           (map #(parse-line %))
           (vec))))


(let [lines (read-lines "input.txt")]
  (count (filter true? (map #(isValidPassword? %) lines))))
