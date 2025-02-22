plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    kotlin("plugin.serialization") version "2.1.10"
    id("maven-publish")

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
//    androidTestImplementation(libs.espresso.core)

    testImplementation(libs.mockk)
    testImplementation (libs.test.core.ktx)


}

publishing {

    publications {
        create<MavenPublication>("release") {
            groupId = "com.dubizzle"  // Change to your GitHub username
            artifactId = "util"             // Change to your library name
            version = "0.0.1"

            afterEvaluate {
                from(components["release"])
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/zainempg/notification") // Change this

            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: "zainempg"
//                password = System.getenv("GITHUB_TOKEN") ?: "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"
                password = System.getenv("GITHUB_TOKEN") ?: "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"
            }
        }
    }
}