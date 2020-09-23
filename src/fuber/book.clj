(ns fuber.book
  (:require [clojure.math.numeric-tower :as math]))

;; Sample cabs-info where in :location first variable is latitute while second varibale is longitude
(def cabs-info
  (atom
   [{:license "123abc" :booked? false :pink? false :location [(rand 100) (rand 100)]}
    {:license "456abc" :booked? false :pink? true :location [(rand 100) (rand 100)]}
    {:license "786abc" :booked? false :pink? false :location [(rand 100) (rand 100)]}
    {:license "432abc" :booked? false :pink? true :location [(rand 100) (rand 100)]}
    {:license "126abc" :booked? false :pink? false :location [(rand 100) (rand 100)]}]))

(defn distance-between-locations
  "Function to calculate distance between customer and current cab locations.
   Distance calculated is assumed to be in km"
  [cus-loc cab-loc]
  (let [distance (math/sqrt (+ (math/expt (- (first cus-loc) (first cab-loc)) 2)
                               (math/expt (- (second cus-loc) (second cab-loc)) 2)))]
    distance))

(defn available-cabs
  "Returns all the available (free) cabs"
  []
  (filter #(false? (% :booked?)) @cabs-info))

(defn available-pink-cabs
  "Retuns only tha available (free) pink cabs"
  []
  (filter #(true? (% :pink?)) (available-cabs)))

(defn nearest-available-cab
  "Given the customer location find the nearest available
   cab for the customer"
  [cus-loc pink]
  (let [free-cabs (if pink (available-pink-cabs)
                    (available-cabs))
        nearest-free-cab (first (sort-by #(distance-between-locations cus-loc (% :location))
                                         free-cabs))]
    nearest-free-cab))
