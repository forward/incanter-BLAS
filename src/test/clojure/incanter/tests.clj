(ns incanter.tests
  (:use clojure.test)
  (:require (incanter core-tests infix-tests stats-tests distributions-tests dataset-tests smoke-tests)))

(run-tests 'incanter.core-tests 'incanter.infix-tests 'incanter.stats-tests 'incanter.distributions-tests 'incanter.dataset-tests 'incanter.smoke-tests)