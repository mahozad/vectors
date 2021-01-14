#!kotlinc -script -jvm-target 11

import java.io.File
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path

val root = Path.of(".")
val ignored = listOf(File("./subpixel-arrangement/"))
val linkLabel = "[Wikimedia page]"
val wikiBaseUrl = "https://upload.wikimedia.org/wikipedia/commons/"
val ansiReset = "\u001B[00m"
val ansiCyan = "\u001B[36m"
val ansiRed = "\u001B[31m"

for (readme in readmeFiles()) {
    val vector = findVectorFile(readme)
    val wikiLink = extractWikiLink(readme)
    val wikiVector = loadWikiVector(wikiLink)
    val areIdentical = compare(vector, wikiVector)
    prettyPrint(vector, areIdentical)
}

waitForUserInputToExit()

fun readmeFiles() = Files
        .find(root, 2, { it, _ -> it.endsWith("README.md") })
        .map(Path::toFile)
        .filter { it.parentFile !in ignored }
        .filter { it.readText().contains(linkLabel) }

fun findVectorFile(readme: File) = readme.parentFile.listFiles().findLast {
    it.name.matches(Regex("[1-9]-.+svg"))
}!!

fun extractWikiLink(readme: File) = URL(readme.readText().substringBetween("$linkLabel(", ")"))

fun loadWikiVector(link: URL): File {
    val page = link.openStream().reader().readText()
    val path = page.substringBetween("<a href=\"$wikiBaseUrl", "\"")
    val url = URL("$wikiBaseUrl$path")
    return createTempFile().apply { writeBytes(url.readBytes()) }
}

fun compare(f1: File, f2: File) = f1.readBytes() contentEquals f2.readBytes()

fun createTempFile() = Files.createTempFile(null, null).toFile()

fun waitForUserInputToExit() = System.console()?.readLine()

fun String.substringBetween(s1: String, s2: String) = substringAfter(s1).substringBefore(s2)

fun prettyPrint(vector: File, areIdentical: Boolean) {
    val name = vector.parentFile.normalize()
    val label = if (areIdentical) "${ansiCyan}identical" else "${ansiRed}different"
    println(String.format("├%-32s┼%9s┤", "─", "─").replace(' ', '─'))
    println(String.format("│%-32s│%9s$ansiReset│", name, label))
}
