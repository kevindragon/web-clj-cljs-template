(ns leiningen.new.web-clj-cljs
  "Generate a basic web project with cljs."
  (:require [leiningen.new.templates :refer [renderer year date project-name
                                             ->files sanitize-ns name-to-path
                                             multi-segment]]
            [leiningen.core.main :as main]))

(def render (renderer "web-clj-cljs"))

(defn web-clj-cljs
  "A web project template."
  [name & feature-params]
  (println "feature-params")
  (println feature-params)
  (let [main-ns (multi-segment (sanitize-ns name))
        handler-ns (multi-segment (sanitize-ns name) "handler")
        data {:raw-name name
              :name (project-name name)
              :namespace main-ns
              :handler-ns handler-ns
              :nested-dirs (name-to-path main-ns)
              :year (year)
              :date (date)}]
    (main/info "Generating a project called" name
               "based on the 'web-clj-cljs' project.")
    (->files data
             ["project.clj" (render "project.clj" data)]
             ["README.md" (render "README.md" data)]
             [".gitignore" (render "gitignore" data)]
             ["src/{{nested-dirs}}.clj" (render "core.clj" data)]
             [(str "src/" (name-to-path handler-ns) ".clj")
              (render "handler.clj" data)]
             ["test/{{nested-dirs}}_test.clj" (render "test.clj" data)]
             ["resources/public/index.html" (render "index.html" data)])))
