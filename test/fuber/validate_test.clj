(ns fuber.validate-test
  (:require [clojure.test :refer :all]
            [fuber.validate :refer :all]))

(deftest input-params
  (testing "Spec functions"
    (is (true? (valid-end-trip-params? "123abc" [1 2])))
    (is (false? (valid-end-trip-params? 1 true)))
    (is (false? (valid-end-trip-params? "123abc" [1 "2"])))
    (is (true? (valid-book-trip-params? [1 2] true)))
    (is (false? (valid-book-trip-params? ["1" 2] true)))
    (is (false? (valid-book-trip-params? [1 2] "true")))))
