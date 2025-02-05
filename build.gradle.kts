plugins {
    `java-library`

    `maven-publish`
}

group = "me.aroze.colornames"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.7.1")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}

val JAVA_VERSION = 21

java {
    withJavadocJar()
    withSourcesJar()

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(JAVA_VERSION))
    }

    tasks {
        withType<JavaCompile> {
            options.encoding = "UTF-8"
            options.release.set(JAVA_VERSION)
        }

        javadoc {
            (options as StandardJavadocDocletOptions)
                .tags("apiNote:a:API:", "implSpec:a:Implementation Requirements", "implNote:a:Implementation Note:")
        }
    }
}

publishing {
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}