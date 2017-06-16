(ns fineants.web
  (:require [compojure.core :refer [defroutes GET]]
            [compojure.route :as route]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.adapter.jetty :as ring]
            [fineants.views.layout :as layout]))

(defroutes routes
  (GET "/" [] (layout/index))
  (route/not-found (layout/four-oh-four)))

(def application
  (wrap-defaults routes site-defaults))

(defn start [port]
  (ring/run-jetty application {:port port
                               :join? false}))

(defn main []
  (start 8080))
;; (main)
