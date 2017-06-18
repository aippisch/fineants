(ns fineants.db.entities
  (:refer-clojure :exclude [update])
  (:require [korma.core :refer :all])
  (:require [fineants.db.db :refer [fadb fadb]]))

(declare users groups)

(defentity users
           (pk :id)
           (table :users)
           (database fadb)
           (entity-fields :id :email :name)
           (many-to-many groups :groupmembers))

(defentity groups
           (pk :id)
           (table :groups)
           (database fadb)
           (entity-fields :name)
           (many-to-many users :groupmembers))

(def users-base (-> (select* users)))

(def users-ordered (-> users-base
                       (order :created)))

(def get-users
  (select users-ordered))

(defn get-user-with-id
  "get user for id"
  [id]
  (select users-ordered
          (where {:id id})))

(defn get-users-with-name
  "get users for name"
  [name]
  (select users-ordered
          (where {:name [like name]})))

(def groups-base (-> (select* groups)))

(def groups-ordered (-> groups-base
                        (order :created)))

(def get-groups
  (select groups-ordered))
