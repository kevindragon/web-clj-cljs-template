(ns {{namespace}}
  (:require [reagent.core :as r]))

(defn main []
  [:h1 "Hello World!"])

(defn ^:export run []
  (r/render [main]
            (js/document.getElementById "app")))
