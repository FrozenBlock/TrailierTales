plugins {
	id("net.frozenblock.triangle.fabric")
	id("org.quiltmc.gradle.licenser")
	checkstyle
}

checkstyle {
	configFile = rootProject.file("checkstyle.xml")
	toolVersion = "10.20.2"
}

val githubActions: Boolean = System.getenv("GITHUB_ACTIONS") == "true"
val licenseChecks: Boolean = githubActions

val fabric_loader_version: String by project

val mod_id: String by project
val mod_version: String by project
val minecraft_version: String by project
val protocol_version: String by project
val maven_group: String by project
val archives_base_name: String by project

val fabric_api_version: String by project
val frozenlib_version: String by project
val wilderwild_version: String by project

val modmenu_version: String by project
val cloth_config_version: String by project
val lithium_version: String by project

base {
	archivesName = archives_base_name
}

val release = findProperty("releaseType") == "stable"

version = getModVersion()
group = maven_group

tasks.jar {
	archiveClassifier.set("fabric")
}

fabric {
	dependOn(project(":tt-common"))
	accessWidener(project(":tt-common"))
	dataGen {
		owner = project(":tt-common")
		splitSourceSet("datagen")
	}
}

loom {
	enableTransitiveAccessWideners = true
	interfaceInjection {
		enableDependencyInterfaceInjection = true
	}

	runs {
		named("client") {
			vmArg("-Dfabric-tag-conventions-v2.missingTagTranslationWarning=SILENCED")
			vmArg("-DMC_DEBUG_FROZENLIB_WIND_DISTURBANCES=true")
			vmArg("-DMC_DEBUG_ENABLED=true")
			vmArg("-DMC_DEBUG_FROZENLIB_WIND=true")
		}
		named("server") {
			vmArg("-Dfabric-tag-conventions-v2.missingTagTranslationWarning=SILENCED")
		}
	}
}

repositories {
	flatDir {
		dirs("libs")
	}
}

dependencies {
	implementation("net.fabricmc:fabric-loader:$fabric_loader_version")
	implementation("net.fabricmc.fabric-api:fabric-api:$fabric_api_version")

	// FrozenLib
	api("net.frozenblock:frozenlib-fabric:${frozenlib_version}")

	// Wilder Wild
	implementation("net.frozenblock:wilderwild-fabric:${wilderwild_version}")

	// Mod Menu
	compileOnly("com.terraformersmc:modmenu:$modmenu_version")

	// Cloth Config
	compileOnly("me.shedaniel.cloth:cloth-config-fabric:$cloth_config_version") {
		exclude(group = "net.fabricmc.fabric-api")
		exclude(group = "com.terraformersmc")
	}

	// Lithium
	compileOnly("maven.modrinth:lithium:${lithium_version}-fabric")
}

tasks {
	processResources {
		val properties = mapOf(
			"mod_id" to mod_id,
			"version" to version,
			"protocol_version" to protocol_version,
			"minecraft_version" to "~26.3-",

			"fabric_api_version" to ">=$fabric_api_version",
			"frozenlib_version" to ">=${frozenlib_version.split('-').firstOrNull()}-"
		)

		properties.forEach { (a, b) -> inputs.property(a, b) }

		filesNotMatching(
			listOf(
				"**/*.java",
				"**/sounds.json",
				"**/lang/*.json",
				"**/.cache/*",
				"**/*.accesswidener",
				"**/*.classtweaker",
				"**/*.nbt",
				"**/*.png",
				"**/*.ogg",
				"**/*.mixins.json"
			)
		) {
			expand(properties)
		}
	}

	license {
		if (licenseChecks) {
			rule(rootProject.file("codeformat/HEADER"))

			include("**/*.java")
		}
	}
}

val applyLicenses: Task by tasks
val test: Task by tasks
val runClient: Task by tasks

val sourcesJar: Jar by tasks
val javadocJar: Jar by tasks

java {
	sourceCompatibility = JavaVersion.VERSION_25
	targetCompatibility = JavaVersion.VERSION_25
}

artifacts {
	archives(sourcesJar)
	archives(javadocJar)
}

fun getModVersion(): String {
	var version = "$mod_version-mc$minecraft_version"

	if (!release) {
		version += "-unstable"
	}

	return version
}

val changelogText = run {
	val split = rootProject.file("CHANGELOG.md").readText().split("-----------------")
	check(split.size == 2) { "Malformed changelog" }
	split[1].trim()
}

upload {
	maven {
		name.set("trailiertales-fabric")
	}

	forEach {
		changelog = changelogText
	}

	curseforge {
		dependencies {
			required("fabric-api")
			required("frozenlib")
			optional("modmenu")
			optional("cloth-config")
			optional("wilder-wild")
		}
	}

	modrinth {
		dependencies {
			required("fabric-api")
			required("frozenlib")
			optional("modmenu")
			optional("cloth-config")
			optional("wilder-wild")
		}
	}
}
