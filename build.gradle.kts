import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }
}

group = "rando"
version = "1.0-SNAPSHOT"

allprojects {
    apply(plugin = "kotlin")

    tasks.withType<KotlinCompile>() {
        kotlinOptions.jvmTarget = "11"
    }

    repositories {
        mavenCentral()
        mavenLocal()
    }
}

plugins {
    application
}

application {
    mainClass.set("rando.ApplicationKt")
}

tasks.test {
    useJUnitPlatform()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverHostCommon)
    implementation(libs.ktor.serverNetty)
    implementation(libs.ktor.locations)
    implementation(libs.ktor.webjars)
    implementation(libs.ktor.htmlBuilder)
    implementation(libs.ktor.metricsMicrometer)

    implementation("io.micrometer:micrometer-registry-prometheus:1.8.4")

    implementation("org.webjars.bowergithub.picocss:pico:1.5.0")
    implementation("org.webjars.npm:htmx.org:1.7.0")
    implementation("org.webjars.npm:hyperscript.org:0.9.5")

    implementation("org.hashids:hashids:1.0.3")
    implementation("dev.ustits.krefty:krefty-core:0.3.4")

    implementation(libs.hoplite.core)
    implementation(libs.hoplite.toml)

    implementation(libs.logback.classic)

    testImplementation(libs.ktor.serverTests)

    testImplementation(libs.kotest.runnerJUnit5)
    testImplementation(libs.kotest.assertionsCore)
    testImplementation(libs.kotest.extensionsAssertionsKtor)
}

