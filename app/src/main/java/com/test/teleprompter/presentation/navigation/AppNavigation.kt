package com.test.teleprompter.presentation.navigation

import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.test.teleprompter.presentation.common.theme.TeleprompterTheme
import com.test.teleprompter.presentation.features.main.MainScreenRoot
import com.test.teleprompter.presentation.features.scenarios.ScenariosScreenRoot
import com.test.teleprompter.presentation.features.video_record.VideoRecordScreenRoot
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.KType
import kotlin.reflect.typeOf

@Composable
fun AppNavigationRoot(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    AppNavigation(
        navController,
        modifier
    )
}

@Composable
fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Main,
            modifier = Modifier.weight(1f)
        ) {
            composableNoTransition<Screen.Main> {
                MainScreenRoot(navController)
            }
            composableNoTransition<Screen.Scenarios> {
                ScenariosScreenRoot(navController)
            }
            composableNoTransition<Screen.RecordVideo>(
                typeMap = mapOf(
                    typeOf<Screen.RecordVideo>() to parcelableType<Screen.RecordVideo>()
                )
            ) {
                val route = it.toRoute<Screen.RecordVideo>()
                VideoRecordScreenRoot(route.scenarioId, navController)
            }
        }
    }
}

@Preview
@Composable
fun AppNavigationPreview(modifier: Modifier = Modifier) {
    TeleprompterTheme {
        AppNavigation(
            navController = rememberNavController()
        )
    }
}

inline fun <reified T : Any> NavGraphBuilder.composableNoTransition(
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    deepLinks: List<NavDeepLink> = emptyList(),
    noinline content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable<T>(
        typeMap = typeMap,
        deepLinks = deepLinks,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        content = content
    )
}

inline fun <reified T : Parcelable> parcelableType(
    isNullableAllowed: Boolean = false,
    json: Json = Json,
) = object : NavType<T>(isNullableAllowed = isNullableAllowed) {
    override fun get(bundle: Bundle, key: String) =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, T::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }

    override fun parseValue(value: String): T = json.decodeFromString(value)

    override fun serializeAsValue(value: T): String = json.encodeToString(value)

    override fun put(bundle: Bundle, key: String, value: T) = bundle.putParcelable(key, value)
}
