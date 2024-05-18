package com.example.FirdaJetpack.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.FirdaJetpack.R
import com.example.FirdaJetpack.model.Animal
import com.example.FirdaJetpack.ui.theme.SubmissionComposeTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailScreenKtTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeDataAnimal = Animal(
        id = 1,
        name = "Babi",
        type = "Omnivora",
        image = R.drawable.babi,
        description = "Babi adalah sejenis hewan ungulata yang bermoncong panjang dan berhidung lemper dan merupakan hewan yang aslinya berasal dari Eurasia. Babi merupakan omnivora yang berarti mereka mengonsumsi daging maupun tumbuh-tumbuhan. Ada 3 rumpun babi, yaitu: babi lokal, babi ras/impor, dan babi persilangan. Beberapa rumpun babi lokal, antara lain: Babi Bali, Babi Karawang, Babi Sumba, Babi Nias, Babi Batak, dan Babi Tana Toraja. Beberapa rumpun babi dianataranya Babi Landrace, Babi Yorkshire, Babi Tamworth, dan Babi Saddle Back.",
        life = "darat",
        isFavorite = false
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            SubmissionComposeTheme {
                DetailInformation(
                    id = fakeDataAnimal.id,
                    name = fakeDataAnimal.name,
                    type = fakeDataAnimal.type,
                    image = fakeDataAnimal.image,
                    description = fakeDataAnimal.description,
                    life = fakeDataAnimal.life,
                    isFavorite = fakeDataAnimal.isFavorite,
                    navigateBack = {},
                    onFavoriteButtonClicked = {_, _ ->},

                )
            }
        }
    }

    @Test
    fun detailInformation_isDisplayed() {
        composeTestRule.onNodeWithTag("scrollToBottom").performTouchInput {
            swipeUp()
        }
        composeTestRule.onNodeWithText(fakeDataAnimal.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeDataAnimal.type).assertIsDisplayed()
        //composeTestRule.onNodeWithText(fakeDataAnimal.positional).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeDataAnimal.description).assertIsDisplayed()
    }

    @Test
    fun addToFavoriteButton_hasClickAction() {
        composeTestRule.onNodeWithTag("favorite_detail_button").assertHasClickAction()
    }

    @Test
    fun detailInformation_isScrollable() {
        composeTestRule.onNodeWithTag("scrollToBottom").performTouchInput {
            swipeUp()
        }
    }

    @Test
    fun favoriteButton_hasCorrectStatus() {
        // Assert that the favorite button is displayed
        composeTestRule.onNodeWithTag("favorite_detail_button").assertIsDisplayed()

        // Assert that the content description of the favorite button is correct based on the isFavorite state
        val isFavorite = fakeDataAnimal.isFavorite // Set the isFavorite state here
        val expectedContentDescription = if (isFavorite) {
            "Remove from Favorite"
        } else {
            "Add to Favorite"
        }

        composeTestRule.onNodeWithTag("favorite_detail_button")
            .assertContentDescriptionEquals(expectedContentDescription)
    }
}