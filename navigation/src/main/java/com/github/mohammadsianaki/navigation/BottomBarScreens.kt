package com.github.mohammadsianaki.navigation

import androidx.annotation.StringRes
import java.io.Serializable

sealed class BottomBarScreens(val route: String, @StringRes val stringRes: Int) : Serializable {
    object Home : BottomBarScreens("home", R.string.home)
    object Markets : BottomBarScreens("markets", R.string.markets)
    object Trade : BottomBarScreens("trade", R.string.trade)
    object Wallet : BottomBarScreens("wallet", R.string.wallet)
    object Profile : BottomBarScreens("profile", R.string.profile)

    companion object {
        val screens = listOf(Home, Markets, Trade, Wallet, Profile)
    }
}
