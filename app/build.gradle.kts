plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {

    namespace = "com.macsoftware.event"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.macsoftware.event"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

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
    packagingOptions {

        exclude("META-INF/DEPENDENCIES")
        /*'META-INF/DEPENDENCIES'
        exclude() 'META-INF/NOTICE'
        exclude()'META-INF/LICENSE'
        exclude() 'META-INF/LICENSE.txt'
        exclude() 'META-INF/NOTICE.txt'
        */
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildToolsVersion = "33.0.1"
}

dependencies {



    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.firebase:firebase-database-ktx:20.2.2")
    implementation("com.google.firebase:firebase-firestore-ktx:24.7.1")
    implementation("com.google.api-client:google-api-client-android:2.2.0")

    implementation("com.google.api-client:google-api-client:2.2.0")
    implementation("com.google.oauth-client:google-oauth-client-jetty:1.34.1")
    implementation("com.google.apis:google-api-services-sheets:v4-rev581-1.25.0")
    implementation("com.google.http-client:google-http-client-gson:1.43.3")

    implementation("com.fasterxml.jackson.core:jackson-core:2.15.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.15.2")
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("com.google.http-client:google-http-client-android:1.43.3")

    implementation("com.google.api-client:google-api-client:2.2.0")
    implementation("com.google.api-client:google-api-client-android:2.2.0")
    implementation("com.google.api-client:google-api-client-gson:2.2.0")
    implementation("com.google.apis:google-api-services-sheets:v4-rev581-1.25.0")

    implementation("com.google.apis:google-api-services-drive:v3-rev20230822-2.0.0")
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    implementation("com.google.http-client:google-http-client-jackson2:1.43.3")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.google.zxing:core:3.4.0") // Zxing temel kütüphane
    implementation("com.journeyapps:zxing-android-embedded:4.2.0") // Zxing Android için entegre edilmiş kütüphane

}