![logo](./symbol/2-optimized.svg)

Scheme vs Schema:  
The word **Schema** is used for diagrams and models.

To reference a file or article from wikipedia in Commons, do like this:

    [[:en:Image:Exceptional newcomer.jpg|blah blah blah]]

It seems that browsers do NOT (yet) support mesh gradients

To reference files from other wikipedia languages prefix its name with the language code.
For example [[fa:logo.svg]]. See [this help page](https://en.wikipedia.org/wiki/Help:Interlanguage_links) for more info

SVGs could be cleaned with [this tool](https://github.com/RazrFalcon/svgcleaner)

For information about how to be a graphic contributor on wikipedia refer [here](https://en.wikipedia.org/wiki/Wikipedia:Graphics_Lab)

For help on how to use SVG files on wikipedia refer [here](https://commons.wikimedia.org/wiki/Help:SVG)

Supported fonts in SVG files on Wikipedia are listed [here](https://meta.wikimedia.org/wiki/SVG_fonts)

Use {{Vector version available|NewImage.svg}} template in old raster pages to show that the vector version is available
Use {{superseded|File:NewImage.svg}} template to indicate the new version of the image should be used
Use {{Derived from|OldImage.jpg|display=50}} template to show that the vector is derived from another image.

---

Instead of providing separate SVG files for a multi-part image, the `viewBox` attribute
of the SVG can be used on the same SVG file to show only the needed part.

---

The `{{language|cs}}` template can be used to show the name of a language in user's language.
Could be user e.g. in captions of other versions of the SVG.

---

For translating SVG files see [this helpful guide](https://commons.wikimedia.org/wiki/Commons:Translation_possible/Learn_more)

---

Guidelines for creating  SVG files are available in [this mozilla page](https://developer.mozilla.org/en-US/docs/Mozilla/Developer_guide/SVG_Guidelines)

---

The xml declaration is not needed at the top of the SVG file:

    <?xml version="1.0" encoding="UTF-8"?>

See [this post](https://stackoverflow.com/q/38169475) for more info

--

If the same exact version of SVG is already available on Wikipedia, then it
won't enable to upload the file. So to see if the vector on Wikipedia is the
latest version or not, just upload the latest version of the vector image there

--

Images would better to have some padding. A good rule of thumb could be 5-15% of
size of the canvas so for size of 512 pixels, 40 pixels seems good.
