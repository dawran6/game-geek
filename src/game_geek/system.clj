(ns game-geek.system
  (:require
   [com.stuartsierra.component :as component]
   [game-geek.schema :as schema]
   [game-geek.server :as server]))

(defn new-system
  []
  (merge (component/system-map)
         (server/new-server)
         (schema/new-schema-provider)))
