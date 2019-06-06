(ns {{namespace}}
  (:require [{{name}}.handler :refer [start-http-server]])
  (:gen-class))

(defn -main [& args]
  (start-http-server))
