It seems that browser [content security policy](https://content-security-policy.com/) and GitHub [sanitization](https://blobfolio.com/2017/when-a-stranger-calls-sanitizing-svgs/) doesn't allow certain features of SVGs like
scripts or external references in styles (even embedded base64 data) (See [this](https://stackoverflow.com/q/50560054)).

GitHub changelog is available [here](https://github.blog/changelog/).

I made a ticket at [GitHub feedback page](https://support.github.com/contact/feedback). It went as follows:

Hello,

GitHub sanitazation does not allow files to link to content hosted on GitHub itself (not even content in the same repo as the file linking to it).
I will describe what I mean by *linking*.

There are maybe many repositories (especially those that deal with graphic content) that need to show a preview of their work in the README file of repository. Now, SVG is a popular and powerful format with so many features. One is that it can reference and use external resources. So those repositories could leverage this feature to showcase their latest version of work (without having to create a new PNG or JPEG preview every time they update their product).

Consider this SVG (named for example `file.svg`) with the following content:

```xml
<svg viewBox="0 0 600 380" xmlns="http://www.w3.org/2000/svg">
  <style>
    @font-face {
      font-family: Vazir;
      src: url('./dist/Vazir-Regular.woff2') format('woff2');
      font-weight: normal;
    }
  </style>
  <text fill="#cc9900" x="200" y="100" font-family="Vazir" font-size="20">My showcase text</text>
</svg>
```
As you can see this SVG uses an external font located in `./dist/` directory.

If [this repository](https://github.com/rastikerdar/vazir-font) (for *Vazir* font) were to add the above file in the root directory of its repository to showcase the latest version of its font, GitHub and the browser wouldn't load the referenced *Vazir* font in SVG and the browser would complain like this:

> Refused to load the font because it violates the following Content Security Policy directive: "default-src 'none'" ...

This is true even for the favicon of GItHub itself!

![Screenshot](https://github.zendesk.com/attachments/token/SrEUYlfoZ4u4BnhgDMOCEtNPH/?name=Screenshot+%282%29.png)

As I said, there must be many many more repositories that suffer this rather minor issue (like [this](https://github.com/PapirusDevelopmentTeam/papirus-icon-theme) repository that has given up updating its outdated preview shown at the top of the readme which contains older version of its icons).

I do not know the technical and security reasons but it would be a great feature to be able to reference other GitHub resources (at least those in the same repository).

Thank you.
