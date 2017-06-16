(ns fineants.views.layout
  (:require [hiccup.page :as h]))

(defn base [title & body]
  (h/html5
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
    [:meta {:name "viewport" :content
            "width=device-width, initial-scale=1, maximum-scale=1"}]
    [:title title]
    (h/include-css "bower_components/bootstrap/dist/css/bootstrap.min.css")
    #_(h/include-css "http://fonts.googleapis.com/css?family=Sigmar+One&v1")]
   [:body
    [:div {:id "header"}
     [:h1 {:class "container"} "FineAnts"]]
    [:div {:id "content" :class "container"}
     body]]))


;; -----------------------------------------------------------------------------
;; Error Messages

(defn four-oh-four []
  (base "Page Not Found"
        [:img {:src "//www.how-to-draw-funny-cartoons.com/image-files/cartoon-ant-8.gif"}]
        [:div
         "The page you requested could not be found"]))


;; -----------------------------------------------------------------------------
;; Normal Pages

(defn index []
  (base "FineAnts"
        [:h4 "Awesome!"]))
