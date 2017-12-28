(ns json-dryer.core
  (:use org.satta.glob)
  (:require
    [clojure.data.json :as json]
    [clojure.tools.cli :refer [parse-opts]]))

(def cli-options
  [["-s" "--source-files VALUE" "source files pattern"                          :default nil]
   ["-t" "--target-file VALUE"  "target file to store abstracted commonalities" :default nil]])

(defn get-commonalities
  [files]
  (map (fn [file] (json/read-str (slurp "/tmp/test.json") :key-fn keyword)) files))

(defn write-commonalities
  [commonalities]
  (println commonalities))

(defn invalid?
  [commonality]
  false)

(defn -main [& args]
  (let [parsed-options          (:options (parse-opts args cli-options))
        validated-commonalities ((comp (partial remove invalid?) get-commonalities glob)
                                 (:source-files parsed-options))

        ]
    (write-commonalities validated-commonalities)))
