plugins {
    id("com.android.application")
}

android {
    namespace = "com.praktisi.movieapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.praktisi.movieapp"
        minSdk = 34
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "API_KEY", "\"55cbb5d181db709334261b4845e94912\"")
        buildConfigField("String", "URL_IMG", "\"https://image.tmdb.org/t/p/w500\"")
        buildConfigField("String", "URL_IMG_ITEM", "\"https://image.tmdb.org/t/p/w185\"")
    }

    buildFeatures {
        buildConfig = true
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation(libs.paging.runtime)
    val nav_version = "2.7.7"
    val room_version = "2.6.1"
    val lifecycle_version = "2.7.0"
    val lottieVersion = "3.4.0"

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // *** For Navigation ***
    //noinspection UseTomlInstead
    implementation("androidx.navigation:navigation-fragment:$nav_version")
    //noinspection UseTomlInstead
    implementation("androidx.navigation:navigation-ui:$nav_version")

    // *** For SmartTabLayout ***
    //noinspection UseTomlInstead
    implementation("com.ogaclejapan.smarttablayout:library:2.0.0@aar")
    //noinspection UseTomlInstead
    implementation("com.ogaclejapan.smarttablayout:utils-v4:2.0.0@aar")

    // *** For Room SQLite ***
    //noinspection UseTomlInstead
    implementation("androidx.room:room-runtime:$room_version")
    //noinspection UseTomlInstead
    annotationProcessor("androidx.room:room-compiler:$room_version")

    // *** For MaterialRatingBar ***
    //noinspection UseTomlInstead
    implementation("me.zhanghai.android.materialratingbar:library:1.4.0")

    //noinspection UseTomlInstead
    implementation("com.google.code.gson:gson:2.10.1")

    // ViewModel
    //noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycle_version")
    //noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycle_version")
    //noinspection UseTomlInstead
    implementation("androidx.lifecycle:lifecycle-runtime:$lifecycle_version")

    // *** For Retrofit ***
    //noinspection UseTomlInstead
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    //noinspection UseTomlInstead
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    //noinspection UseTomlInstead
    implementation("com.squareup.okhttp3:logging-interceptor:3.12.1")

    // *** For Glide ***
    //noinspection UseTomlInstead
    implementation("com.github.bumptech.glide:glide:4.12.0")
    //noinspection UseTomlInstead
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    // *** For Skeleton ***
    //noinspection UseTomlInstead
    implementation("com.faltenreich:skeletonlayout:5.0.0")

    implementation("com.airbnb.android:lottie:$lottieVersion")
}