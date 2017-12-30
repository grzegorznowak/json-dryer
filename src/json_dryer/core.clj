(ns json-dryer.core
  (:use org.satta.glob)
  (:require
    [clojure.data.json :as json]
    [clojure.tools.cli :refer [parse-opts]]))


(def cli-options
  [["-s" "--source-files VALUE" "source files pattern"                          :default nil]
   ["-t" "--target-file VALUE"  "target file to store abstracted commonalities" :default nil]])

(defn extract-json
  [file]
  (json/read-str (slurp file) :key-fn keyword))

(defn get-commonalities
  [files]
  (map (fn [file] (extract-json file) files)))

(defn write-commonalities
  [commonalities]
  (println commonalities))

(defn invalid?
  [commonality]
  false)

(defn -main [& args]
  (let [parsed-options (:options (parse-opts args cli-options))
        commonalities  ((comp get-commonalities glob) (:source-files parsed-options))
        _              (println commonalities)]))
        ;  validated-commonalities ((comp (partial remove invalid?)))
       ;   _ (println validated-commonalities)]))


  ;   (doall (write-commonalities validated-commonalities))))

