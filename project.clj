(defproject fuber "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.clojure/math.numeric-tower "0.0.4"]
                 [compojure "1.6.1"]
                 [http-kit "2.3.0"]
                 [ring/ring-defaults "0.3.2"]
                 [clj-http "3.10.3"]]
  :plugins       [[lein-ring "0.12.5"]]
  :main ^:skip-aot fuber.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
