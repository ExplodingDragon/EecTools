import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
    kotlin("plugin.serialization") version "1.5.0"
    id("org.openjfx.javafxplugin") version "0.0.9"

}

application{
    mainClass.set("MainKt")
}

java.sourceCompatibility = JavaVersion.VERSION_11

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    implementation("com.github.OpenEdgn:FXUIManager:1a8e067a2d"){
        exclude("com.github.OpenEdgn.Logger4K","core")
    }
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.0")
    implementation(project(":eec-core"))
    implementation("com.github.OpenEdgn.Logger4K:logger-console:1.5.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
    testImplementation("org.junit.platform:junit-platform-launcher:1.6.2")
}

javafx {
    version = "15.0.1"
    modules("javafx.controls", "javafx.fxml")
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




