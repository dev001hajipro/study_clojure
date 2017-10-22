(ns study-clojure.core
  (:require [clojure.java.io :as io])
  (:import (java.util Date))
  (:gen-class))

;;;;;;;;;;;;;;;;;;;;;;
; util
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
;;;;;;;;;;;;;;;;

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

(defn -main
  [& args]
  (home-path)
  (let [filepath "c:\\tmp\\hello_clojure.txt"]
    (renew-file filepath)
    (read-file2 filepath println))
  (println "Hello, World!"))
