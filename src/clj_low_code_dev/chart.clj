(ns clj-low-code-dev.chart
  (:require [clj-low-code-dev.widget :refer [widget->query
                                             query-result->html]]))

(defmethod widget->query :chart [{:keys [definition]}]
  (let [{:keys [table x y]} definition]
    {:select [(keyword x) (keyword y)]
     :from [(keyword table)]}))

(defmethod query-result->html :chart [{:keys [definition chart-type]} data]
  (let [{:keys [x y]} definition]
    [:vega-lite {:data {:values data}
                 :mark chart-type
                 :encoding {:x {:field x :type "ordinal"}
                            :y {:field y :type "quantitative"}}}]))