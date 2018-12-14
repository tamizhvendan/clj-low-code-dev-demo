(ns clj-low-code-dev.layout
  (:require [clj-low-code-dev.db :refer [run->query]]
            [oz.core :as oz]
            [clj-low-code-dev.widget :refer [widget->query
                                             query-result->html]]))

(defn widget->html [widget]
  (->> (widget->query widget)
       run->query
       (query-result->html widget)))

(defn element->html [{:keys [widget grid-size]}]
  [:div {:class (str "column is-" grid-size)} (widget->html widget)])

(defn row-layout->html [elements]
  (let [col-elements (map element->html elements)]
    [:div.columns col-elements]))

(defn page-layout->html [row-layouts]
  (let [row-elements (map row-layout->html row-layouts)]
    [:div.container row-elements]))


(defn render-page [page-layout]
  (oz/view! [:div
             [:link {:href "https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.2/css/bulma.min.css"
                     :rel "stylesheet"}]
             (page-layout->html page-layout)]))