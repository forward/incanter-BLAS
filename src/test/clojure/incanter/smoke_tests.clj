(ns incanter.smoke-tests
  (:use [incanter core]
        [incanter datasets]
        [clojure test]))

(defmacro smoke-test [name result & body-test]
  `(deftest ~name
     (is (= ~result (-> (do ~@body-test) flatten vec)))))

(defn compare-with-precision
  ([u v p]
    (doseq [i (range 0 (count u))]
      (is (< (abs (- (nth u i) (nth v i))) p))))
  ([u v] (compare-with-precision u v 0.001)))

(defmacro smoke-test-precision [name result & body-test]
  `(deftest ~name
     (let [results# (-> (do ~@body-test) flatten vec)]
       (doseq [i# (range 0 (count results#))]
         (is (< (abs (- (nth ~result i#) (nth results# i#))) 0.001))))))

(def A (matrix [[1 2 3]
                [4 5 6]
                [7 8 9]
                [10 11 12]]))

(def V (matrix [[1 2 3]
                [4 5 6]
                [7 8 9]
                [10 11 12]]))

(deftest test-is-matrix
  (is (matrix? A))
  (is (not (matrix? [1 2 3]))))

(deftest test-nrow
  (is (= 4 (nrow A))))

(deftest test-ncol
  (is (= 3 (ncol A))))

(deftest test-dim
  (is (= [4 3] (dim A))))

(deftest test-identity-matrix
  (is (= (vec (flatten (identity-matrix 2))) [1.0 0.0 0.0 1.0])))

(deftest teset-diag
  (is (= [1.0 5.0 9.0] (diag A)))
  (is (not (matrix? (diag A)))))

(deftest test-trans
  (is (= [1.0 4.0 7.0 10.0 2.0 5.0 8.0 11.0 3.0 6.0 9.0 12.0] (vec (flatten (trans A)))))
  (is (matrix? (trans A))))

(deftest test-plus
  (is (= [2.0 4.0 6.0 8.0 10.0 12.0 14.0 16.0 18.0 20.0 22.0 24.0] (-> (plus A A) flatten vec))))

(deftest test-plus-plus
  (is (= [3.0 6.0 9.0 12.0 15.0 18.0 21.0 24.0 27.0 30.0 33.0 36.0] (-> (plus A A A) flatten vec))))

(deftest test-plus-scalar
  (is (= [2.0 3.0 4.0 5.0 6.0 7.0 8.0 9.0 10.0 11.0 12.0 13.0] (-> (plus A 1) flatten vec))))

(deftest test-minus
  (is (= 0.0 (-> (minus A A) flatten distinct first))))

(deftest test-minus-minus
  (is (= [-1.0 -2.0 -3.0 -4.0 -5.0 -6.0 -7.0 -8.0 -9.0 -10.0 -11.0 -12.0] (-> (minus A A A) flatten vec))))

(deftest test-minus-scalar
  (is (= [0.0 1.0 2.0 3.0 4.0 5.0 6.0 7.0 8.0 9.0 10.0 11.0] (-> (minus A 1) flatten vec))))

(deftest test-mult
  (is (= (-> [1.0 4.0 9.0 16.0 25.0 36.0 49.0 64.0 81.0 100.0 121.0 144.0] (mult A A) flatten vec))))

(deftest test-mult-mult
  (is (= (-> [1.0 8.0 27.0 64.0 125.0 216.0 343.0 512.0 729.0 1000.0 1331.0 1728.0] (mult A A A) flatten vec))))

(deftest test-mult-scalar
  (is (= (-> [2.0 4.0 6.0 8.0 10.0 12.0 14.0 16.0 18.0 20.0 22.0 24.0] (mult A 2)))))

(deftest test-div
  (is [1.0 1.0 1.0 1.0 1.0 1.0 1.0 1.0 1.0 1.0 1.0 1.0] (= (-> (div A A) flatten vec))))

(deftest test-div-div
  (is (= [1.0 0.5 0.3333333333333333 0.25 0.2 0.16666666666666666 0.14285714285714285 0.125 0.1111111111111111 0.1 0.09090909090909091 0.08333333333333333] (-> (div A A A) flatten vec))))

(deftest test-div-scalar
  (is (= [0.5 1.0 1.5 2.0 2.5 3.0 3.5 4.0 4.5 5.0 5.5 6.0] (-> (div A 2) flatten vec))))

(deftest test-special-case
  (is (= '(1 1/2 1/3) (div [1 2 3]))))

(deftest test-pow
  (is (= [1.0 4.0 27.0 256.0 3125.0 46656.0 823543.0 1.6777216E7 3.87420489E8 1.0E10 2.85311670611E11 8.916100448256E12] (-> (pow A A) flatten vec))))

(deftest test-pow-pow
  (is (= [1.0 16.0 19683.0 4.294967296E9 2.9802322387695315E17 1.0314424798490537E28 2.5692357752105887E41 6.277101735386681E57 1.9662705047555292E77 1.0E100 1.0197997569961307E126 2.524058584527068E155]
        (-> (pow A A A) flatten vec))))

(deftest test-pow-scalar
  (is (= [1.0 4.0 9.0 16.0 25.0 36.0 49.0 64.0 81.0 100.0 121.0 144.0] (-> (pow A 2) flatten vec))))

(deftest test-atan2
  (is (= [0.7853981633974483 0.7853981633974483 0.7853981633974483 0.7853981633974483 0.7853981633974483 0.7853981633974483 0.7853981633974483 0.7853981633974483 0.7853981633974483 0.7853981633974483 0.7853981633974483 0.7853981633974483]
        (-> (atan2 A A) flatten vec))))

(deftest test-atan2-atan2
  (is (= [0.6657737500283538 0.3741966805226849 0.25605276998075555 0.19388305158888441 0.15580649996954174 0.13015964383300485 0.11173244287230884 0.09786116982311284 0.08704594528845262 0.07837892038914972 0.07127887221462778 0.06535663096344164]
        (-> (atan2 A A A) flatten vec))))

(deftest test-atan2-scalar
  (is (= [0.4636476090008061 0.7853981633974483 0.982793723247329 1.1071487177940904 1.1902899496825317 1.2490457723982544 1.2924966677897853 1.3258176636680326 1.3521273809209546 1.373400766945016 1.3909428270024184 1.4056476493802699]
        (-> (atan2 A 2) flatten vec))))


(smoke-test-precision test-sqrt
  [1.0 1.4142135623730951 1.7320508075688772 2.0 2.23606797749979 2.449489742783178 2.6457513110645907 2.8284271247461903 3.0 3.1622776601683795 3.3166247903554 3.4641016151377544]
  (sqrt A))


(smoke-test test-sq
  [1.0 4.0 9.0 16.0 25.0 36.0 49.0 64.0 81.0 100.0 121.0 144.0]
  (sq A))

(smoke-test-precision test-log
  [0.0 0.6931471805599453 1.0986122886681098 1.3862943611198906 1.6094379124341003 1.791759469228055 1.9459101490553132 2.0794415416798357 2.1972245773362196 2.302585092994046 2.3978952727983707 2.4849066497880004]
  (log A))

(smoke-test-precision test-log2
  [0.0 1.0 1.5849625007211563 2.0 2.321928094887362 2.584962500721156 2.807354922057604 2.9999999999999996 3.1699250014423126 3.3219280948873626 3.4594316186372973 3.584962500721156]
  (log2 A))

(smoke-test-precision test-log10
  [0.0 0.30102999566398114 0.4771212547196624 0.6020599913279623 0.6989700043360186 0.7781512503836435 0.8450980400142567 0.9030899869919433 0.9542425094393248 0.9999999999999999 1.041392685158225 1.0791812460476247]
  (log10 A))

(smoke-test-precision test-exp
  [2.7182818284590455 7.38905609893065 20.085536923187668 54.598150033144236 148.4131591025766 403.4287934927351 1096.6331584284585 2980.9579870417283 8103.083927575384 22026.465794806718 59874.14171519782 162754.79141900392]
  (exp A))

(smoke-test test-abs
  [1.0 2.0 3.0 4.0 5.0 6.0 7.0 8.0 9.0 10.0 11.0 12.0]
  (abs A))

(smoke-test-precision test-asin
  [1.5707963267948966 0.0 0.0 1.5707963267948966]
  (asin (identity-matrix 2)))

(smoke-test-precision test-sin
  [0.8414709848078965 0.9092974268256817 0.1411200080598672 -0.7568024953079282 -0.9589242746631385 -0.27941549819892586 0.6569865987187891 0.9893582466233818 0.4121184852417566 -0.5440211108893698 -0.9999902065507035 -0.5365729180004349]
  (sin A))

(smoke-test-precision test-cos
  [0.5403023058681398 -0.4161468365471424 -0.9899924966004454 -0.6536436208636119 0.28366218546322625 0.9601702866503661 0.7539022543433046 -0.14550003380861354 -0.9111302618846769 -0.8390715290764524 0.004425697988050785 0.8438539587324921]
  (cos A))

(smoke-test-precision test-acos
  [0.0 1.5707963267948966 1.5707963267948966 0.0]
  (acos (identity-matrix 2)))

(smoke-test-precision test-tan
  [1.5574077246549023 -2.185039863261519 -0.1425465430742778 1.1578212823495777 -3.380515006246586 -0.29100619138474915 0.8714479827243187 -6.799711455220379 -0.45231565944180985 0.6483608274590866 -225.95084645419513 -0.6358599286615808]
  (tan A))

(smoke-test-precision test-atan
  [0.7853981633974483 0.0 0.0 0.7853981633974483]
  (atan (identity-matrix 2)))

(smoke-test test-kronecker
  [4.0 0.0 0.0 0.0 4.0 0.0 0.0 0.0 4.0]
  (kronecker 4 (identity-matrix 3)))

(deftest test-det
  (is (< (abs (- 14
                (det (matrix [[3 1 8] [2 -5 4] [-1 6 -2]]))))
        0.0001)))

(deftest test-trace
  (is (= (trace A) 15.0)))

(deftest test-vectorize
  (is (= (vectorize A) '(1.0 4.0 7.0 10.0 2.0 5.0 8.0 11.0 3.0 6.0 9.0 12.0))))

(deftest test-half-vectorize
  (is
    '(1.0 3.0 4.0)
    (= (half-vectorize (matrix [[1 2] [3 4]])))))

(smoke-test-precision test-cholesky
  [2.0 1.0 -1.0 0.0 3.0 1.0 0.0 0.0 1.7320508075688772]
  (decomp-cholesky (matrix [[4 2 -2] [2 10 2] [-2 2 5]])))


(deftest test-svd
  (let [r (decomp-svd A)]
    (compare-with-precision  (-> (:S r) flatten vec)
      [25.462407436036397 1.2906616757612326 2.5033102861990926E-15])))

(smoke-test-precision  test-eigenvalues
  [16.116843969807057 -1.1168439698070447 -8.046297179356069E-16]
  (:values (decomp-eigenvalue (matrix (take 3 A)))))


(deftest test-lu
  (let [res (decomp-lu A)
        l (:L res)
        u (:U res)]
    (compare-with-precision
      [1.0 0.0 0.0 0.1 1.0 0.0 0.7000000000000001 0.33333333333333215 1.0 0.4 0.6666666666666663 0.0]
      (-> l flatten vec ))
    (compare-with-precision
      [10.0 11.0 12.0 0.0 0.8999999999999999 1.7999999999999998 0.0 0.0 1.7763568394002505E-15 0.0 0.0 0.0]
      (-> u flatten vec))))


(deftest test-to-vect
  (is (= 4 (count (to-vect A)))))

(deftest test-rank
  (is (= (rank A) 2)))

(deftest test-length
  (is (= (length A) 12)))

(deftest test-get-dataset-to-matrix
  (is (= 60 (length  (to-matrix  (get-dataset :plant-growth))))))


(smoke-test  test-symmetric-matrix
  [1.0 2.0 4.0 7.0 2.0 3.0 5.0 8.0 4.0 5.0 6.0 9.0 7.0 8.0 9.0 10.0]
  (-> (symmetric-matrix [1
                         2 3
                         4 5 6
                         7 8 9 10])
    flatten vec))