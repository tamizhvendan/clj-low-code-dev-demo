(ns clj-low-code-dev.list
  (:require [clj-low-code-dev.widget :refer [widget->query
                                             query-result->html]]
            [clojure.set :as set]))

(defn add-limit [{:keys [limit]} query]
  (if (some? limit)
    (assoc query :limit limit)
    query))

(defmethod widget->query :list [{:keys [definition]}]
  (let [{:keys [table attributes]} definition
        columns (map (comp keyword :name) attributes)
        base-query {:select columns
                    :from [(keyword table)]}]
    (add-limit definition base-query)))

(defn transform-query-result [{:keys [definition]} results]
  (let [cols (->> (:attributes definition)
                  (mapcat #(list (keyword (:name %)) (:display-name %)))
                  (apply array-map))]
    (map #(set/rename-keys % cols) results)))

(defn data->html [data]
  (let [headers (keys (first data))
        header-tags (map #(vec [:th %]) headers)
        td-tags-fn (partial map #(vec [:td %]))
        rows (map vals data)
        row-tags (map #(vec [:tr (td-tags-fn %)]) rows)]
    [:table {:class "table is-bordered"}
     [:trbody
      [:tr header-tags]
      row-tags]]))

(defmethod query-result->html :list [widget data]
  (data->html (transform-query-result widget data)))