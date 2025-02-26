import com.fasterxml.jackson.module.kotlin.jsonMapper

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "2.1.10"
    id("maven-publish")
    id("org.jetbrains.dokka") version "1.8.10"
    id("jacoco") // Add JaCoCo plugin
    id("org.jetbrains.kotlinx.kover") version "0.9.1"
    id("org.sonarqube") version "4.0.0.2929"



}
jacoco {
    toolVersion = "0.8.8" // Specify the JaCoCo version
}
tasks.koverHtmlReport {
    dependsOn("testDebugUnitTest")
}

sonarqube {
    properties {
        property("sonar.projectKey", "your_project_key")
        property("sonar.organization", "your_organization")
        property("sonar.host.url", "https://sonarcloud.io") // or self-hosted URL
        property(
            "sonar.coverage.jacoco.xmlReportPaths",
            "build/reports/jacoco/jacocoTestReport.xml"
        )

    }
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")

    reports {
        xml.required.set(true)
        html.required.set(true)

    }

    val fileFilter = listOf(
        "**/R.class",
        "**/R\$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*"
    )

    val debugTree = fileTree("${buildDir}/intermediates/javac/debug") {
        exclude(fileFilter)
    }

    val kotlinDebugTree = fileTree("${buildDir}/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }

    val execFile = fileTree(buildDir) {
        include("jacoco/testDebugUnitTest.exec")
    }

    sourceDirectories.setFrom(files("$projectDir/src/main/java"))
    classDirectories.setFrom(files(debugTree, kotlinDebugTree))
    executionData.setFrom(files(execFile))
}

tasks.dokkaHtml.configure {
    outputDirectory.set(buildDir.resolve("dokka"))
}


android {
    namespace = "com.dubizzle.util"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
        targetSdk = 34

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.kotlinx.serialization)
    implementation (libs.kotlinx.serialization.json)


    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)

    testImplementation(libs.mockk)
    testImplementation (libs.test.core.ktx)


}

publishing {

    publications {
        create<MavenPublication>("release") {
            groupId = "com.dubizzle"  // Change to your GitHub username
            artifactId = "util"       // Change to your library name
            version = System.getenv("VERSION_NAME")
                ?.takeIf { it.isNotEmpty() }
                ?.plus("_beta")
                ?: "0.0.6"

            afterEvaluate {
                from(components["release"])
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/zainempg/dubizzle_util")
            credentials {
                val username = System.getenv("GPR_USERNAME") ?: project.findProperty("GPR_USERNAME") as String?
                val password = System.getenv("GPR_TOKEN") ?: project.findProperty("GPR_TOKEN") as String?

                if (username == null || password == null) {
                    throw GradleException("GitHub Packages credentials are not set. Please set GPR_USERNAME and GPR_TOKEN environment variables.")
                }

                this.username = username
                this.password = password
            }
        }
    }
}

