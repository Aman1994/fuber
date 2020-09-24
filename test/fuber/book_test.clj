(ns fuber.book-test
  (:require [clojure.test :refer :all]
            [fuber.book :refer :all]))

(deftest distance-btwn-two-location
  (testing "Distance between two locations"
    (is (= 1.4142135623730951 (distance-between-locations [1 2] [2 1])))
    (is (= 0 (distance-between-locations [1 2] [1 2])))
    (is (= 19.916073910286634 (distance-between-locations [2.1 2] [22 2.8])))))
