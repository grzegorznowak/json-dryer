(ns json-dryer.core-test
  (:require [clojure.test      :refer :all]
            [clojure.data.json :as     json]
            [json-dryer.core   :refer :all]))

(def json-simple-1
  (json-string-to-map
    "{\"uncommon\" : \"a value\"}"))

(def json-simple-2
  (json-string-to-map
    "{\"something\" : \"something 2\",
      \"common\"    : \"a value that is common\"}"))

(def json-simple-3
  (json-string-to-map
    "{\"something\" : \"something 1\",
      \"common\"    : \"a value that is common\"}"))

(def json-complex-1-string
  "{\"level 1\" : [
        \"level 1 value\",
        {
          \"level 2\" : [
            \"level 2 value\"
          ]
        }
     ]}")

(def json-complex-1
  (json-string-to-map json-complex-1-string))


(def json-commonality-simple
  (json-string-to-map
    "{\"common\": \"a value that is common\"}"))

(deftest test-json-flattening
  (testing "If json flattening works ok"
    (println (flatten (seq json-complex-1)))
    (is (=
          [
           [["level 1"]
            (json-string-to-map
              "{\"level 1 value\",
              \"level 2\" : [
                \"level 2 value\"
              ]}")]
           [[["level 1"] ["level 2"]]
            (json-string-to-map
                "\"{[level 2 value]}\"")]]
          (flatten-map json-complex-1)))))

;(deftest test-extracting-simple-commonalities
;  (testing "If I will be able to denormalize simple jsons"
;    (is
;      (=
;        json-commonality-simple
;        (get-commonalities [json-simple-1 json-simple-2 json-simple-3])))))
