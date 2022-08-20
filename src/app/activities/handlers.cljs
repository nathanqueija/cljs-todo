(ns app.activities.handlers
  (:require [app.activities.core :as activities.core]
            [re-frame.core :as rf]))

(rf/reg-event-fx
 :activities/add-requested
 (fn [{:keys [db]} [_ {:keys [activity]}]]
   {:db (-> db
            (activities.core/append activity))}))

(rf/reg-event-fx
 :activities/edit-requested
 (fn [{:keys [db]} [_ {:keys [activity]}]]
   {:db (-> db
            (activities.core/edit activity))}))

(rf/reg-event-fx
 :activities/delete-requested
 (fn [{:keys [db]} [_ {:keys [activity]}]]
   {:db (-> db
            (activities.core/delete activity))}))

(rf/reg-event-fx
 :activities/fetch-started
 (fn [{:keys [db]} _]
   {:db (activities.core/fetch-started db)}))

(rf/reg-event-fx
 :activities/fetch-done
 (fn [{:keys [db]} [_ activities]]
   {:db (activities.core/fetch-done db activities)}))

(rf/reg-event-fx
 :activities/fetch-failed
 (fn [{:keys [db]} [_ {:keys [error]}]]
   {:db (activities.core/fetch-failed db error)}))
