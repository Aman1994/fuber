(defproject fuber "0.1.0"
  :description "Backend APIs for cab booking service"
  :url "https://github.com/Aman1994/fuber"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [compojure "1.6.1"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-mock "0.3.2"]]
  :plugins       [[lein-ring "0.12.5"]]
  :main ^:skip-aot fuber.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}}
  :ring {:handler fuber.core/handler})
