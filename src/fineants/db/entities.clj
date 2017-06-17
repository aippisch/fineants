(ns fineants.db.entities)
(use 'korma.core)
(require '[fineants.db.db :refer [fadb fadb]])

(declare users groups)

(defentity users
           (pk :id)
           (table :users)
           (database fadb)
           (entity-fields :email :name)
           (many-to-many groups :groupmembers))

(defentity groups
           (pk :id)
           (table :groups)
           (database fadb)
           (entity-fields :name)
           (many-to-many users :groupmembers))
