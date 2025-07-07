#!path/to/kotlin -some -params

@file:CompilerOptions("-jvm-target", "21")

import java.io.BufferedReader
import java.io.File
import java.net.URI
import java.net.URL
import java.nio.file.Path
import kotlin.io.path.*

val root = Path(".")
val ignored = listOf(Path("./subpixel-arrangement/"))
val linkLabel = "[Wikimedia page]"
val wikiBaseUrl = "https://upload.wikimedia.org/wikipedia/commons"
val (col1, col2) = Pair(32, 9)
val ansiReset = "\u001B[00m"
val ansiPurple = "\u001B[31m"
val ansiCyan = "\u001B[36m"
val ansiRed = "\u001B[31m"

for (readme in readmeFiles()) {
    val vector = findVectorFile(readme)
    val wikiLink = extractWikiLink(readme)
    val wikiVector = loadWikiVector(wikiLink)
    val areIdentical = wikiVector?.let { compare(vector, wikiVector) }
    prettyPrint(vector, wikiLink, areIdentical)
}

waitForUserInputToExit()

fun readmeFiles() = root
    .toFile()
    .walk()
    .maxDepth(2)
    .filter { it.endsWith("README.md") }
    .map(File::toPath)
    .filter { it.parent !in ignored }
    .filter { linkLabel in it.readText() }

fun findVectorFile(readme: Path) = readme.parent.listDirectoryEntries().findLast {
    it.name matches Regex("[1-9]-.+svg")
}!!

fun extractWikiLink(readme: Path) =
    URI(readme.readText().substringBetween("$linkLabel(", ")")).toURL()

fun loadWikiVector(link: URL): Path? {
    val html = link
        .runCatching { openStream() }
        .getOrNull()
        ?.bufferedReader()
        ?.use(BufferedReader::readText)
        ?: return null
    val path = html.substringBetween("<a href=\"$wikiBaseUrl", "\"")
    val url = URI("$wikiBaseUrl/$path").toURL()
    return createTempFile().apply {
        writeBytes(url.readBytes())
    }
}

fun compare(f1: Path, f2: Path?) = f1.readBytes() contentEquals f2?.readBytes()

fun waitForUserInputToExit() = System.console()?.readLine()

fun String.substringBetween(s1: String, s2: String) =
    substringAfter(s1).substringBefore(s2)

fun prettyPrint(
    vector: Path,
    wikiLink: URL,
    areIdentical: Boolean?
) {
    if (areIdentical == null) {
        println("│%-${col1 + col2}s│".format("${ansiPurple}Not found: $wikiLink$ansiReset"))
    } else {
        val name = vector.parent.normalize()
        val label = if (areIdentical) "${ansiCyan}identical" else "${ansiRed}different"
        println("├%-${col1}s┼%${col2}s┤".format("─", "─").replace(' ', '─'))
        println("│%-${col1}s│%${col2}s│".format(name, label + ansiReset))
    }
}
