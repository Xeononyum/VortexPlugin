plugins {
    id("java")
}

group = "org.ru.vortex"
version = "1.0.0"

repositories {
    mavenCentral()
    maven(url = "https://jitpack.io")
}

dependencies {
    val mindustryVersion = "v140.4"

    compileOnly("com.github.Anuken.Arc:arc-core:$mindustryVersion")
    compileOnly("com.github.Anuken.Mindustry:core:$mindustryVersion")

    implementation("net.dv8tion:JDA:5.0.0-beta.2")
    implementation("com.google.code.gson:gson:2.10")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.19.0")

    implementation("org.mongodb:mongodb-driver-reactivestreams:4.8.1")
    implementation(platform("io.projectreactor:reactor-bom:2020.0.24"))
    implementation(("io.projectreactor:reactor-core"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.jar {
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}