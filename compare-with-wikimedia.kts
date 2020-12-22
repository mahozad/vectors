#!kotlinc -script -jvm-target 11

import java.io.File
import java.io.InputStreamReader
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

val ROOT = Path.of(".")
val IGNORED = listOf(Path.of("./subpixel-arrangement/"))
val LINK_LABEL = "Wikimedia page"
val WIKI_BASE_URL = "https://upload.wikimedia.org/wikipedia/commons/"

val readmes = getReadmeFiles()
for (readme in readmes) {
    val vector = findVectorFile(readme)
    val imagePage = extractWikimediaLink(readme)
    val imageUrl = extractRawUrl(imagePage)
    val areSynced = compare(vector, imageUrl)
    println("$vector: $areSynced")
}

fun getReadmeFiles() = Files
        .find(ROOT, 2, { path, _ -> path.endsWith("README.md") })
        .filter { it.parent !in IGNORED }
        .filter { Files.readString(it).indexOf(LINK_LABEL) >= 0 }
        .collect(Collectors.toList())

fun findVectorFile(readme: Path): File? {
    return readme.parent.toFile()
            .listFiles().findLast { it.name.matches(Regex("\\d+-.*\\.svg")) }
}

fun extractWikimediaLink(readme: Path?): URL {
    val content = Files.readString(readme)
    var hint = content.indexOf(LINK_LABEL, 0)
    val startIndex = content.indexOf("(", hint) + 1
    val endIndex = content.indexOf(")", startIndex) - 1
    val url = content.substring(startIndex..endIndex)
    return URL(url)
}

fun extractRawUrl(link: URL): URL {
    val input = link.openStream()
    val page = InputStreamReader(input).readText()
    val url = WIKI_BASE_URL + page.substringAfter("<a href=\"$WIKI_BASE_URL").substringBefore("\"")
    return URL(url)
}

fun compare(offline: File?, online: URL): Boolean {
    // if (offline!!.length() != online.openConnection().getHeaderField("content-Length").toLong()) return false
    return offline!!.readBytes().contentEquals(online.openStream().readAllBytes())
}
