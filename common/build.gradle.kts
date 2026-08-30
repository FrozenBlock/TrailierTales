plugins {
	id("net.frozenblock.triangle.common")
	id("org.quiltmc.gradle.licenser")
	checkstyle
}

checkstyle {
	configFile = rootProject.file("checkstyle.xml")
	toolVersion = "10.20.2"
}

val frozenlib_version: String by project

val githubActions: Boolean = System.getenv("GITHUB_ACTIONS") == "true"
val licenseChecks: Boolean = githubActions

val applyLicenses: Task by tasks

common {
	accessWidener()
}

neoForge {
	accessTransformers {} // Required for transitive AW to apply!
}

dependencies {
    compileOnly("net.frozenblock:frozenlib-common:${frozenlib_version}")?.let {
        accessTransformers(it)
        interfaceInjectionData(it)
    }
}

tasks {
	license {
		if (licenseChecks) {
			rule(rootProject.file("codeformat/HEADER"))

			include("**/*.java")
		}
	}
}

configurations {
	create("commonJava") {
		isCanBeResolved = false
		isCanBeConsumed = true
	}
	create("commonResources") {
		isCanBeResolved = false
		isCanBeConsumed = true
	}
}

upload.maven {
	name.set("trailiertales-common")
}
