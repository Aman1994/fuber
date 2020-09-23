(ns fuber.validate
  (:require [clojure.spec.alpha :as s]
            [fuber.book :refer [cabs-info]]))

(defn license-exists?
  "Retuns true if license exists else false"
  [license]
  (not (empty? (filter #(= license (% :license)) @cabs-info))))

(s/def ::location
  (s/and vector? #(= (count %) 2) #(every? int? %)))

(s/def ::license
  (s/and string? #(license-exists? %)))

(s/def ::pink boolean?)
