
(defn read-nums [filename]
    (with-open [rdr (clojure.java.io/reader filename)]
      (->> rdr
       line-seq
       (map #(Integer/parseInt %))
       set)))

(let [lines (read-nums "input.txt")]
  (loop [line lines]
    (let [val (first line)]
        (if (contains? lines (- 2020 val))
            (* val (- 2020 val))
            (recur (rest line))))))
