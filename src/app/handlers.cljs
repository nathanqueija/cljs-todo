(ns app.handlers
  (:require [app.db :as db]
            [re-frame.core :as rf]
            app.activities.handlers))

(rf/reg-event-fx
 :app/launched
 (fn [_ _]
   {:db db/app-db
    :activities/fetch nil}))
