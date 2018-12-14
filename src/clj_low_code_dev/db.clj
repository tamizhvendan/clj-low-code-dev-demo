(ns clj-low-code-dev.db
  (:require [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]))

(defn run->query [query]
  (jdbc/query {:dbtype "postgres"
               :dbname "pagila"
               :user "postgres"
               :password "test"} (sql/format query)))