(ns clj-low-code-dev.widget)

(defmulti widget->query :type)
(defmulti query-result->html (fn [widget _] (:type widget)))