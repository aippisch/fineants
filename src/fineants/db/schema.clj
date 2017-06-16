(ns fineants.db.schema
  (:require [clojure.java.jdbc :as sql]))

(def connect-string "postgresql://fineants:YhTv2ejHdtK9f6i3q6bq2hCeRK@localhost:5432/fineants")

(def users
  [[:id :serial "PRIMARY KEY"]
   [:email :varchar "NOT NULL"]
   [:name :varchar "NOT NULL"]
   [:created :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]])

(defn migrate []
  (print "Creating database structure...") (flush)
  (sql/db-do-commands connect-string
                      (sql/create-table-ddl
                        :users users))
  (println " done"))

(def someuser
  {:email "ippisch@cs.uni-duesseldorf.de"
   :name  "Andre Ippisch"})

(defn insert [newuser]
  (sql/insert! connect-string :users newuser))

(defn query []
  (into [] (sql/query connect-string ["select * from users order by id asc"])))