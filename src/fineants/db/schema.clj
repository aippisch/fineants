(ns fineants.db.schema)
(require '[clojure.java.jdbc :as sql])

(def connect-string "postgresql://fineants:YhTv2ejHdtK9f6i3q6bq2hCeRK@localhost:5432/fineants")

(def users
  [[:id :serial "PRIMARY KEY"]
   [:email :varchar "NOT NULL"]
   [:name :varchar "NOT NULL"]
   [:created :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]])

(def groups
  [[:id :serial "PRIMARY KEY"]
   [:name :varchar]
   [:created :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]])

(def groupmembers
  [[:group :serial "REFERENCES groups (id)"]
   [:user :serial "REFERENCES users (id)"]])

(defn migrated?
  "check if already migrated"
  [tablename]
  (-> (sql/query connect-string
                 [(str "SELECT COUNT(*) "
                       "FROM information_schema.tables "
                       (format "WHERE table_name='%s'" tablename))])
      first :count pos?))

(defn migrate []
  (print "creating database structure...") (flush)
  (if (migrated? :users)
    (sql/db-do-commands connect-string
                        (sql/create-table-ddl :users users)))
  (if (migrated? :groups)
    (sql/db-do-commands connect-string
                        (sql/create-table-ddl :groups groups)))
  (if (migrated? :groupmembers)
    (sql/db-do-commands connect-string
                        (sql/create-table-ddl :groupmembers groupmembers)))
  (println " done"))

(def someusers
  [{:email "dummy1@dummy.dummy"
    :name  "Dummy 1"}
   {:email "dummy2@dummy.dummy"
    :name  "Dummy 2"}
   {:email "dummy3@dummy.dummy"
    :name  "Dummy 3"}])

(defn insertsomeusers [someusers]
  (sql/insert-multi! connect-string :users someusers))

(defn query []
  (into [] (sql/query connect-string ["SELECT * FROM users ORDER BY id ASC"])))

(migrate)