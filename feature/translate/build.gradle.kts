plugins {
    id("com.vamsi3.android.screentranslator.gradle.plugin.android-library")
    id("com.vamsi3.android.screentranslator.gradle.plugin.android-library-compose")
    id("com.vamsi3.android.screentranslator.gradle.plugin.android-hilt")
}

android {
    namespace = "com.vamsi3.android.screentranslator.feature.translate"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:model"))
    implementation(project(":core:resource"))
    implementation(project(":feature:settings"))
    implementation(libs.androidx.core)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.hilt.navigation.compose)
}
