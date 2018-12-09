(ns user
  (:require
   [game-geek.schema :as gg-schema]
   [com.walmartlabs.lacinia :as lacinia]
   [clojure.walk :as walk])
  (:import
   (clojure.lang IPersistentMap)))

(def schema (gg-schema/load-schema))

(defn simplify
  [m]
  (walk/postwalk
   (fn [node]
     (cond
       (instance? IPersistentMap node) (into {} node)
       (seq? node) (vec node)
       :else node))
   m))

(defn q
  [query-string]
  (-> (lacinia/execute schema query-string nil nil)
      simplify))
