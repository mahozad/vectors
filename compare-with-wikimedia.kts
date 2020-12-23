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

for (readme in readmeFiles()) {
    val vector = findVectorFile(readme)
    val wikiLink = extractWikiLink(readme)
    val wikiVector = getWikiVector(wikiLink)
    val areSynced = compare(vector, wikiVector)
    println("$vector \t $areSynced")
}

waitForUserInputToExit()

fun readmeFiles() = Files
        .find(ROOT, 2, { it, _ -> it.endsWith("README.md") })
        .filter { it.parent !in IGNORED }
        .filter { it.toFile().readText().contains(LINK_LABEL) }
        .collect(Collectors.toList())

fun findVectorFile(readme: Path) = readme.parent.toFile().listFiles().findLast {
    it.name.matches(Regex("""\d+-.*\.svg"""))
}!!

fun extractWikiLink(readme: Path): URL {
    val content = Files.readString(readme)
    var hint = content.indexOf(LINK_LABEL, 0)
    val startIndex = content.indexOf("(", hint) + 1
    val endIndex = content.indexOf(")", startIndex) - 1
    return URL(content.substring(startIndex..endIndex))
}

fun getWikiVector(link: URL): File {
    val input = link.openStream()
    val page = InputStreamReader(input).readText()
    val path = page.substringAfter("<a href=\"$WIKI_BASE_URL").substringBefore("\"")
    val url = URL("$WIKI_BASE_URL$path")
    return Files.createTempFile(null, null).toFile().apply {
        writeBytes(url.readBytes())
    }
}

fun compare(f1: File, f2: File) = f1.readBytes() contentEquals f2.readBytes()

fun waitForUserInputToExit() = System.console()?.readLine()
