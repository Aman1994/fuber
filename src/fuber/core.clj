(ns fuber.core
  (:require [fuber.book :refer :all]
            [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [fuber.validate :refer :all]
            [ring.middleware.defaults :refer :all])
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

(defn -main
  "This is our main entry point"
  [& args]
  (let [port (Integer/parseInt (or (first args) "3000"))]
    (server/run-server (wrap-defaults #'app-routes site-defaults) {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))))
