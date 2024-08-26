package com.forest.android_clean_architecture.ui.navigations

enum class Root(val route: String) {
    SPLASH("splash"),
    AUTH("auth"),
    MAIN("main"),
}

enum class Main(val route: String) {
    HOME("home"),
    PROFILE("profile"),
}

enum class IgnoreBottom(val route: String) {
    DETAIL("detail"),
}