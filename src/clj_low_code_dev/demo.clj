(ns clj-low-code-dev.demo
  (:require [oz.core :as oz]
            [clj-low-code-dev.bar-chart]
            [clj-low-code-dev.layout :refer [page-layout->html render-page]]
            [clj-low-code-dev.list]))

(def movies-list
  {:type :list
   :definition {:table "film"
                :limit 5
                :attributes [{:name "title"
                              :display-name "Movie Name"}
                             {:name "description"
                              :display-name "Description"}
                             {:name "release_year"
                              :display-name "Year Relased"}]}})

(def sales-by-category-chart
  {:type :bar-chart
   :definition {:table "sales_by_film_category"
                :x "category"
                :y "total_sales"}})

(def customers-list
  {:type :list
   :definition {:table "customers"
                :limit 5
                :attributes [{:name "name"
                              :display-name "Name"}
                             {:name "address"
                              :display-name "Address"}]}})

(def page-layout [[{:widget movies-list :grid-size 6}
                   {:widget sales-by-category-chart :grid-size 6}]
                  [{:widget customers-list :grid-size 12}]])

(oz/start-plot-server!)
(render-page page-layout)