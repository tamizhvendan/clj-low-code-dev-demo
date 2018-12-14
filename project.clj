(defproject clj-low-code-dev "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.postgresql/postgresql "42.2.4"] ; DB Driver for Postgres
                 [honeysql "0.9.3"] ; dynamic SQL generation
                 [hiccup "1.0.5"]
                 [metasoarous/oz "1.3.1"]
                 [org.clojure/java.jdbc "0.7.7"]]
  :main ^:skip-aot clj-low-code-dev.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
