package com.guilherme.marvelcharacters.data.source.local

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.google.common.truth.Truth.assertThat
import com.guilherme.marvelcharacters.infrastructure.BaseUnitTest
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test

@ExperimentalCoroutinesApi
class NightModeLocalDataSourceImplTest : BaseUnitTest() {

    @RelaxedMockK
    private lateinit var sharedPreferences: SharedPreferences

    @InjectMockKs
    private lateinit var nightModeLocalDataSource: NightModeLocalDataSourceImpl

    @Test
    fun `isDarkModeEnabled - return true when night mode is yes`() {
        every { sharedPreferences.getInt(any(), any()) } returns AppCompatDelegate.MODE_NIGHT_YES

        val result = nightModeLocalDataSource.isDarkModeEnabled()

        assertThat(result).isTrue()
    }

    @Test
    fun `isDarkModeEnabled - return false when night mode is no`() {
        every { sharedPreferences.getInt(any(), any()) } returns AppCompatDelegate.MODE_NIGHT_NO

        val result = nightModeLocalDataSource.isDarkModeEnabled()

        assertThat(result).isFalse()
    }

    @Test
    fun `setDarkModeEnabled - check yes value is saved`() {
        nightModeLocalDataSource.setDarkModeEnabled(isEnabled = true)

        verify { sharedPreferences.edit().putInt(any(), AppCompatDelegate.MODE_NIGHT_YES) }
    }

    @Test
    fun `setDarkModeEnabled - check no value is saved`() {
        nightModeLocalDataSource.setDarkModeEnabled(isEnabled = false)

        verify { sharedPreferences.edit().putInt(any(), AppCompatDelegate.MODE_NIGHT_NO) }
    }

    @Test
    fun `getDarkMode - return value saved`() {
        every { sharedPreferences.getInt(any(), any()) } returns AppCompatDelegate.MODE_NIGHT_YES

        val result = nightModeLocalDataSource.getDarkMode()

        assertThat(result).isEqualTo(AppCompatDelegate.MODE_NIGHT_YES)
    }
}