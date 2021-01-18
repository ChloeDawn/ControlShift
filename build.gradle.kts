import org.gradle.util.GradleVersion
import java.time.Instant

plugins {
  id("fabric-loom") version "0.6.22"
  id("net.nemerosa.versioning") version "2.6.1"
  id("signing")
}

group = "dev.sapphic"
version = "1.2.0"

java {
  withSourcesJar()
}

repositories {
  maven("https://files.minecraftforge.net/maven/")
}

dependencies {
  minecraft("com.mojang:minecraft:1.16.5")
  mappings(minecraft.officialMojangMappings())
  modImplementation("net.fabricmc:fabric-loader:0.11.1")
  implementation("com.google.code.findbugs:jsr305:3.0.2")
  implementation("org.jetbrains:annotations:20.1.0")
  implementation("org.checkerframework:checker-qual:3.9.0")
  compileOnly("net.minecraftforge:forge:1.16.5-36.0.1:universal")
}

tasks {
  compileJava {
    with(options) {
      release.set(8)
      isFork = true
      isDeprecation = true
      encoding = "UTF-8"
      compilerArgs.addAll(listOf("-Xlint:all", "-parameters"))
    }
  }

  processResources {
    filesMatching("/fabric.mod.json") {
      expand("version" to project.version)
    }
  }

  jar {
    from("/LICENSE")

    manifest.attributes(
      "Build-Timestamp" to Instant.now(),
      "Build-Revision" to versioning.info.commit,
      "Build-Jvm" to "${
        System.getProperty("java.version")
      } (${
        System.getProperty("java.vendor")
      } ${
        System.getProperty("java.vm.version")
      })",
      "Built-By" to GradleVersion.current(),

      "Implementation-Title" to project.name,
      "Implementation-Version" to project.version,
      "Implementation-Vendor" to project.group,

      "Specification-Title" to "ForgeMod",
      "Specification-Version" to "1.0.0",
      "Specification-Vendor" to project.group,

      "MixinConfigs" to "dev/sapphic/controlshift/mixins.json"
    )

    exclude(minecraft.getRefmapName())
  }

  assemble {
    dependsOn(versionFile)
  }
}

if (hasProperty("signing.mods.keyalias")) {
  val alias = property("signing.mods.keyalias")
  val keystore = property("signing.mods.keystore")
  val password = property("signing.mods.password")

  listOf(tasks.remapJar, tasks.remapSourcesJar).forEach {
    it.get().doLast {
      val file = outputs.files.singleFile
      ant.invokeMethod(
        "signjar", mapOf(
          "jar" to file,
          "alias" to alias,
          "storepass" to password,
          "keystore" to keystore,
          "verbose" to true,
          "preservelastmodified" to true
        )
      )
      signing.sign(file)
    }
  }
}
