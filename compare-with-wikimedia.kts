#!kotlinc -script -jvm-target 11

import java.io.File
import java.io.InputStreamReader
import java.net.URL
import java.nio.file.Files
import java.nio.file.Path

val readmes = Files.find(Path.of("."), 5, { path: Path, _ ->
    path.toFile().name == "README.md"
})

readmes
        .filter {
            val content = Files.readString(it)
            var index = content.indexOf("Wikimedia page", 0, true)
            return@filter index >= 0
        }
        .map {
            val content = Files.readString(it)
            var i = content.indexOf("Wikimedia page", 0, true)
            val startParIndex = content.indexOf("(", i)
            val endParIndex = content.indexOf(")", startParIndex)
            return@map content.substring((startParIndex + 1)..(endParIndex - 1))
        }.forEach {
            val input = URL(it).openStream()
            val page = InputStreamReader(input).readText()
            val url = "https://upload.wikimedia.org/wikipedia/commons/" + page.substringAfter("<a href=\"https://upload.wikimedia.org/wikipedia/commons/").substringBefore("\"")


            val length = URL(url).openConnection().getHeaderField("content-Length").toLong()
            if (length == File("./aim-9-variants/3-optimized.svg").length())
                println("")


            println(url)
        }
