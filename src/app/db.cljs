(ns app.db
  (:require
   [app.activities.db :as activities]))

(def app-db (merge activities/db))
