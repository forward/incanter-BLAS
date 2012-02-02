(ns incanter.tests
  (:use clojure.test
        (incanter core-tests infix-tests)))

(run-tests 'incanter.core-tests)
(run-tests 'incanter.infix-tests)
