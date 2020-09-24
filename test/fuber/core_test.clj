(ns fuber.core-test
  (:require [clojure.test :refer :all]
            [fuber.core :refer :all]
            [clj-http.client :refer [get]]))

(deftest handlers
  (testing "testing endpoints response"
    (-main)
    (is (= (select-keys (get "http://localhost:3000/total-amount") [:status :body])
           {:status 200
            :body "0"}))
    (is (= (select-keys (get "http://127.0.0.1:3000/book-trip?source=[1,2]&pink=false")
                        [:status :body])
           {:status 200
            :body "Cab with license number 456abc booked"}))
    (is (= (select-keys (get "http://127.0.0.1:3000/end-trip?license=456abc&destination=[3 4]")
                        [:status :body])
           {:status 200
            :body "Trip Ended"}))))
