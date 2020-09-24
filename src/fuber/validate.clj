(ns fuber.validate
  (:require [clojure.spec.alpha :as s]))

(s/def ::location
  (s/coll-of number? :kind vector? :count 2))

(s/def ::license string?)
  ;(s/and string? #(license-exists? %)))

(s/def ::pink boolean?)

(defn valid-end-trip-params?
  "Validates if the params provided to end-trip function is valid or not"
  [lic-num dest]
  (and (s/valid? ::license lic-num)
       (s/valid? ::location dest)))

(defn valid-book-trip-params?
  "Validates if the params provided to book-cab function is valid or not"
  [source pink]
  (and (s/valid? ::location source)
       (s/valid? ::pink pink)))
