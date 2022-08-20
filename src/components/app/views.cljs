(ns components.app.views
  (:require
   [components.activity.views :as activity.views]
   [components.activities-manager.views :as activities-manager.views]
   [re-frame.core :as rf]))

(defn base []
  (let [data @(rf/subscribe [:activities/data])
        status @(rf/subscribe [:activities/status])]
    (condp contains? status
      #{:idle :loading} [:h2 "Loading activities..."]
      #{:loaded} [:div
                  [activities-manager.views/base]
                  [:div.activities-list
                   (map
                    (fn [[id activity]]
                      ^{:key (str id)}
                      [activity.views/base {:activity activity}])
                    data)]]
      [:<>])))
