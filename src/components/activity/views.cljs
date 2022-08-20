(ns components.activity.views
  (:require [reagent.core :as r]
            [re-frame.core :as rf]))


(defn field [{:keys [name value on-change]}]
  ;; I'm using Reagent atoms here for local state
  ;; One valid option which I prefer would be to use
  ;; functional components and React hooks, I placed
  ;; a file called react_helpers.cljs as an example of
  ;; how I envision them being used.
  (r/with-let [editing? (r/atom false)
               toggle-edit #(reset! editing? (not @editing?))
               current-value (r/atom value)
               on-cancel (fn []
                           (toggle-edit)
                           (on-change name value)
                           (reset! current-value value))
               on-change (fn [^js e]
                           (on-change name (-> e .-target .-value))
                           (reset! current-value (-> e .-target .-value)))
               on-save (fn []
                         (toggle-edit))]

    [:div
     (if @editing?
       [:div
        [:input {:name name :value @current-value :on-change on-change}]
        [:button {:type "button"
                  :on-click on-cancel} "Cancel"]
        [:button {:on-click on-save} "Save"]]
       [:div
        [:span value]
        [:button {:type "button"
                  :on-click toggle-edit} "Edit"]])]))

(defn base [{:keys [activity]}]
  (let [current-activity (r/atom activity)
        on-change-field (fn [name value]
                          (swap! current-activity assoc (keyword name) value))
        on-submit (fn [e]
                    (.preventDefault e)
                    (rf/dispatch [:activities/edit-requested {:activity @current-activity}]))
        on-delete (fn []
                    (rf/dispatch [:activities/delete-requested {:activity @current-activity}]))]
    [:div.activity
     ;; TODO: I'm using a plain HTML form here, but I'd like to create
     ;; an abstracted form component that could be reused. I envision
     ;; it using React context and the input fields connecting to its state
     ;; using React hooks so we wouldn't need to prop drill anything or have
     ;; on-change handlers in every field.
     [:form {:on-submit on-submit}
      [field {:name "title" :value (:title activity) :on-change on-change-field}]]
     [:button {:type "button"
               :on-click on-delete} "Delete"]]))
