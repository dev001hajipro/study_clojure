(ns study-clojure.core
  (:require [clojure.java.io :as io])
  (:import (java.util Date))
  (:gen-class))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; UTIL
(defn now []
  "the dot of 'Date.' means instanciate 'now' in Java.
   same like (new java.util.Date)"
  (Date.))
    
(defn home-path []
  "get user home directory"
  (System/getProperty "user.home"))

(defmulti rand* (fn [min max] [(class min) (class max)]))
(defmethod rand* [Long Long] [min max] 
  (long (+ (rand (- (inc max) min)) min)))
(defmethod rand* [Double Double] [min max] 
  (+ (rand (- (inc max) min)) min))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; FILE IO

(defn read-file [filepath]
  (with-open [rdr (io/reader filepath)]
    (doseq [str (line-seq rdr)]
      (println str))))

(defn read-file2 [filepath f]
  (with-open [rdr (io/reader filepath)]
    (doall (map f (line-seq rdr)))))

(defn renew-file [filepath]
  (if (.exists (io/as-file filepath))
    (io/delete-file filepath))
  (with-open [w (io/writer filepath)]
    (.write w (str (now) "\n"))
    (.write w (str "hellox"))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; Sandbox
(defn factorial [n]
  "factorial"
  (if (<= n 1N)
    1N
    (* n (factorial (dec n)))))
    
(defn sample-if-let [n]
  "if-let sample"
  (if-let [v n] v "no data."))

; tail recursion
(defn tail-recursion-loop [max-count]
  (loop [n 1]
    (if (< n max-count)
      (do 
        (print (str n ","))
        (recur (inc n)))
      (println "Done."))))
    

(defn fizzbuzz [n]
  (loop [i 1]
    (if (<= i n)
      (do
        (print (cond
                 (and (zero? (mod i 3)) (zero? (mod i 5))) "fizzbuzz"
                 (zero? (mod i 3)) "fizz"
                 (zero? (mod i 5)) "buzz"
                 :else i))
        (print " ")
        (recur (inc i))))))

(defn cond-test [n]
  (cond
    (< n 10) "n<10"
    (< n 20) "n<20"
    :else "n>=20"))

(defn destruct-map []
  "exercise destruct and map"
  (let [fruits {:name "apple" :quantity 50 :taste "good"}]
    (let [{s :name, q :quantity} fruits]
      (print s ",q=" q))))

(defn hello-map []
  "% is argument placeholder. # is lamda."
  (let [fruits '("apple" "orange" "melon")]
    (map #(* (count %) 2) fruits)))

(defn vlen [v]
  "count vector length."
  (loop [l v, c 0]
    (if (empty? l)
      c
      (recur (rest l) (inc c)))))

(defn test1 []
  (do
    (every? even? (map #(* % 2) (range 1000000)))
    (some nil? [1 2 nil])))
      
;; take & drop
(defn take-drop []
  (->>
       [:xxx :yyy :zzz]
       (cycle)
       (drop 2)
       (take 5)))

(defn fact2 [n]
  (apply * (take n (iterate inc 1))))

(defn fact3 [n]
  (->>
       (iterate inc 1)
       (take n)
       (apply *)))

;; protocol
;; type and interface.
(defprotocol Compass
  (direction [c])
  (left [c])
  (right [c]))

(def directions [:north :east :south :west])

(defn turn [base amount]
  ; reminder
  (rem (+ base amount) (count directions)))

(defrecord SimpleCompass [bearing]
  Compass ; protocol name.
  (direction [_]
    (directions bearing))
  (left [_]
    (SimpleCompass. (turn bearing 3)))
  (right [_]
    (SimpleCompass. (turn bearing 1)))
  Object ; protocol 
  (toString [this] (str "[" (direction this) "]")))
  

(defn -main
  [& args]
  (home-path)
  (let [filepath "c:\\tmp\\hello_clojure.txt"]
    (renew-file filepath)
    (read-file2 filepath println))
  (println "Hello, World!")
  (do
    (print "What's your name?")
    (flush)
    (let [v (read-line)]
      (print v))))
    
  
