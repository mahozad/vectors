![Perfect badge](../prefect-vector-badge.svg)
![The vector graphic](6-filled.svg)

[Wikimedia page](https://commons.wikimedia.org/wiki/File:Filled_flower_sewing_pattern.svg)

Every line in a circle is different from the next line by 15 degrees

We can reuse the elements by embedding theme in a `def` element and 
then take advantage of `use` element

We can set the rotation center of the `rotate` transform in SVG `transform` element:

    <use transform="rotate(45 256 256)" xlink:href="#template"/>

`... 256 256` is the rotation center in the above code

---

The `ruler` path effect can be used to mark a path (the petal in this vector)
with equal distances and then using <kbd>CTRL</kbd> + <kbd>SHIFT</kbd> + <kbd>k</kbd> to break apart them for
snapping to work
