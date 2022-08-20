(ns app.activities.fx
  (:require [app.activities.core :as activities.core]
            [re-frame.core :as rf]))

(rf/reg-fx
 :activities/fetch
 (fn []
   (rf/dispatch [:activities/fetch-started])
   (-> (activities.core/fetch+)
       (.then (fn [activities]
                (rf/dispatch [:activities/fetch-done activities])))
       (.catch (fn [error]
                 (rf/dispatch [:activities/fetch-failed {:error error}]))))))
