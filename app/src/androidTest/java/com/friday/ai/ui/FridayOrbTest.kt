package com.friday.ai.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.friday.ai.domain.model.AssistantStatus
import com.friday.ai.ui.components.FridayOrb
import com.friday.ai.ui.theme.FridayTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FridayOrbTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testOrbRenders() {
        composeTestRule.setContent {
            FridayTheme {
                FridayOrb(status = AssistantStatus.READY)
            }
        }
        composeTestRule.onRoot().assertExists()
    }
}
