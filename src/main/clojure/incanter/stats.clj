;;; stats.clj -- Statistics library for Clojure built on the CERN Colt Library

;; by David Edgar Liebke http://incanter.org
;; March 11, 2009

;; Copyright (c) David Edgar Liebke, 2009. All rights reserved.  The use
;; and distribution terms for this software are covered by the Eclipse
;; Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;; which can be found in the file epl-v10.htincanter.at the root of this
;; distribution.  By using this software in any fashion, you are
;; agreeing to be bound by the terms of this license.  You must not
;; remove this notice, or any other, from this software.

;; CHANGE LOG
;; March 11, 2009: First version



(ns ^{:doc "This is the core statistical library for Incanter.
            It provides probability functions (cdf, pdf, quantile),
            random number generation, statistical tests, basic
            modeling functions, similarity/association measures,
            and more.

            This library is built on Parallel Colt
            (http://sites.google.com/site/piotrwendykier/software/parallelcolt),
            an extension of the Colt numerics library
            (http://acs.lbl.gov/~hoschek/colt/).
            "
      :author "David Edgar Liebke and Bradford Cross"}
  incanter.stats
  (:import (incanter Matrix)
           (uk.co.forward.clojure.incanter DoubleFunctions)
           (org.jblas DoubleMatrix)
           (javax.swing JTable JScrollPane JFrame)
           (java.util Vector)
           (org.apache.commons.math.stat.descriptive.moment Variance))
  (:use [clojure.set :only [difference intersection union]])
  (:use [incanter.core :only [matrix matrix? to-list sqrt]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; STATISTICAL FUNCTIONS
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(defn mean
  "
    Returns the mean of the data, x.

    Examples:
      (mean (sample-normal 100))

    References:
      http://incanter.org/docs/parallelcolt/api/cern/jet/stat/tdouble/DoubleDescriptive.html
      http://en.wikipedia.org/wiki/Mean

  "
  ([x]
  (if (matrix? x)
    (.mean (.wrapped x))
    (.mean (.wrapped (matrix (double-array x)))))))


(defn variance
  "
    Returns the sample variance of the data, x. Equivalent to R's var function.

    Examples:
      (variance (sample-normal 100))

    References:
      http://incanter.org/docs/parallelcolt/api/cern/jet/stat/tdouble/DoubleDescriptive.html
      http://en.wikipedia.org/wiki/Sample_variance#Population_variance_and_sample_variance

  "
  ([x] (let [xx (if (or (nil? x) (empty? x)) [0] (to-list x))]
         (.evaluate (Variance. true) (double-array xx)))))


(defn sd
  "
    Returns the sample standard deviation of the data, x. Equivalent to
    R's sd function.

    Examples:
      (sd (sample-normal 100))

    References:
      http://incanter.org/docs/parallelcolt/api/cern/jet/stat/tdouble/DoubleDescriptive.html
      http://en.wikipedia.org/wiki/Standard_deviation
  "
  ([x]
    ;; population sd, not the sample sd
    ;; return the sample standard deviation
    (sqrt (variance x))))