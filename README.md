shakkuri
======
Shakkuri brings <b>custom tags</b> to Hiccup, a library for representing HTML in Clojure. It uses vectors to represent elements, and maps to represent an element's attributes.

Install
-------
Add the following dependency to your `project.clj` file:

[![clojars version](https://clojars.org/shakkuri/latest-version.svg?raw=true)](https://clojars.org/shakkuri)

The version number will always match Hiccup's.

Why?
-------
After reading [this discussion](https://groups.google.com/forum/#!topic/clojure/2AdBf1ri3do) I got interested on [these custom tags](https://github.com/davesann/hiccup/commit/custom-tags). I think they are awesome because they are abstracted as vectors, just like any other tags. Furthermore, I don't like as much to see `()` representing custom tags because it feels as <em>do something</em>, but `[]` really feels as <em>something</em>. The [map of transform fns proposal](https://groups.google.com/d/msg/clojure/2AdBf1ri3do/WgQs7294jxMJ) is probably a sound improvement because it's independent and explicit about where the custom tags come from. Maybe in the future I'll implement that proposal which only requires adding the map of transform fns to the `html` fn in the user code.

Syntax
------
Examples for custom tags are [here](https://github.com/davesann/hiccup/blob/custom-tags/repl/example.clj) and [here](https://gist.github.com/ccfontes/0c5ff2087de1a498604d).

Here is a basic example of Hiccup syntax:

```clj
user=> (use 'hiccup.core)
nil
user=> (html [:span {:class "foo"} "bar"])
"<span class=\"foo\">bar</span>"
```

The first element of the vector is used as the element name. The second
attribute can optionally be a map, in which case it is used to supply
the element's attributes. Every other element is considered part of the
tag's body.

Hiccup is intelligent enough to render different HTML elements in
different ways, in order to accommodate browser quirks:

```clj
user=> (html [:script])
"<script></script>"
user=> (html [:p])
"<p />"
```

And provides a CSS-like shortcut for denoting `id` and `class`
attributes:

```clj
user=> (html [:div#foo.bar.baz "bang"])
"<div id=\"foo\" class=\"bar baz\">bang</div>"
```

If the body of the element is a seq, its contents will be expanded out
into the element body. This makes working with forms like `map` and
`for` more convenient:

```clj
user=> (html [:ul
               (for [x (range 1 4)]
                 [:li x])])
"<ul><li>1</li><li>2</li><li>3</li></ul>"
```

### Something is missing?
Ask me :)

Feature requests are welcome!

Contributors
------
- [Dave Sann](https://github.com/davesann)
- [James Reeves](https://github.com/weavejester)
- [Carlos Cunha](https://github.com/ccfontes)

License
------
Copyright (C) 2014 Carlos C. Fontes.

Double licensed under the [Eclipse Public License](http://www.eclipse.org/legal/epl-v10.html) (the same as Clojure) or
the [Apache Public License 2.0](http://www.apache.org/licenses/LICENSE-2.0.html).
