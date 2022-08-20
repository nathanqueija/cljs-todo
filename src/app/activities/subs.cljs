(ns app.activities.subs
  (:require
   [app.activities.core :as activities.core]
   [re-frame.core :as rf]))


(rf/reg-sub
 :activities/data
 (fn [db]
   (activities.core/data db)))

(rf/reg-sub
 :activities/status
 (fn [db]
   (activities.core/status db)))

