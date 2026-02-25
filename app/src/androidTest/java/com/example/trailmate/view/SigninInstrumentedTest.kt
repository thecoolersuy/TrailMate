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
 * Instrumented test for SigninActivity using Espresso and Compose Test
 *
 * This test verifies the sign-in flow in TrailMate app
 */
@RunWith(AndroidJUnit4::class)
class SigninInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<SigninActivity>()

    @Before
    fun setup() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    /**
     * Test 1: Verify that email and password fields accept text input
     *
     * This tests that users can enter their credentials
     */
    @Test
    fun testLoginInputFields_acceptTextInput() {
        // Enter email
        composeRule.onNodeWithTag("emailInput")
            .performTextInput("test@gmail.com")

        // Enter password
        composeRule.onNodeWithTag("passwordInput")
            .performTextInput("password123")

        // If both text inputs are successful, test passes
    }

    /**
     * Test 2: Verify that Sign In button is clickable
     *
     * This tests the main login button functionality
     */
    @Test
    fun testSignInButton_isClickable() {
        // Enter email
        composeRule.onNodeWithTag("emailInput")
            .performTextInput("test@gmail.com")

        // Enter password
        composeRule.onNodeWithTag("passwordInput")
            .performTextInput("password123")

        // Click Sign In button
        composeRule.onNodeWithTag("signInButton")
            .performClick()

        // Button click successful if test doesn't crash
        // Note: Actual Firebase authentication would need mocking
    }

    /**
     * Test 3: Verify that clicking "Sign Up" link navigates to SignupActivity
     *
     * This tests navigation from sign-in to sign-up page
     */
    @Test
    fun testSignUpLink_navigatesToSignupActivity() {
        // Click on "Sign Up" link
        composeRule.onNodeWithTag("signUpLink")
            .performClick()

        // Verify that SignupActivity is launched
        Intents.intended(hasComponent(SignupActivity::class.java.name))
    }

    /**
     * Test 4: Verify that clicking "Forgot Password" navigates to ForgetPasswordActivity
     *
     * This tests the forgot password flow
     */
    @Test
    fun testForgotPasswordLink_navigatesToForgetPasswordActivity() {
        // Click on "Forgot Password" link
        composeRule.onNodeWithTag("forgotPasswordLink")
            .performClick()

        // Verify that ForgetPasswordActivity is launched
        Intents.intended(hasComponent(ForgetPasswordActivity::class.java.name))
    }

    /**
     * Test 5: Verify that Google sign-in button is clickable
     *
     * This tests the Google OAuth button
     */
    @Test
    fun testGoogleButton_isClickable() {
        // Click Google button
        composeRule.onNodeWithTag("googleButton")
            .performClick()

        // Test passes if button is clickable
    }

    /**
     * Test 6: Verify that Facebook sign-in button is clickable
     *
     * This tests the Facebook OAuth button
     */
    @Test
    fun testFacebookButton_isClickable() {
        // Click Facebook button
        composeRule.onNodeWithTag("facebookButton")
            .performClick()

        // Test passes if button is clickable
    }

    /**
     * Test 7: Verify that back button navigates to SignupActivity
     *
     * This tests the back navigation
     */
    @Test
    fun testBackButton_navigatesToSignupActivity() {
        // Click back button
        composeRule.onNodeWithTag("backButton")
            .performClick()

        // Verify navigation to SignupActivity
        Intents.intended(hasComponent(SignupActivity::class.java.name))
    }

    /**
     * Test 8: Complete sign-in flow test
     *
     * This tests the entire login process from start to finish
     */
    @Test
    fun testCompleteSignInFlow() {
        // Step 1: Enter email
        composeRule.onNodeWithTag("emailInput")
            .performTextInput("user@trailmate.com")

        // Step 2: Enter password
        composeRule.onNodeWithTag("passwordInput")
            .performTextInput("mySecurePassword")

        // Step 3: Click Sign In button
        composeRule.onNodeWithTag("signInButton")
            .performClick()

        // Test verifies entire flow completes without crashes
        // Actual authentication would require Firebase mocking
    }

    /**
     * Test 9: Test empty credentials (negative test)
     *
     * This verifies that clicking sign-in with empty fields doesn't crash
     */
    @Test
    fun testSignInButton_withEmptyCredentials() {
        // Click Sign In without entering any credentials
        composeRule.onNodeWithTag("signInButton")
            .performClick()

        // Test passes if app doesn't crash
        // In production, this should show an error message
    }
}