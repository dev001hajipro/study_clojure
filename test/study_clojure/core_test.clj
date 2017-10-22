(ns study-clojure.core-test
  (:require [clojure.test :refer :all]
            [study-clojure.core :refer :all]))

; sample
(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(deftest now-test
  (testing "current date object."
    (is (instance? java.util.Date (now)))))
