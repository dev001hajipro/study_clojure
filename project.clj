(defproject study_clojure "0.1.0-SNAPSHOT"
  :description "study clojure project."
  :url "https://github.com/dev001hajipro/study_clojure"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]]
  :main ^:skip-aot study-clojure.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
