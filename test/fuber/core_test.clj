(ns fuber.core-test
  (:require [clojure.test :refer :all]
            [fuber.core :refer :all]
            [clj-http.client :as http]
            [fuber.book :refer :all]
            [slingshot.slingshot :refer :all]))

(deftest handlers
  (testing "testing endpoints response"
    (with-redefs [cabs-info
                  (atom [{:license "xyz123" :booked? false :pink? false :location [1 5]}
                         {:license "pqr123" :booked? false :pink? true :location [2 9]}
                         {:license "lmn123" :booked? false :pink? false :location [3 21]}])
                  total-amount (atom 0)]
     (-main)
     (is (= (select-keys (http/get "http://localhost:3000/total-amount") [:status :body])
            {:status 200
             :body "0"}))
     (is (= (select-keys (http/get "http://127.0.0.1:3000/book-trip?source=[10,2]&pink=false")
                         [:status :body])
            {:status 200
             :body "Cab with license number xyz123 booked"}))
     (is (= (select-keys (http/get "http://127.0.0.1:3000/book-trip?source=[10,2]&pink=true")
                         [:status :body])
            {:status 200
             :body "Cab with license number pqr123 booked"}))
     (is (= (select-keys (http/get "http://127.0.0.1:3000/end-trip?license-num=xyz123&destination=[3 4]")
                         [:status :body])
            {:status 200
             :body "Trip Ended. Total amount owed = 123.7618681177688"}))
     (is (= (select-keys (http/get "http://127.0.0.1:3000/end-trip?license-num=pqr123&destination=[3 4]")
                         [:status :body])
            {:status 200
             :body "Trip Ended. Total amount owed = 128.7618681177688"}))
     (is (= (select-keys
             (http/get "http://127.0.0.1:3000/end-trip?license-num=pqr123&destination=[3 4]" {:throw-exceptions false})
             [:status :body])
            {:status 400
             :body "[:causes \"cab already free\"]"}))
     (is (= (select-keys
             (http/get "http://127.0.0.1:3000/book-trip?source1=pqr123&pink=true" {:throw-exceptions false})
             [:status :body])
            {:status 400
             :body "{:source1 pqr123, :pink true} - failed: (contains? % :source) spec: :fuber.validate/book-required\n"}))
     (is (= (select-keys (http/get "http://localhost:3000/total-amount") [:status :body])
            {:status 200
             :body "252.52373623553763"})))))
