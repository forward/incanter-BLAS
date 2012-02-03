## About

Matrix manipulation library, BLAS under the hood, Incanter interface.

The ported namespaces are:
- incanter.core
- incanter.infix
- incanter.internal

## Performance

    (defn multiplication-test2 [dim]
      (let [r   (range 0 dim)
             m (matrix (for [_ r] (for [_ r] (rand))))]
        (time  (do (mmult m m) :ok))))
     
    (multiplication-test2 10)
    (multiplication-test2 100)
    (multiplication-test2 1000)
     
    ;; incanter+colt
    "Elapsed time: 0.042 msecs"
    "Elapsed time: 2.109 msecs"
    "Elapsed time: 1523.129 msecs"
     
    ;; incanter+jBlas
     
    "Elapsed time: 0.089 msecs"
    "Elapsed time: 0.882 msecs"
    "Elapsed time: 230.297 msecs"

## Build

Use Maven:

    $ mvn compile
    $ mvn clojure:compile
    $ mvn assembly:single

## Test & Benchmarking
    
After compiling Java and Clojure sources:
    
    $ mvn clojure:test
