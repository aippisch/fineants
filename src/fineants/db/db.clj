(ns fineants.db.db
  (:refer-clojure :exclude [update])
  (:require [korma.db :refer :all]))

(defdb fadb (postgres {:db         "fineants"
                       :user       "fineants"
                       :password   "YhTv2ejHdtK9f6i3q6bq2hCeRK"
                       :host       "localhost"
                       :port       "5432"
                       :delimiters ""}))
