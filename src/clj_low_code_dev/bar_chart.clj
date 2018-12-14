(ns clj-low-code-dev.bar-chart
  (:require [clj-low-code-dev.widget :refer [widget->query
                                             query-result->html]]))

(defmethod widget->query :bar-chart [{:keys [definition]}]
  (let [{:keys [table x y]} definition]
    {:select [(keyword x) (keyword y)]
     :from [(keyword table)]}))

(defmethod query-result->html :bar-chart [{:keys [definition]} data]
  (let [{:keys [x y]} definition]
    [:vega-lite {:data {:values data}
                 :mark "bar"
                 :encoding {:x {:field x :type "ordinal"}
                            :y {:field y :type "quantitative"}}}]))