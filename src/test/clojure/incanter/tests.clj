(ns incanter.tests
  (:use clojure.test)
  (:require (incanter core-tests infix-tests stats-tests)))

(run-tests 'incanter.core-tests 'incanter.infix-tests 'incanter.stats-tests)
