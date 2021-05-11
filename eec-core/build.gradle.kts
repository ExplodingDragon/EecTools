import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}


java.sourceCompatibility = JavaVersion.VERSION_11

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    implementation("com.github.OpenEdgn.Logger4K:slf4j-over-logger4k:1.5.0")
    api("com.github.OpenEdgn.Logger4K:logger-core:1.5.0")
    implementation(group = "org.http4k", name = "http4k-core", version = "4.6.0.0")
    implementation(group = "org.http4k", name = "http4k-client-apache", version = "4.6.0.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
    testImplementation("org.junit.platform:junit-platform-launcher:1.6.2")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "11"
}




