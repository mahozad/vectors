![The vector graphic](6-filled.svg)

Used in [this wikipedia page](https://hu.wikipedia.org/wiki/Fonalgrafika)

Every line in a circle is different from the next line by 15 degrees

We can reuse the elements by embedding theme in a `def` element and 
then take advantage of `use` element

We can set the rotation center of the `rotate` transform in SVG `transform` element:

    <use transform="rotate(45 256 256)" xlink:href="#template"/>

`... 256 256` is the rotation center in the above code
