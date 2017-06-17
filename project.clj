(defproject fineants "0.1.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha17"]
                 [org.clojure/java.jdbc "0.6.1"]
                 [org.postgresql/postgresql "42.1.1"]
                 [ring/ring-jetty-adapter "1.6.1"]
                 [ring/ring-defaults "0.3.0"]
                 [compojure "1.6.0"]
                 [hiccup "1.0.5"]
                 [korma "0.4.3"]]
  :plugins [[lein-kibit "0.1.5"]
            [lein-ancient "0.6.10"]]
  :main ^:skip-aot fineants.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
