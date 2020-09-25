(ns fuber.core
  (:require [fuber.book :refer :all]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [fuber.validate :refer :all]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [ring.middleware.json :refer [wrap-json-response]])
  (:gen-class))

(defroutes app-routes
  "Defines routes for fuber app"
  (GET "/book-trip" request (try (validate-data request book-cab)
                              (catch Exception e {:status 400 :body (ex-data e)})))
  (GET "/end-trip"  request (try (validate-data request end-trip)
                              (catch Exception e {:status 400 :body (ex-data e)})))
  (GET "/total-amount" [] (str @total-amount))
  (GET "/available-cabs" [] (available-cabs))
  (route/not-found "<h1>Page not found</h1>"))

(def handler
  (-> app-routes
      wrap-json-response
      wrap-keyword-params
      wrap-params))
