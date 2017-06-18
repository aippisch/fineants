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
  [[:group :integer "REFERENCES groups (id)"]
   [:user :integer "REFERENCES users (id)"]])

(def transactions
  [[:id :serial "PRIMARY KEY"]
   [:key :integer "UNIQUE"]
   [:group :integer "REFERENCES groups (id)"]
   [:name :varchar "NOT NULL"]
   [:amount :numeric "NOT NULL"]
   [:currency "CHARACTER(3)" "NOT NULL"]
   [:icon :integer "NOT NULL" "DEFAULT 0"]
   [:date :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]
   [:settle :boolean "NOT NULL" "DEFAULT FALSE"]
   [:created :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]])

(def payments
  [[:transaction "REFERENCES transactions (id)"]
   [:user "REFERENCES users (id)"]
   [:amount :numeric "NOT NULL"]])

(def debts
  [[:transaction :integer "REFERENCES transactions (id)"]
   [:user :integer "REFERENCES users (id)"]
   [:amount :numeric "NOT NULL"]])

(def contacts
  [[:user :integer "REFERENCES users (id)"]
   [:contact :integer "REFERENCES users (id)"]
   [:created :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]])

(def receipts
  [[:id :serial "PRIMARY KEY"]
   [:transaction "REFERENCES transactions (id)"]
   [:receipt :varchar "NOT NULL"]
   [:created :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]])

(def comments
  [[:id :serial "PRIMARY KEY"]
   [:transaction "REFERENCES transactions (id)"]
   [:comment :varchar "NOT NULL"]
   [:created :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]])

(defn migrated?
  "check if already migrated"
  [tablename]
  (-> (sql/query connect-string
                 [(str "SELECT COUNT(*) "
                       "FROM information_schema.tables "
                       (format "WHERE table_name='%s'" tablename))])
      first :count pos?))

(def tables
  {:users        users
   :groups       groups
   :groupmembers groupmembers
   :transactions transactions
   :payments     payments
   :debts        debts
   :contacts     contacts
   :receipts     receipts
   :comments     comments})

(defn migrate []
  (print "creating database structure...") (flush)
  (for [[table spec] tables]
    (if (migrated? table)
      (sql/db-do-commands connect-string
                          (sql/create-table-ddl table spec))))
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