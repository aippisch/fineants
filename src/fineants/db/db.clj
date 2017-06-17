(ns fineants.db.db)
(use 'korma.db)
(require '[clojure.string :as str])

(defdb fadb (postgres {:db         "fineants"
                       :user       "fineants"
                       :password   "YhTv2ejHdtK9f6i3q6bq2hCeRK"
                       :host       "localhost"
                       :port       "5432"
                       :delimiters ""}))
