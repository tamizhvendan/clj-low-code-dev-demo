(ns clj-low-code-dev.core
  (:require [clj-low-code-dev.demo :refer [app list-widget-defintion]]
            [oz.core :as oz])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (oz/start-plot-server!))
