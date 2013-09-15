(ns starlanes.game.map-test
  (:require [clojure.test :refer :all]
            [starlanes.game.base :as base]
            [starlanes.game.map :as game]
            [starlanes.util :as util]))


(deftest test-update-coords
  (let [game-data (game/update-coords "1a" "+" (base/game-data-factory))]
    (is (= (game-data :star-map) {:a1 "+"}))
    (let [game-data (game/update-coords "02a" "E" game-data)]
      (is (= (game-data :star-map) {:a1 "+", :a02 "E"})))))

(deftest test-create-item
  (is (= (game/create-item 0.04) "*"))
  (is (= (game/create-item 0.05) "*"))
  (is (= (game/create-item 0.06) ".")))

(deftest test-get-star-coords
  (is (= [:c3 :e4 :a1]
         (game/get-star-coords util/fake-game-data))))

(deftest test-get-possible-neighbors
  (is (= [95 96 97] (game/get-possible-neighbors 96)))
  (is (= [122 123 124] (game/get-possible-neighbors 123))))

(deftest test-get-possible-x-neighbors
  (is (= ["a" "b"] (game/get-possible-x-neighbors "a")))
  (is (= ["a" "b" "c"] (game/get-possible-x-neighbors "b")))
  (is (= ["d" "e"] (game/get-possible-x-neighbors "e")))
  (is (= ["e"] (game/get-possible-x-neighbors "f")))
  (is (= [] (game/get-possible-x-neighbors "g"))))

(deftest test-get-possible-y-neighbors
  (is (= [] (game/get-possible-y-neighbors -1)))
  (is (= [1] (game/get-possible-y-neighbors 0)))
  (is (= [1 2] (game/get-possible-y-neighbors 1)))
  (is (= [1 2] (game/get-possible-y-neighbors "1")))
  (is (= [1 2 3] (game/get-possible-y-neighbors 2)))
  (is (= [4 5] (game/get-possible-y-neighbors 5)))
  (is (= [5] (game/get-possible-y-neighbors 6)))
  (is (= [] (game/get-possible-y-neighbors 7))))

(deftest test-get-neighbors-pairs
  (is (= '(("a" 2) ("b" 1) ("b" 2))
         (game/get-neighbors-pairs :a1)))
  (is (= '(("a" 1) ("a" 2) ("a" 3) ("b" 1) ("b" 3) ("c" 1) ("c" 2) ("c" 3))
         (game/get-neighbors-pairs :b2)))
  (is (= '(("d" 4) ("d" 5) ("e" 4))
         (game/get-neighbors-pairs :e5))))

(deftest test-get-neighbors
  (is (= '(:a2 :b1 :b2)
         (game/get-neighbors :a1)))
  (is (= '(:a1 :a2 :a3 :b1 :b3 :c1 :c2 :c3)
         (game/get-neighbors :b2)))
  (is (= '(:d4 :d5 :e4)
         (game/get-neighbors :e5))))

(deftest test-next-to-star?
  (is (= true (game/next-to-star? :a2 util/fake-game-data)))
  (is (= true (game/next-to-star? :b1 util/fake-game-data)))
  (is (= true (game/next-to-star? :b2 util/fake-game-data)))
  (is (= false (game/next-to-star? :a3 util/fake-game-data)))
  (is (= false (game/next-to-star? :e1 util/fake-game-data)))
  (is (= false (game/next-to-star? :e2 util/fake-game-data)))
  (is (= true (game/next-to-star? :d5 util/fake-game-data)))
  (is (= true (game/next-to-star? :e5 util/fake-game-data)))
  (is (= true (game/next-to-star? :d2 util/fake-game-data)))
  (is (= true (game/next-to-star? :d3 util/fake-game-data))))