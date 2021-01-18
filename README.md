![logo](./symbol/2-optimized.svg)

Arrow character: 🡲  
xlink namespace: xmlns:xlink="http://www.w3.org/1999/xlink"

### Tips and Tricks

 - To create curved pipes or lines there are two ways:
     1. Draw a thick stroke and then convert it to path
     2. Draw two circles for the joint and then converting them to objects

 - To create an angled object especially if it is composed of multiple sub-objects, it is
   easier to create it in straight horizontal or vertical mode and then rotate it (or the group)

---

The script can be executed by:
  - Linux: making the file executable (giving it *x* permission) and double-clicking or
    typing `script.kts` in terminal (which when sees the shebang, uses it to run the script)
  - Windows: associate *.kts* files with Kotlin (*kotlin.bat*).
    If you want to add arguments to Kotlin, modify registry
    `HKEY_CLASSES_ROOT\Applications\kotlinc.bat\shell\open\command` 🡲 `(Default)` key
    and add the desired arguments (for example`-jvm-target 1.8` if we are using Java features beyond version 1.6; can also be 9, 10, etc.):

    `"C:\Program Files\kotlinc\bin\kotlin.bat" -jvm-target 11 "%1"`

Some notes:
  - To enable IDEA highlighting the script name should end with `.main.kts`
  - To prevent false IDEA warnings and errors, set the target language level in
    `Settings` 🡲 `Build, Execution, Deployment` 🡲 `Kotlin Compiler` 🡲 `Target JVM version`

See [this](https://stackoverflow.com/a/7574585) and [this](https://superuser.com/q/361816) for more info

---

To enable ASCII colors in CMD go to `HKEY_CURRENT_USER\Console\` 🡲 `VirtualTerminalLevel`
and set its value to `1` (create this key with type *DWORD* if it does not exist)

---

[This page](https://commons.wikimedia.org/wiki/Template:Convert_to_SVG) is for *Convert to SVG* template that has links (labeled category) to images that should be converted to SVG

---

See [the sample marker file](marker.svg) for how to create a simplified marker.
The marker orientation is aligned with its x axis and its intended beginning
(that is where it should connect to the path) is placed on (0,0) coordinate.

Use it like this:

    <marker id="arrow" overflow="visible" orient="auto">
      <path d="..." fill="#000"/>
    </marker>

---

[Wikimedia SVG checker](https://commons.wikimedia.org/wiki/Commons:Commons_SVG_Checker)
[SVG Support table](https://razrfalcon.github.io/resvg-test-suite/svg-support-table.html)
Wikimedia uses *librsvg* for creating SVG previews

Scheme vs Schema:  
The word **Schema** is used for diagrams and models.

To reference a file or article from wikipedia in Commons, do like this:

    [[:en:Image:Exceptional newcomer.jpg|blah blah blah]]

It seems that browsers do NOT (yet) support mesh gradients

---

For help on how to use SVG images on GitHub see [this post](https://stackoverflow.com/q/13808020)

---

For how to show RTL language along paths see [this post](https://stackoverflow.com/q/24849981/)

---

To reference files from other wikipedia languages prefix its name with the language code.
For example [[fa:logo.svg]]. See [this help page](https://en.wikipedia.org/wiki/Help:Interlanguage_links) for more info

SVGs could be cleaned with [this tool](https://github.com/RazrFalcon/svgcleaner)

For information about how to be a graphic contributor on wikipedia refer [here](https://en.wikipedia.org/wiki/Wikipedia:Graphics_Lab)

For help on how to use SVG files on wikipedia refer [here](https://commons.wikimedia.org/wiki/Help:SVG)

Supported fonts in SVG files on Wikipedia are listed [here](https://meta.wikimedia.org/wiki/SVG_fonts)
and [here](https://en.wikipedia.org/wiki/Wikipedia:Typography)

The default sans-serif font of the Inkscape is `Verdana`

Use {{Vector version available|NewImage.svg}} template in old raster pages to show that the vector version is available
Use {{superseded|File:NewImage.svg}} template to indicate the new version of the image should be used
Use {{Derived from|OldImage.jpg|display=50}} or {{ef|OldImage.jpg}} templates to show that the vector is derived from another image.

---

To prevent subtle discrepancies when resizing the whole vector,
first ungroup all the objects (by pressing `CTRL+SHIFT+G` multiple times)
and then do the resizing.

---

Instead of providing separate SVG files for a multi-part image, the `viewBox` attribute
of the SVG can be used on the same SVG file to show only the needed part.

---

The `{{#language:es}}` and `({{language|es}})` templates can be used to show the name of a language
in user's language or the actual native name.
Could be used e.g. in captions of other versions of the SVG.

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
