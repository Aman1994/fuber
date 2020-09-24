(ns fuber.core
  (:require [fuber.book :refer :all]
            [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.defaults :refer :all])
  (:gen-class))

(defn process-request
  [request]
  (case (get request :uri)
    "/book-trip" (try
                   (book-cab (read-string ((get request :params) :source))
                             (read-string ((get request :params) :pink)))
                   (catch Exception e {:status 400 :body "Bad parameters"}))
    "/end-trip"  (try
                   (end-trip ((get request :params) :license)
                             (read-string ((get request :params) :destination)))
                   (catch Exception e {:status 400 :body (str "Bad paramters" (ex-data e))}))))

(defroutes app-routes
  "Defines routes for fuber app"
  (GET "/book-trip" [] process-request)
  (GET "/end-trip" []  process-request)
  (GET "/total-amount" [] (str @total-amount))
  (GET "/available-cabs" [] (available-cabs))
  (route/not-found "<h1>Page not found</h1>"))

(defn -main
  "This is our main entry point"
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
      ; Run the server with Ring.defaults middleware
      (server/run-server (wrap-defaults #'app-routes site-defaults) {:port port})
      (println (str "Running webserver at http:/127.0.0.1:" port "/"))))
