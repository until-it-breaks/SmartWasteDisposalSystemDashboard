plugins {
    // Apply the java plugin to add support for Java
    java

    // Apply the application plugin to add support for building a CLI application
    // You can run your app via task "run": ./gradlew run
    application

    /*
     * Adds tasks to export a runnable jar.
     * In order to create it, launch the "shadowJar" task.
     * The runnable jar will be found in build/libs/projectname-all.jar
     */
    id("com.gradleup.shadow") version "8.3.4"
}

repositories { // Where to search for dependencies
    mavenCentral()
}

dependencies {

}

application {
    // Define the main class for the application.
    mainClass.set("it.unibo.dashboard.Main")
}
