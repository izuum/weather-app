@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
}

android {
  namespace = "com.github.izuum.weatherapp"
  compileSdk = 34

  defaultConfig {
    applicationId = "com.github.izuum.weatherapp"
    minSdk = 26
    targetSdk = 34
    versionCode = 1
    versionName = "1.0"
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
  kotlinOptions {
    jvmTarget = "1.8"
  }
  buildFeatures {
    viewBinding = true
  }
}

dependencies {
  implementation(libs.android.material)
  implementation(libs.android.swipe)
  implementation(libs.fragments)
  implementation(libs.android.gms)
  implementation(libs.android.appcompat)
  implementation(libs.android.constraint)
  implementation(libs.lifecycle.livedata)
  implementation(libs.lifecycle.viewmodel)

  implementation(libs.retrofit)
  implementation(libs.retrofit.converter.gson)
  implementation(libs.glide)
}
