import org.gradle.internal.os.OperatingSystem
import org.gradle.kotlin.dsl.support.zipTo
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    application
    kotlin("plugin.serialization") version "1.5.0"
    id("org.openjfx.javafxplugin") version "0.0.9"
    id("edu.sc.seis.launch4j") version "2.4.9"

}
val mainClassStr = "MainKt"

application {
    mainClass.set(mainClassStr)
}

java.sourceCompatibility = JavaVersion.VERSION_11

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    implementation("com.github.OpenEdgn:FXUIManager:1a8e067a2d") {
        exclude("com.github.OpenEdgn.Logger4K", "core")
    }
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.0")
    implementation(project(":eec-core"))
    implementation("com.github.OpenEdgn.Logger4K:logger-console:1.6.0")
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

tasks.register<Copy>("include-jre") {
    println("I: Include JRE Runtime from System Path.")

    val env = System.getenv()
    when {
        env.containsKey("JRE_HOME") -> {
            from(env["JRE_HOME"])
            println("I: load jre from ${env["JRE_HOME"]} .")
        }
        env.containsKey("JAVA_HOME") -> {
            from(env["JAVA_HOME"])
            println("I: load jre from ${env["JAVA_HOME"]} .")
        }
        else -> {
            throw RuntimeException("Build Fail: JRE Path Not found.")
        }
    }
    into("$buildDir/launch4j/runtime")

}

// 打包
tasks.register("release") {
    dependsOn("createExe", "include-jre")
    doLast {
        val path = "$buildDir/release-${version}.zip"
        zipTo(file(path), file("$buildDir/launch4j"))
        println(
            """
            I:  Release Ver.${rootProject.version}
            I:  package: ${path.replace("\\", "/")}
            I:  size:  ${file(path).length()}
            """.trimIndent()
        )
    }
}

launch4j {
    mainClassName = mainClassStr
    icon = "${projectDir}/icon.ico"
    dontWrapJar = true
    outfile = if (OperatingSystem.current().isWindows) {
        "eec-tools.exe"
    } else {
        "eec-tools"
    }
    headerType = "gui"
    jreRuntimeBits = "64"
    jreMinVersion = "11"
    bundledJre64Bit = true
    libraryDir = "app"
    windowTitle = "ChatBot"
    bundledJrePath = "./runtime"
}
tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}


