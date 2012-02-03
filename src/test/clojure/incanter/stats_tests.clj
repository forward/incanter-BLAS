;;; core-test-cases.clj -- Unit tests of Incanter functions 

;; by David Edgar Liebke http://incanter.org
;; October 31, 2009

;; Copyright (c) David Edgar Liebke, 2009. All rights reserved.  The use
;; and distribution terms for this software are covered by the Eclipse
;; Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;; which can be found in the file epl-v10.html at the root of this
;; distribution.  By using this software in any fashion, you are
;; agreeing to be bound by the terms of this license.  You must not
;; remove this notice, or any other, from this software.


;; to run these tests:
;; (use 'tests core-test-cases)
;;  need to load this file to define data variables
;; (use 'clojure.test) 
;; then run tests
;; (run-tests 'incanter.tests.core-test-cases)

(ns incanter.stats-tests
  (:use clojure.test
        (incanter core stats)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; UNIT TESTS FOR incanter.stats.clj
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;; define a simple matrix for testing
(def A (matrix [[1 2 3]
                [4 5 6]
                [7 8 9]
                [10 11 12]]))

(def test-mat (matrix
                [[39 10]
                 [51 20]
                 [60 30]
                 [64 40]
                 [73 50]
                 [83 60]
                 [90 70]
                 [93 80]
                 [99 90]
                 [105 100]
                 [110 110]
                 [111 120]
                 [113 130]
                 [117 140]
                 [120 150]
                 [125 160]
                 [130 170]
                 [133 180]
                 [133 190]
                 [134 200]
                 [138 210]
                 [140 220]
                 [145 230]
                 [146 240]
                 [148 250]]))

(def x (sel test-mat :cols 0))
(def y (sel test-mat :cols 1))

(def dataset1 (dataset [:a :b :c ] [[1 2 3] [4 5 6] [7 8 9] [10 11 12]]))

(def summary-ds0 (to-dataset [[1] [4] [7]]))
(def summary-ds1 (to-dataset [[1] [3.142] [7]]))
(def summary-ds2 (to-dataset [["a"] ["b"] ["c"]]))
(def summary-ds3 (to-dataset [[:a ] [:b ] [:c ]]))
(def summary-ds4 (to-dataset [[:a ] ["b"] [:c ]]))
(def summary-ds5 (to-dataset [[1] [2.1] [:c ]]))
(def summary-ds6 (to-dataset [[1] [2.1] ["c"]]))
(def summary-ds7 (to-dataset [[1] [2.1] [nil]]))

(def summary-ds8 (to-dataset [["a"] ["b"] ["c"] ["d"] ["b"] ["e"] ["a"] ["b"] ["f"] ["a"] ["b"] ["e"]]))
(def summary-ds9 (to-dataset [["a" 1.2] [":b" 3] [:c 0.1] ["d" 8] ["b" 9] ["e" 7.21] ["a" 1E1] ["b" 6.0000] ["f" 1e-2] ["a" 3.0] ["b" 4] ["e" 5]]))


(deftest mean-test
  (is (= (map mean (trans test-mat)) [108.0 130.0])))

(deftest sample-mean
  (is (= 3.0
        (mean [2 3 4]))))

(deftest variance-test
  (is (= (map variance (trans test-mat)) [1001.5833333333334 5416.666666666667])))

(deftest sd-test
  ;; calculate the standard deviation of a variable
  (is (= (sd x) 31.6478013980961))
  (is (= (map sd A) [1.0 1.0 1.0 1.0])))

(deftest stdev-test
  (is (= 2.138089935299395
        (sd [2 4 4 4 5 5 7 9]))))
