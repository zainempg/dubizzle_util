# Project Documentation

## Documentation Links

- [JaCoCo Report](https://zainempg.github.io/dubizzle_util/docs/jacoco/)
- [Dokka Documentation](https://zainempg.github.io/dubizzle_util/docs/dokka/)

## GitHub Packages

To use the packages from this repository, add the following to your `build.gradle.kts` file:

```kotlin
repositories {
    maven {
        url = uri("https://maven.pkg.github.com/zainempg/dubizzle_util")
        credentials {
            username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
            password = project.findProperty("gpr.token") as String? ?: System.getenv("TOKEN")
        }
    }
}

dependencies {
    implementation("com.dubizzle.util:your-package-name:version")
}
