(ns app.activities.core
  (:require [clojure.edn :as edn]))

(defonce activities-url "/data/activities.edn")

(defn fetch-started [db]
  (assoc-in db [:activities :status] :loading))

(defn fetch-done
  [db activities]
  (-> db
      (assoc-in [:activities :status] :loaded)
      (assoc-in [:activities :data] (->> activities
                                         (mapv (fn [{:keys [id] :as activity}]
                                                 [id activity]))
                                         (into {})))))

(defn fetch-failed [db {:keys [error]}]
  (-> db
      (assoc-in [:activities :status] :error)
      (assoc-in [:activities :error] error)))

(defn status [db]
  (get-in db [:activities :status]))

(defn error [db]
  (get-in db [:activities :error]))

(defn data [db]
  (get-in db [:activities :data]))

(defn fetch+
  []
  (-> (js/fetch activities-url)
      (.then #(.text %))
      (.then #(edn/read-string %))))

(defn append [db activity]
  (let [id (random-uuid)]
    (assoc-in db [:activities :data id] (assoc activity :id id))))

(defn edit [db {:keys [id] :as activity}]
  (assoc-in db [:activities :data  id] activity))

(defn delete [db {:keys [id]}]
  (update-in db [:activities :data] dissoc id))
