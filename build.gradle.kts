plugins {
    `java-library`
}

repositories {
    mavenLocal()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

java{
    toolchain{
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

dependencies {
    api("org.springframework.boot:spring-boot-starter:3.2.0")
    api("org.springframework.boot:spring-boot-starter-jersey:3.2.0")
    api("org.springframework.boot:spring-boot-starter-data-jpa:3.2.0")
    api("org.postgresql:postgresql:42.2.24")

    testImplementation("org.springframework.boot:spring-boot-starter-test:3.2.0")
    testImplementation("org.glassfish.jersey.test-framework.providers:jersey-test-framework-provider-grizzly2:2.27")


    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
}

tasks.withType<Test> {
    useJUnitPlatform()
}



group = "com.citizen"
version = "0.0.1-SNAPSHOT"
description = "Citizen App"

