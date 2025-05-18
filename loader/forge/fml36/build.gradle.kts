plugins {
    id("net.minecraftforge.gradle") version "5.1.57"
    id("java-library")
}

base {
    archivesName.set("${property("mod_id")}-${project.name}")
}

group = property("mod_group") as String
version = property("mod_version") as String

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}

minecraft {
    mappings("official", property("minecraft_version") as String)
    runs {
        create("client") {
            workingDirectory = "run"
        }
    }
}

dependencies {
    compileOnly(project(":core"))
}
