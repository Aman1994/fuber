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
