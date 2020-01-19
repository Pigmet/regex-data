(ns regex-data.core
  (:require [clojure.string :refer [join]])
  (:refer-clojure :exclude [and or *  + repeat  set range]))

;;=======constants=======

(def tab #"\t")

(def newline #"\n")

(def backslash #"\\")

(def word #"\w")

(def digit #"\d")

(def non-word #"\W")

(def non-digit #"\D")

(def whitespace #"\s")

(def non-whitespace #"\S")

(def lower #"\p{Lower}")

(def upper #"\p{Upper}")

(def ascii #"\p{ASCII}")

(def alpha #"\p{Alpha}")

(def decimal #"\p{Digit}")

(def alnum #"\p{Alnum}")

(def punct #"\p{Punct}")

(def graph #"\p{Graph}")

(def print #"\p{Print}")

(def blank #"\p{Blank}")

(def cntrl #"\p{Cntrl}")

(def xdigit #"\p{XDigit}")

(def space #"\p{Space}")

(def bol #"^")

(def eol #"$")

(def word-boundary #"\b")

(def non-word-boundary #"\B")

(def beginning-of-input #"\A")

(def end-last-match #"\G")

(def terminator #"\Z")

(def end-of-input #"\z")

(def anything #".")

;;=======operators=======

(defn- make-group-string [s]
  (format "(?:%s)" s))

(defn and [& args]
  (re-pattern  (make-group-string (join (map str args)))))

(defn or [& args]
  (re-pattern  (make-group-string (join "|" (map str args)))))

(def ^:private quantifier-table
  {:greedy ""
   :reluctant "?"
   :possessive "+"})

(defn- seq-op-impl [re op quantifier]
  (re-pattern (str re op (quantifier-table quantifier))))

(defn + [re &{:keys [quantifier] :or {quantifier :greedy}}]
  (seq-op-impl re "+" quantifier))

(defn * [re &{:keys [quantifier] :or {quantifier :greedy}}]
  (seq-op-impl re "*" quantifier))

(defn ? [re &{:keys [quantifier] :or {quantifier :greedy}}]
  (seq-op-impl re "?"quantifier))

(defn repeat [n re &{:keys [quantifier] :or {quantifier :greedy}}]
  (re-pattern
   (format "%s{%s}%s" re n (quantifier-table quantifier))))

(defn at-leat [n re &{:keys [quantifier ] :or {quantifier :greedy}}]
  (re-pattern
   (format "%s{%s,}%s" re n (quantifier-table quantifier))))

(defn repeat-range [low high re &{:keys [quantifier] :or {quantifier :greedy}}]
  (re-pattern
   (format "%s{%s,%s}%s" re low high (quantifier-table quantifier))))

(defn group [re]
  (re-pattern  (format "(%s)" re)))

(defn named-group [s re]
  (re-pattern  (format "(?<%s>%s)" s re)))

(defn set [& args]
  (re-pattern(format "[%s]" (join (map str args)))))

(defn except [& args]
  (re-pattern (format "[^%s]" (join (map str args)))))

(defn range [start end]
  (re-pattern (format "[%s-%s]" start end)))

(comment

  (re-matches (and (+ digit) (* (and "-" (+ digit)))) "090-1230-4433")
  
  )

