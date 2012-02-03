(ns incanter.benchmark
  (:use [incanter core]))


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
        (do (reduce + (diag (mult m m)))
          :ok)))))


(defn multi-test2 [dim times]
  (let [m (identity (map (fn [_] (map rand (range 0 dim))) (range 0 dim)))]
    (time
      (doseq [i (range 0 times)]
        (matrix m)))))

(import org.jblas.MatrixFunctions)
(defn multiplication-test1 [dim]
  (let [m (.wrapped (matrix (map (fn [_] (map rand (range 0 dim))) (range 0 dim))))]
    (time(do (.mmul m m) :ok))))

(defn multiplication-test2 [dim]
  (let [m (matrix (for [_ (range 0 dim)] (for [_ (range 0 dim)] (double (rand)))))]
    (time  (do (mmult m m) :ok))))



(println "complex")
(incanter-test1 1000)

(println "creation")
(incanter-test2 1000)


(println "all-together")
(incanter-test0 1000)


(println "Big test")
(multi-test 10 1000000)


(println "pure matrix multiplication")
(multiplication-test1 10)
(multiplication-test1 100)
(multiplication-test1 1000)

(println "matrix multiplication")
(multiplication-test2 10)
(multiplication-test2 100)
(multiplication-test2 1000)

