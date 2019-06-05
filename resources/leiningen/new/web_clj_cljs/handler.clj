(ns {{handler-ns}}
  (:require [clojure.java.io :as io]
            [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [ring.middleware.json :refer [wrap-json-params wrap-json-response]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [GET POST DELETE defroutes]]
            [compojure.route :as compojure-route]))

(defroutes http-routes
  (GET "/" [] (io/resource "public/index.html"))
  (compojure-route/not-found "<h1>Page not found</h1>"))

(def http-app
  (-> #'http-routes
      (wrap-reload)
      (wrap-resource "public")
      (wrap-defaults
       (-> site-defaults
           (assoc-in [:security :anti-forgery] false)))
      (wrap-json-params)
      (wrap-json-response)))

(defonce ^:private http-server (atom nil))

(defn stop-http-server []
  (when @http-server (.stop @http-server)))

(defn start-http-server
  "启动http server，join控制是否在当前线程阻塞"
  [& {:keys [join? port]
      :or {join? false port 19880}}]
  (stop-http-server)
  (reset! http-server
          (run-jetty #'http-app {:join? join? :port port})))
