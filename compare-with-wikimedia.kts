#!kotlinc -script -jvm-target 11

import java.io.File
import java.io.InputStreamReader
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

val ROOT = Path.of(".")
val IGNORED = listOf(File("./subpixel-arrangement/"))
val LINK_LABEL = "[Wikimedia page]"
val WIKI_BASE_URL = "https://upload.wikimedia.org/wikipedia/commons/"

for (readme in readmeFiles()) {
    val vector = findVectorFile(readme)
    val wikiLink = extractWikiLink(readme)
    val wikiVector = loadWikiVector(wikiLink)
    val areSynced = compare(vector, wikiVector)
    println("$vector \t $areSynced")
}

waitForUserInputToExit()

fun readmeFiles() = Files
        .find(ROOT, 2, { it, _ -> it.endsWith("README.md") })
        .map { it.toFile() }
        .filter { it.parentFile !in IGNORED }
        .filter { it.readText().contains(LINK_LABEL) }
        .collect(Collectors.toList())

fun findVectorFile(readme: File) = readme.parentFile.listFiles().findLast {
    it.name.matches(Regex("""\d+-.*\.svg"""))
}!!

fun extractWikiLink(readme: File) = URL(readme.readText().substringBetween("$LINK_LABEL(", ")"))

fun loadWikiVector(link: URL): File {
    val input = link.openStream()
    val page = InputStreamReader(input).readText()
    val path = page.substringBetween("<a href=\"$WIKI_BASE_URL", "\"")
    val url = URL("$WIKI_BASE_URL$path")
    return createTempFile().apply { writeBytes(url.readBytes()) }
}

fun compare(f1: File, f2: File) = f1.readBytes() contentEquals f2.readBytes()

fun waitForUserInputToExit() = System.console()?.readLine()

fun createTempFile() = Files.createTempFile(null, null).toFile()

fun String.substringBetween(s1: String, s2: String) = substringAfter(s1).substringBefore(s2)
