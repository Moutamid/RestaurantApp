plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.firebase.crashlytics")

}

android {
    namespace = "com.moutamid.easyroomapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.moutamid.easyroomapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        setProperty("archivesBaseName", "EasyRoomApp-$versionName")
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }

}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.activity:activity:1.9.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.google.firebase:firebase-database:20.2.2")
    implementation("com.airbnb.android:lottie:3.4.0")
    implementation("com.android.volley:volley:1.2.1")
    implementation("com.google.android.gms:play-services-vision:20.1.3")
    implementation("com.google.firebase:firebase-storage:20.2.1")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.fxn769:stash:1.3.2")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.github.Kwasow:BottomNavigationCircles-Android:1.2")
    implementation("com.makeramen:roundedimageview:2.3.0")
    implementation(platform("com.google.firebase:firebase-bom:32.2.3"))
    implementation("com.google.firebase:firebase-crashlytics")
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth:21.0.1")
    constraints {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.0") {
            because("kotlin-stdlib-jdk7 is now a part of kotlin-stdlib")
        }
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.0") {
            because("kotlin-stdlib-jdk8 is now a part of kotlin-stdlib")
        }
    }

    implementation("io.github.glailton.expandabletextview:expandabletextview:1.0.4")
    implementation("com.google.android.gms:play-services-places:17.0.0")
    implementation("com.github.chrisbanes:PhotoView:2.0.0")
    implementation("com.google.android.libraries.places:places:2.4.0")
    implementation("androidx.work:work-runtime:2.7.0")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("com.makeramen:roundedimageview:2.3.0")
    implementation("com.squareup.picasso:picasso:2.71828")
    implementation("com.google.firebase:firebase-messaging:23.0.0")

    implementation("com.firebaseui:firebase-ui-database:7.2.0")
}