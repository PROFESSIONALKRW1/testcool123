pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://maven.architectury.dev/") }
        maven { url = uri("https://maven.fabricmc.net/") }
        maven { url = uri("https://maven.neoforged.net/releases") }
        maven { url = uri("https://files.minecraftforge.net/maven/") }
        mavenLocal()
    }
}

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
    id("dev.architectury.loom") version "1.9-SNAPSHOT" apply false // with 1.10 wait for remap fixes on neo/forge}

include(":core")

val coreModules = getProperty("core_modules")!!.split(',').map { it.trim() }

coreModules.forEach { module ->
    include(":loader-$module")

    val project = project(":loader-$module")
    val dir = module.replace("-", "/")
    project.projectDir = file("loader/$dir")
    when (module) {
        "core" -> project.buildFileName = "../loader-core.gradle.kts"
        "fabric-core" -> project.buildFileName = "../../loader-fabric-core.gradle.kts"
        "fabric-15", "fabric-16" -> project.buildFileName = "../../loader-fabric.gradle.kts"
        "forge-fml40", "forge-fml47", "neoforge-fml2", "neoforge-fml4" -> project.buildFileName = "../../loader-forge.gradle.kts"
    }
}

fun getProperty(key: String): String? {
    return settings.extra[key] as? String
}

fun getVersions(key: String): Set<String> {
    return getProperty(key)!!.split(',').map { it.trim() }.toSet()
}

val versions = mapOf(
    "forge" to getVersions("forge_versions"),
    "fabric" to getVersions("fabric_versions"),
    "neoforge" to getVersions("neoforge_versions")
)

val sharedVersions = versions.map { entry ->
    val loader = entry.key
    entry.value.map { "$it-$loader" }
}.flatten().toSet()



    create(rootProject)
}
