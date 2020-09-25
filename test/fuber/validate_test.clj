(ns fuber.validate-test
  (:require [clojure.test :refer :all]
            [clojure.spec.alpha :as s]
            [fuber.validate :as va]))

(deftest input-params
  (testing "Spec functions"
    (is (s/valid? ::va/book-required {:source [1 2] :pink true}))
    (is (s/valid? ::va/end-required {:license-num "123abc" :destination [1 2]}))
    (is (not (s/valid? ::va/end-required {:license-num "123abc" :destination [1 "2"]})))
    (is (not (s/valid? ::va/book-required {:source "123abc" :pink true})))
    (is (s/valid? ::va/license-num "123abc"))
    (is (s/valid? ::va/pink true))
    (is (not (s/valid? ::va/pink "true")))
    (is (s/valid? ::va/source [1 2]))
    (is (not (s/valid? ::va/source ["1" 2])))))
