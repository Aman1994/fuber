(ns fuber.validate
  (:require [clojure.spec.alpha :as s]
            [fuber.book :refer :all]))

(defn license-exists?
  "Retuns true if license exists else false"
  [license-num]
  (not (empty? (filter #(= license-num (% :license)) @cabs-info))))

(s/def ::source
  (s/coll-of number? :kind vector? :count 2))

(s/def ::destination
  (s/coll-of number? :kind vector? :count 2))

;;Retuns true if string and license exists in our data
(s/def ::license-num (and string?
                          #(license-exists? %)))

(s/def ::pink boolean?)

(s/def ::book-required (s/keys :req-un [::source ::pink]))

(s/def ::end-required (s/keys :req-un [::license-num ::destination]))

(defn with-http-error-response
  "Throws an error and catches it"
  [response]
  (try
    (throw (Exception. ""))
    (catch Exception e response)))

(defn check-validity
  "Checks if params are valid if yes executes the required function
   else catch the exception"
  [api-func spec params]
  (if (s/valid? spec params)
    (api-func params)
    (with-http-error-response
        {:status 400
         :body (s/explain-str spec params)})))

(defn validate-data
  "Validates values passed in the request params"
  [request api-func]
  (let [params (into {} (map (fn [[k v]]
                               (assoc {} k (if (= k :license-num) v (read-string v)))) (request :params)))]
    (case (request :uri)
      "/book-trip" (check-validity api-func ::book-required params)
      "/end-trip" (check-validity api-func ::end-required params))))
