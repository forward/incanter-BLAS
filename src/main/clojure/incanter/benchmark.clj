(ns incanter.benchmark
  (:use [incanter core])
  (:gen-class :main true))


(defn rand-mat [size]
  (let [r (range 0 size)]
    (matrix (for [_ r] (for [_ r] (rand))))))

(defmacro benchmark-test [name & body]
  `(let [t# (fn [~'m ~'size] ~@body)]
     [~name
      (fn [numTrials# size#]
        (println (str "\nTesting " ~name " : " size# "\n"))
        (System/gc)
        (let [m# (rand-mat size#)
              before# (System/currentTimeMillis)
              _# (dotimes [_# numTrials#] (t# m# size#))
              after# (System/currentTimeMillis)]
          (- after# before#)))]))

(defn def-suite [& tests]
  (reduce (fn [ac [name f]]
            (assoc ac name f))
    {}
    tests))

(defn benchmark [min-size max-size step times suite]
  (let [sizes (range min-size max-size step)
        results {}]
    (reduce (fn [total-results test-name]
              (let [to-test (test-name suite)
                    test-results (reduce (fn [test-results size]
                                           (let [result (to-test times size)]
                                             (assoc test-results size {:total result :avg (/ result size)})))
                                          {}
                                          sizes)]
                (assoc total-results test-name test-results)))
      {}
      (keys suite))))

(defn report [results]
  (doseq [test-name (keys results)]
    (let [test-results (test-name results)
          times (sort (keys test-results))]
      (doseq [time times]
        (let [this-results (get test-results time)]
          (println (str (name test-name) "," time "," (:total this-results) "," (double (:avg this-results)))))))))

(def *benchmark-suite*  (def-suite

                          (benchmark-test
                            :matrix-creation
                            (do (rand-mat size) :ok))

                          (benchmark-test
                            :matrix-multiplication
                            (do (mmult m m) :ok))

                          (benchmark-test
                            :solve
                            (do (solve m) :ok))

                          (benchmark-test
                            :transpose-matrix-multiplication
                            (do (mmult m (trans m)) :ok))

                          (benchmark-test
                            :matrix-addition
                            (do (plus m m) :ok))

                          (benchmark-test
                            :matrix-scalar-multiplication
                            (do (mult m 1) :ok))

                          (benchmark-test
                            :eigenvalues
                            (do (decomp-eigenvalue m) :ok))))

(defn -main [min max step times]
  (report (benchmark (Integer/parseInt min) (Integer/parseInt max) (Integer/parseInt step) (Integer/parseInt times) *benchmark-suite*)))
