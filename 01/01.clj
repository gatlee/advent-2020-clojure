
(defn read-input []
    (with-open [rdr (clojure.java.io/reader "input.txt")]
        (reduce conj [] (line-seq rdr))))

(let [lines (read-input)]
  (loop [line lines
         cache #{}]
    (let [val (Integer/parseInt (first line))]
        (if (contains? cache val)
            (* val (- 2020 val))
            (recur (rest line) (conj cache (- 2020 val)))))))
