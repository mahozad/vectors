#!kotlinc -script -jvm-target 11

import java.io.File
import java.io.InputStreamReader
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

val WIKIMEDIA_BASE_URL = "https://upload.wikimedia.org/wikipedia/commons/"
val ignored = listOf(Path.of("./subpixel-arrangement/"))

val readmes = getAllReadmeFiles()
for (readme in readmes) {
    val vector = findVectorFile(readme)
    val imagePage = extractWikimediaLink(readme)
    val imageUrl = extractRawUrl(imagePage)
    val areSynced = compare(vector, imageUrl)
    println("File://$vector: $areSynced ")
}

fun getAllReadmeFiles() = Files.find(Path.of("."), 5,
        { path, _ -> path.toFile().name == "README.md" }
).filter {
    val content = Files.readString(it)
    var index = content.indexOf("Wikimedia page", 0, true)
    return@filter index >= 0
}.filter {
    !ignored.contains(it.parent)
}.collect(Collectors.toList())

fun findVectorFile(readme: Path) = readme.parent.toFile().listFiles().findLast { it.name.matches(Regex("\\d+-.*\\.svg")) }

fun extractWikimediaLink(readme: Path?): URL {
    val content = Files.readString(readme)
    var index = content.indexOf("Wikimedia page", 0, true)
    val startParIndex = content.indexOf("(", index)
    val endParIndex = content.indexOf(")", startParIndex)
    val link = content.substring((startParIndex + 1)..(endParIndex - 1))
    return URL(link)
}

fun extractRawUrl(link: URL): URL {
    val input = link.openStream()
    val page = InputStreamReader(input).readText()
    val url = WIKIMEDIA_BASE_URL + page.substringAfter("<a href=\"$WIKIMEDIA_BASE_URL").substringBefore("\"")
    return URL(url)
}

fun compare(offline: File?, online: URL): Boolean {
    // if (offline!!.length() != online.openConnection().getHeaderField("content-Length").toLong()) return false
    if (offline!!.readBytes().contentEquals(online.openStream().readAllBytes())) {
        return true
    } else {
        return false
    }
}
