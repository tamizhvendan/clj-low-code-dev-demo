(ns clj-low-code-dev.demo
  (:require [honeysql.core :as sql]
            [oz.core :as oz]
            [clj-low-code-dev.bar-chart]
            [clj-low-code-dev.list]
            [clj-low-code-dev.widget :refer [widget->query
                                             query-result->html]]
            [clojure.java.jdbc :as jdbc]))

(defn run->query [query]
  (jdbc/query {:dbtype "postgres"
               :dbname "pagila"
               :user "postgres"
               :password "test"} (sql/format query)))

; (defmulti widget->query :type)
; (defmulti query-result->html (fn [widget _] (:type widget)))

;; List Widget

; (defn add-limit [{:keys [limit]} query]
;   (if (some? limit)
;     (assoc query :limit limit)
;     query))

; (defmethod widget->query :list [{:keys [definition]}]
;   (let [{:keys [table attributes]} definition
;         columns (map (comp keyword :name) attributes)
;         base-query {:select columns
;                     :from [(keyword table)]}]
;     (add-limit definition base-query)))

; (defn transform-query-result [{:keys [definition]} results]
;   (let [cols (->> (:attributes definition)
;                   (mapcat #(list (keyword (:name %)) (:display-name %)))
;                   (apply array-map))]
;     (map #(set/rename-keys % cols) results)))

; (defn data->html [data]
;   (let [headers (keys (first data))
;         header-tags (map #(vec [:th %]) headers)
;         td-tags-fn (partial map #(vec [:td %]))
;         rows (map vals data)
;         row-tags (map #(vec [:tr (td-tags-fn %)]) rows)]
;     [:table {:class "table is-bordered"}
;      [:trbody
;       [:tr header-tags]
;       row-tags]]))

; (defmethod query-result->html :list [widget data]
;   (data->html (transform-query-result widget data)))

; ;; List Widget

; ;; Bar Chart Widget

; (defmethod widget->query :bar-chart [{:keys [definition]}]
;   (let [{:keys [table x y]} definition]
;     {:select [(keyword x) (keyword y)]
;      :from [(keyword table)]}))

; (defmethod query-result->html :bar-chart [{:keys [definition]} data]
;   (let [{:keys [x y]} definition]
;     [:vega-lite {:data {:values data}
;                  :mark "bar"
;                  :encoding {:x {:field x :type "ordinal"}
;                             :y {:field y :type "quantitative"}}}]))

;; Bar Chart

(defn widget->html [widget]
  (->> (widget->query widget)
       run->query
       (query-result->html widget)))

(def movie-list
  {:type :list
   :definition {:table "film"
                :limit 2
                :attributes [{:name "title"
                              :display-name "Movie Name"}
                             {:name "description"
                              :display-name "Description"}
                             {:name "release_year"
                              :display-name "Year Relased"}]}})
(def sales-by-category
  {:type :bar-chart
   :definition {:table "sales_by_film_category"
                :x "category"
                :y "total_sales"}})

(def customers
  {:type :list
   :definition {:table "customers"
                :limit 20
                :attributes [{:name "name"
                              :display-name "Name"}
                             {:name "address"
                              :display-name "Address"}]}})

(oz/start-plot-server!)

(oz/view! [:div.container
           [:link {:href "https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.2/css/bulma.min.css"
                   :rel "stylesheet"}]
           [:div.level (widget->html movie-list)]
           [:div.level.card (widget->html sales-by-category)]])