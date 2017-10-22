(ns study-clojure.core
  (:require [clojure.java.io :as io])
  (:import (java.util Date))
  (:gen-class))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
; UTIL
(defn now []
  ; the dot of 'Date.' means instanciate 'now' in Java.
  ; same like (new java.util.Date)
  (Date.))
    
(defn home-path []
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
  (if (<= n 1N)
    1N
    (* n (factorial (dec n)))))
    
; if-let sample
(defn sample-if-let [n] 
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
    
  
