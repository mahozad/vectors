![The vector graphic](2-vectorized.svg)

Used on [this page](https://en.wiktionary.org/wiki/protractor)

To create the degree markers, create the first left marker (at 0 degree)
and then from `Path effects` choose `Rotate copies`. In the properties of
this path effect set the x and y to the x and y of the center of the main
green circle and set the number of copies to 360. 

For the numbers (text) that does not support path effects, set the rotation
center of the text to the center of the main green circle and then rotate it
with transform tool(before doing this make sure to set the alignment of the
text to middle so if the text is changed it remains centered).
