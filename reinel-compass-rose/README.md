![The vector graphic](2-reworked.svg)

[Wikimedia page](https://commons.wikimedia.org/wiki/File:Compass_rose_pale.svg)

For the clones of an object (`use` elements) to be able to set a fill or stroke,
the original object should not specify the fill or stroke (not even with `none` value).

If we want to use the original object itself, we can create another `use` element for it.
