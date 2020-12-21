#!kotlinc -script -jvm-target 11

import java.nio.file.Files
import java.nio.file.Path

val readmes = Files.find(Path.of("."), 5, { path: Path, _ ->
    path.toFile().name == "README.md"
})

readmes.limit(10).forEach { println(it) }
