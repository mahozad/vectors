#!kotlinc -script -jvm-target 11

import java.io.InputStreamReader
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path
import java.util.stream.Collectors

val ignored = listOf(
        Path.of("./subpixel-arrangement/"),
)

val readmes = Files.find(Path.of("."), 5, { path: Path, _ ->
    path.toFile().name == "README.md"
}).filter {
    val content = Files.readString(it)
    var index = content.indexOf("Wikimedia page", 0, true)
    return@filter index >= 0
}.filter {
    !ignored.contains(it.parent)
}.collect(Collectors.toList())

for (readme in readmes) {
    val vector = sequenceOf(*Files.list(readme.parent).collect(Collectors.toList()).toTypedArray())
            .findLast { it.toFile().name.matches(Regex("\\d+-.*\\.svg")) }

    val content = Files.readString(readme)
    var index = content.indexOf("Wikimedia page", 0, true)
    val startParIndex = content.indexOf("(", index)
    val endParIndex = content.indexOf(")", startParIndex)
    val link = content.substring((startParIndex + 1)..(endParIndex - 1))

    val input = URL(link).openStream()
    val page = InputStreamReader(input).readText()
    val url = "https://upload.wikimedia.org/wikipedia/commons/" + page.substringAfter("<a href=\"https://upload.wikimedia.org/wikipedia/commons/").substringBefore("\"")

    // val length = URL(url).openConnection().getHeaderField("content-Length").toLong()
    val length = URL(url).openStream().readAllBytes().size.toLong()
    print("File://$vector ")
    if (length == vector!!.toFile().length()) {
        println("The same")
    } else {
        println("Not the same")
    }
}
