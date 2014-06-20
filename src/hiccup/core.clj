(ns hiccup.core
  "Library for rendering a tree of vectors into a string of HTML.
  Pre-compiles where possible for performance."
  (:use hiccup.compiler
        hiccup.util))

(defmacro html
  "Render Clojure data structures to a string of HTML."
  [options & content]
  (if-let [mode (and (map? options) (:mode options))]
    (binding [*html-mode* mode]
      `(binding [*html-mode* ~mode]
         ~(apply compile-html content)))
    (apply compile-html options content)))

(def ^{:doc "Alias for hiccup.util/escape-html"}
  h escape-html)

(defmacro deftag 
  "create a tag function based on a fn taking key value pairs
   f is (defn [attrs content]) => new hiccup form
     if f returns nil then the tag will be treated as a plain tag
   "
  [tag f]
  (let [tname (name tag)]
    `(defmethod expand-tag ~tname [[_tag# attrs# contents#]]
       (~f attrs# contents#))))

(defmacro deftag-kv [tag f]
  "create a tag function based on a fn taking key value pairs
   f is intended to be (defn [attrs & {destructure}]) => new hiccup form
     if f returns nil then the tag will be treated as a plain tag
     this is useful if you want to pass name value pairs in content
     you dont have to destructure - but in that case it's easier to use deftag
   "
  (let [tname (name tag)]
    `(defmethod expand-tag ~tname [[_tag# attrs# contents#]]
       (apply ~f attrs# contents#))))

(defmacro deftag-c [tag body]
  "create an inline tag definition attrs and content are automatically captured.
     saves typing - but may not be the best idea
   (deftag :my-tag [:my-new-form ... [:blah attrs content])
   "
  (let [tname (name tag)]
    `(defmethod expand-tag ~tname [[~'_tag ~'attrs ~'contents]]
       ~body)))