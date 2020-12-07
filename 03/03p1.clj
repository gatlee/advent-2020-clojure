(defn read-input [filename]
  "Read file input to vector"
  (with-open [rdr (clojure.java.io/reader filename)]
    (->> rdr
         line-seq
         (vec))))


(defn create-map
  [filename]
  (let [input (read-input filename)
        height (count input)
        width (-> input
                  first
                  count)]
    {:map input :height height :width width}))

(defn tree?
  [char]
  (= char \#))

(defn empty?
  [char]
  (= char \.))

(defn getPos
  "Returns character of position on map.
  0, 0 returns the top left corner of the map.
  Returns nil if position is too low"
  [mapData x y]
  (let [wrappedX (mod x (:width mapData))]
    (-> (:map mapData)
        (get y)
        (get wrappedX))))

(defn tree?
  [ch]
  (= ch \#))


(defn getDiagonals
  [mapData xMovement yMovement]
  (loop [map mapData
         x 0
         y 0
         accum []]
    (let [value (getPos map x y)]
      (if (nil? value)
        accum
        (recur map (+ x xMovement) (+ y yMovement) (conj accum value))))))



(defn tree?
  "Is it a tree?"
  [ch]
  (= \# ch))

(defn countTrees
  [tiles]
  (->> tiles
       (filter tree?)
       count))


(defn solve-p1
  []
  (countTrees (getDiagonals (create-map "input.txt") 3 1)))

