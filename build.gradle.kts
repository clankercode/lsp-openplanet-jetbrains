import org.jetbrains.intellij.platform.gradle.TestFrameworkType
plugins {
    java
    id("org.jetbrains.intellij.platform") version "2.5.0"
}
group = "com.clankercode.openplanet"
version = "0.1.0"
repositories {
    mavenCentral()
    intellijPlatform { defaultRepositories() }
}
dependencies {
    intellijPlatform {
        val localIde = providers.gradleProperty("localIde")
        if (localIde.isPresent) local(localIde.get()) else intellijIdeaCommunity("2024.3.6")
        plugin("com.redhat.devtools.lsp4ij", "0.21.0")
        testFramework(TestFrameworkType.Platform)
    }
    testImplementation("junit:junit:4.13.2")
}
java { toolchain.languageVersion.set(JavaLanguageVersion.of(21)) }
tasks.withType<JavaCompile>().configureEach { options.release.set(21); options.encoding = "UTF-8" }
tasks.withType<Test>().configureEach { maxParallelForks = 1; maxHeapSize = "768m"; jvmArgs("-XX:ActiveProcessorCount=2") }
intellijPlatform {
    pluginConfiguration {
        ideaVersion { sinceBuild = "243"; untilBuild = "243.*" }
    }
}
tasks.jar { from(listOf("LICENSE", "LICENSE-CC0", "THIRD_PARTY_NOTICES.md", "third-party")) { into("META-INF") } }

tasks.withType<JavaExec>().configureEach { jvmArgs("-XX:ActiveProcessorCount=2") }
