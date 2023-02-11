package com.vamsi3.android.screentranslator.core.datastore

import androidx.datastore.core.DataStore
import com.vamsi3.android.screentranslator.core.data.model.ThemeMode
import com.vamsi3.android.screentranslator.core.data.model.TranslateApp
import com.vamsi3.android.screentranslator.core.data.model.UserData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class UserPreferencesDataSource @Inject constructor(
    private val userPreferencesProto: DataStore<UserPreferencesProto>,
) {
    val userData = userPreferencesProto.data
        .map {
            UserData(
                themeMode = when(it.userThemePreference) {
                    null,
                    UserThemePreferenceProto.UNRECOGNIZED,
                    UserThemePreferenceProto.UNSPECIFIED_THEME,
                    UserThemePreferenceProto.SYSTEM -> ThemeMode.SYSTEM
                    UserThemePreferenceProto.DARK -> ThemeMode.DARK
                    UserThemePreferenceProto.LIGHT -> ThemeMode.LIGHT
                },
                translateApp = when(it.userTranslateAppPreference) {
                    null,
                    UserTranslateAppPreferenceProto.UNRECOGNIZED,
                    UserTranslateAppPreferenceProto.UNSPECIFIED_TRANSLATE_APP,
                    UserTranslateAppPreferenceProto.GOOGLE_LENS -> TranslateApp.GOOGLE_LENS
                    UserTranslateAppPreferenceProto.NAVER_PAPAGO -> TranslateApp.NAVER_PAPAGO
                    UserTranslateAppPreferenceProto.DEEPL_TRANSLATE -> TranslateApp.DEEPL_TRANSLATE
                },
            )
        }
        .stateIn(CoroutineScope(Dispatchers.IO), SharingStarted.Eagerly, UserData(
            ThemeMode.SYSTEM,
            TranslateApp.GOOGLE_LENS
        ))

    suspend fun setThemeMode(themeMode: ThemeMode) {
        userPreferencesProto.updateData {
            it.copy {
                this.userThemePreference = when (themeMode) {
                    ThemeMode.SYSTEM -> UserThemePreferenceProto.SYSTEM
                    ThemeMode.LIGHT -> UserThemePreferenceProto.LIGHT
                    ThemeMode.DARK -> UserThemePreferenceProto.DARK
                }
            }
        }
    }

    suspend fun setTranslateApp(translateApp: TranslateApp) {
        userPreferencesProto.updateData {
            it.copy {
                this.userTranslateAppPreference = when (translateApp) {
                    TranslateApp.GOOGLE_LENS -> UserTranslateAppPreferenceProto.GOOGLE_LENS
                    TranslateApp.NAVER_PAPAGO -> UserTranslateAppPreferenceProto.NAVER_PAPAGO
                    TranslateApp.DEEPL_TRANSLATE -> UserTranslateAppPreferenceProto.DEEPL_TRANSLATE
                }
            }
        }
    }
}
