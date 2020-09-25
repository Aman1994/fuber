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
