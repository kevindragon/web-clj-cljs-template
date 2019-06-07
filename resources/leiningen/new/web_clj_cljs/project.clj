(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [ring "1.7.1"]
                 [compojure "1.6.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-json "0.4.0"]
                 [org.clojure/clojurescript "1.10.520"]]
  :main ^:skip-aot {{namespace}}
  :source-paths ["src/clj"]
  :target-path "target/%s"
  :plugins [[lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]
            [lein-figwheel "0.5.18"]]
  :clean-targets ^{:protect false} ["resources/public/js" "target"]
  :cljsbuild
  {:builds [{:id "dev"
             :source-paths ["src/cljs"]
             :compiler {:main {{namespace}}
                        :output-to "resources/public/js/app.js"
                        :output-dir "resources/public/js/out"
                        :asset-path "js/out"
                        :optimizations :none
                        :source-map true
                        :pretty-print true}}
            {:id "min"
             :source-paths ["src/cljs"]
             :compiler {:main {{namespace}}
                        :output-to "resources/public/js/app.js"
                        :optimizations :advanced}}]}
  :profiles
  {:provided {:dependencies [[reagent "0.8.1"]]}
   :dev {:dependencies [[figwheel-sidecar "0.5.18"]
                        [cider/piggieback "0.4.1"]]
         :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}}
   :uberjar {:aot :all
             :prep-tasks ["clean" ["cljsbuild" "once" "min"] "compile"]}})
