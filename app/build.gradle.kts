@Suppress("DSL_SCOPE_VIOLATION")
plugins {
  alias(libs.plugins.android.application)
  alias(libs.plugins.kotlin.android)
}

android {
  namespace = "com.github.izuum.weatherapp"
  compileSdk = 33

  defaultConfig {
    applicationId = "com.github.izuum.weatherapp"
    minSdk = 26
    targetSdk = 33
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
  implementation ("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-gson:2.9.0")

  implementation("androidx.fragment:fragment-ktx:1.6.1")
  implementation("com.google.android.gms:play-services-location:21.0.1")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
}
