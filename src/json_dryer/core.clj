(ns json-dryer.core
  (:use
    org.satta.glob
    clojure.walk)
  (:require
    [clojure.data.json :as     json]
    [clojure.tools.cli :refer [parse-opts]]))


(def cli-options
  [["-s" "--source-files VALUE" "source files pattern"                          :default nil]
   ["-t" "--target-file VALUE"  "target file to store abstracted commonalities" :default nil]])

(defn json-string-to-map
  [json-string]
  (json/read-str json-string :key-fn keyword))

(defn get-json-branch-names
  [json]
  nil)

(defn extract-json
  [file]
  (json/read-str (slurp file) :key-fn keyword))

(defn flatten-map
  "Flattens the keys of a nested into a map of depth one but
   with the keys turned into vectors (the paths into the original
   nested map)."
  [s]
  (let [combine-map (fn [k s] (for [[x y] (seq s)] {[k x] (seq y)}))]
    (loop [result {}, coll s]
      (if (empty? coll)
        result
        (let [[i j] (first coll)]
          (recur (into result (combine-map i j)) (rest coll)))))))


(defn hash-json
  [json]
  (flatten-map json))


(defn extract-common-hashes
  [hashed-json]
  (let [])
  hashed-json)

(defn get-commonalities
  [jsons]
  (let [jsons-hashes  (map hash-json jsons)]
     (map extract-common-hashes jsons-hashes)))

(defn write-commonalities
  [commonalities]
  (println commonalities))

(defn invalid?
  [commonality]
  false)



(defn -main [& args]
  (let [parsed-options (:options (parse-opts args cli-options))
        commonalities  ((comp get-commonalities extract-json glob) (:source-files parsed-options))]))
        ;  validated-commonalities ((comp (partial remove invalid?)))
       ;   _ (println validated-commonalities)]))


;     (doall (write-commonalities validated-commonalities))))
   ;  (doall (println commonalities))))

