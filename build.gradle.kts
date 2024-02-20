plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "net.uhuli.survivalextended"
version = "1.0"

repositories {
    mavenCentral()
    maven {
        setUrl ("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        setUrl ("https://repo.dmulloy2.net/repository/public/")
    }
}

dependencies {
    compileOnly ("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    implementation("org.spongepowered:configurate-hocon:4.1.2")
    implementation("dev.triumphteam:triumph-gui:3.1.7")
    compileOnly ("com.comphenix.protocol:ProtocolLib:5.1.0")
}

val javaTarget = 17
java {
    sourceCompatibility = JavaVersion.toVersion(javaTarget)
    targetCompatibility = JavaVersion.toVersion(javaTarget)
}

tasks.processResources {
    from("src/main/resources") {
        include("plugin.yml")
        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        expand (
                "version" to project.version
        )
    }
}

tasks.withType(JavaCompile::class).configureEach {
    options.apply {
        encoding = "utf-8"
    }
}

tasks.withType(AbstractArchiveTask::class).configureEach {
    isReproducibleFileOrder = true
    isPreserveFileTimestamps = false
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

tasks.jar {
    archiveClassifier.set("nonshadow")
}

tasks.shadowJar {
    archiveClassifier.set("")
    relocate ("org.spongepowered", "com.technicjelle.spongeapi")
    relocate("dev.triumphteam.gui", "net.uhuli.itemstorage.gui")
}
