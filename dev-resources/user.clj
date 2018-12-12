(ns user
  (:require
   [com.walmartlabs.lacinia :as lacinia]
   [com.walmartlabs.lacinia.pedestal :as lp]
   [io.pedestal.http :as http]
   [game-geek.system :as system]
   [clojure.walk :as walk]
   [com.stuartsierra.component :as component])
  (:import
   (clojure.lang IPersistentMap)))

(defn simplify
  [m]
  (walk/postwalk
   (fn [node]
     (cond
       (instance? IPersistentMap node) (into {} node)
       (seq? node) (vec node)
       :else node))
   m))

(defonce system (system/new-system))

(defn q
  [query-string]
  (-> system
      :schema-provider
      :schema
      (lacinia/execute schema query-string nil nil)
      simplify))

(defn start
  []
  (alter-var-root #'system component/start-system)
  (browse-url "http://localhost:8888/")
  :started)

(defn stop
  []
  (alter-var-root #'system component/stop-system)
  :stopped)

(comment

  (start)

  (stop)
  )
