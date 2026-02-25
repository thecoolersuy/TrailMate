package com.example.trailmate.view

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test for SignupActivity using Espresso and Compose Test
 *
 * This test verifies the signup flow in TrailMate app
 */
@RunWith(AndroidJUnit4::class)
class SignupInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<SignupActivity>()

    @Before
    fun setup() {

        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }
    /**
     * Test 1: Verify that clicking "Sign In" link navigates to SigninActivity
     * This tests the navigation from SignupActivity to SigninActivity
     */
    @Test
    fun testSignInLink_navigatesToSigninActivity() {
        composeRule.onNodeWithTag("signInLink")
            .performClick()

        Intents.intended(hasComponent(SigninActivity::class.java.name))
    }

    /**
     * Test 2: Verify that clicking back button navigates to MainActivity
     *
     * This tests the back navigation functionality
     */
    @Test
    fun testBackButton_navigatesToMainActivity() {

        composeRule.onNodeWithTag("backButton")
            .performClick()


        Intents.intended(hasComponent(MainActivity::class.java.name))
    }

    /**
     * Test 3: Verify that all input fields accept text input
     *
     * This tests that users can enter data in all form fields
     */
    @Test
    fun testInputFields_acceptTextInput() {

        composeRule.onNodeWithTag("fullnameInput")
            .performTextInput("John Doe")


        composeRule.onNodeWithTag("locationInput")
            .performTextInput("Kathmandu")


        composeRule.onNodeWithTag("emailInput")
            .performTextInput("john.doe@gmail.com")


        composeRule.onNodeWithTag("passwordInput")
            .performTextInput("password123")


        composeRule.onNodeWithTag("confirmPasswordInput")
            .performTextInput("password123")

    }

    /**
     * Test 4: Verify that Create Account button is clickable
     *
     * This tests that the signup button can be interacted with
     */
    @Test
    fun testCreateAccountButton_isClickable() {
        // Find and click the Create Account button
        composeRule.onNodeWithTag("createAccountButton")
            .performClick()

        // If button is clickable, test passes
        // The actual signup logic would need Firebase which is difficult to test
        // in instrumented tests, so we just verify the UI interaction works
    }

    /**
     * Test 5: Verify that Google sign-up button is present and clickable
     *
     * This tests that the Google OAuth button works
     */
    @Test
    fun testGoogleButton_isClickable() {
        // Find and click Google button
        composeRule.onNodeWithTag("googleButton")
            .performClick()

        // Test passes if button is clickable
    }

    /**
     * Test 6: Verify that Facebook sign-up button is present and clickable
     *
     * This tests that the Facebook OAuth button works
     */
    @Test
    fun testFacebookButton_isClickable() {
        // Find and click Facebook button
        composeRule.onNodeWithTag("facebookButton")
            .performClick()

        // Test passes if button is clickable
    }

    /**
     * Test 7: Complete signup flow test
     *
     * This tests the entire signup process from start to clicking submit
     */
    @Test
    fun testCompleteSignupFlow() {
        // Step 1: Fill in full name
        composeRule.onNodeWithTag("fullnameInput")
            .performTextInput("Jane Smith")

        // Step 2: Fill in location
        composeRule.onNodeWithTag("locationInput")
            .performTextInput("Pokhara")

        // Step 3: Fill in email
        composeRule.onNodeWithTag("emailInput")
            .performTextInput("jane.smith@gmail.com")

        // Step 4: Fill in password
        composeRule.onNodeWithTag("passwordInput")
            .performTextInput("securePass123")

        // Step 5: Fill in confirm password
        composeRule.onNodeWithTag("confirmPasswordInput")
            .performTextInput("securePass123")

        // Step 6: Click Create Account button
        composeRule.onNodeWithTag("createAccountButton")
            .performClick()

        // Test verifies that entire flow can be completed without crashes
        // Actual Firebase signup would need mocking for proper testing
    }
}