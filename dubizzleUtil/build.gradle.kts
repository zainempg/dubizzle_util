plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "2.1.10"
    id("maven-publish")
    id("org.jetbrains.dokka") version "2.0.0" // Add Dokka plugin

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
            artifactId = "util"             // Change to your library name
            version = System.getenv("VERSION_NAME")?.plus("_beta") ?: "0.0.3"

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

tasks.dokkaHtml.configure {
    outputDirectory.set(buildDir.resolve("dokka"))
}