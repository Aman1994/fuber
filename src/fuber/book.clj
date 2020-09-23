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

;; total amount earned by fuber till date
(def total-amount (atom 0))

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
  (let [cabs (filter #(false? (% :booked?)) @cabs-info)]
    (if (empty? cabs)
      (throw (ex-info "No cabs available"
                      {:type :unavailable :cause :unavailable :status 403})))
    cabs))

(defn available-pink-cabs
  "Returns only the available (free) pink cabs"
  []
  (let [cabs (filter #(true? (% :pink?)) (available-cabs))]
    (if (empty? cabs)
      (throw (ex-info "No pink cabs available"
                      {:type :unavailable :cause :unavailable :status 403})))
    cabs))

(defn nearest-available-cab
  "Given the customer location find the nearest available
   cab for the customer"
  [cus-loc pink]
  (let [free-cabs (if pink (available-pink-cabs)
                    (available-cabs))
        nearest-free-cab (first (sort-by #(distance-between-locations cus-loc (% :location))
                                         free-cabs))]
    nearest-free-cab))

;; Assumming covering 1 km takes 15 mins (Its Bangalore :p)
(defn amount-owed
  "Returns the total amount owed for the trip"
  [source destination pink]
  (let [total-distance (distance-between-locations source destination)
        total-amount   (+ (* 2 total-distance) (* total-distance 15 1))]
    (if pink
      (+ total-amount 5)
      total-amount)))

(defn book-cab
  "Books the cab and returns the message showing license num of the booked cab"
  [source pink]
  (let [nearest-cab (nearest-available-cab source pink)
        _ (reset! cabs-info (map (fn [{:keys [license] :as item}]
                                   (if (= license (nearest-cab :license))
                                     (assoc item :booked? true :location source) item))
                                 @cabs-info))]
    (str "Cab with license number " (nearest-cab :license) " booked")))

(defn end-trip
  "Ends the trip at the destination given and returns the message
   showing total amount owed for the trip"
  [cab-license destination]
  (let [booked-cab-info (first (filter #(= cab-license (% :license)) @cabs-info))
        _ (when (false? (booked-cab-info :booked?))
            (throw (ex-info "Trying to end the trip of free cab"
                            {:type :unavailable :cause :unavailable :status 403})))
        cost (amount-owed (booked-cab-info :location) destination (booked-cab-info :pink?))
        _ (reset! cabs-info (map (fn [{:keys [license] :as item}]
                                   (if (= license cab-license)
                                     (assoc item :booked? false :location destination) item))
                                 @cabs-info))
        _ (reset! total-amount (+ @total-amount cost))]
    (str "Trip Ended. Total amount owed = " cost)))
