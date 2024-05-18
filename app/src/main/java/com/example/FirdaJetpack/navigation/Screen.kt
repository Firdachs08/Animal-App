package com.example.FirdaJetpack.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorite : Screen("favorite")
    object Profile : Screen("profile")
    object DetailAnimal : Screen("home/{animalId}") {
        fun createRoute(animalId: Int) = "home/$animalId"
    }
}