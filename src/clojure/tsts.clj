(ns tsts)

(use 'incanter.core)
(defn incanter-test0 [dim]
  (let [m (matrix (map (fn [_] (map rand (range 0 dim))) (range 0 dim)))]
    (time (do (mult m m)
            :ok))))

(defn incanter-test1 [dim]
  (let [m (matrix (map (fn [_] (map rand (range 0 dim))) (range 0 dim)))]
    (time (do (println (reduce + (diag (mult m m))))
            :ok))))

(defn incanter-test2 [dim]
  (let []
    (time (do (matrix (map (fn [_] (map rand (range 0 dim))) (range 0 dim)))
            :ok))))


(defn multi-test [dim times]
(time
  (doseq [i (range 0 times)]
    (let [m (matrix (map (fn [_] (map rand (range 0 dim))) (range 0 dim)))]
      (identity (do (println (reduce + (diag (mult m m))))
              :ok))))))


(println "complex")
(incanter-test1 1000)

(println "creation")
(incanter-test2 1000)

(comment
(println "multiplication")
(def m (.wrapped (matrix (map (fn [_] (map rand (range 0 1000))) (range 0 100)))))
(import org.jblas.MatrixFunctions)
(time
  (.muli m m))
)

(println "all-together")
(incanter-test0 1000)


(println "THE REAL THING")
(multi-test 1000 100)
