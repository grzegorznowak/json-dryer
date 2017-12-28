(ns json-dryer.core
  (:use org.satta.glob)
  (:require
    [clojure.data.json :as json]
    [clojure.tools.cli :refer [parse-opts]]))

(def cli-options
  [["-s" "--source-files VALUE" "source files pattern"                          :default nil]
   ["-t" "--target-file VALUE"  "target file to store abstracted commonalities" :default nil]])

(def mkw
  println)

(defn get-commonalities
  [files]
  (map (fn [file] ((comp println json/read-str) (slurp file) :key-fn keyword)) files))

(defn write-commonalities
  [commonalities]
  (println commonalities))


(defn invalid?
  [commonality]
  false)

(defn -main [& args]
  (let [parsed-options (:options (parse-opts args cli-options))
        validated-commonalities ((comp (partial remove invalid?) get-commonalities glob)
                                 (:source-files parsed-options))]


     (doall (write-commonalities validated-commonalities))))

