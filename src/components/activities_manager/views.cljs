(ns components.activities-manager.views
  (:require [reagent.core :as r]
            [re-frame.core :as rf]))


(defn base []
  (r/with-let [adding-activity? (r/atom false)
               activity (r/atom {})
               toggle-activity-panel #(reset! adding-activity? (not @adding-activity?))
               on-change-field (fn [^js e]
                                 (swap!
                                  activity
                                  assoc
                                  (keyword (-> e .-target .-name))
                                  (-> e .-target .-value)))
               on-submit-activity (fn [^js e]
                                    (.preventDefault e)
                                    (toggle-activity-panel)
                                    (rf/dispatch [:activities/add-requested {:activity @activity}]))]
    [:div
     (if @adding-activity?
       [:form {:on-submit on-submit-activity}
        [:input {:name "title" :on-change on-change-field}]
        [:button {:on-click toggle-activity-panel} "Cancel"]
        [:button {:type "submit"} "Add"]]
       [:button {:on-click toggle-activity-panel}
        "Add activity"])]))
