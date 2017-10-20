(ns study-clojure.core
  (:require [clojure.java.io :as io])
  (:gen-class))

(defn cwd []
  print('hello'))

; TODO exists
; TODO open & write date
; TODO delete file.

(defn read-file [filepath]
  (with-open [rdr (io/reader filepath)]
    (doseq [str (line-seq rdr)]
      (println str))))

(defn -main
  [& args]
  (cwd)
  (println "Hello, World!"))
