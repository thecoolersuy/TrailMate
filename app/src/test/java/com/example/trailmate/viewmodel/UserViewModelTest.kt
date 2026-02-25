package com.example.trailmate.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.trailmate.model.UserModel
import com.example.trailmate.repository.UserRepo
import com.google.firebase.auth.FirebaseUser
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class UserViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    /**
     * Test 1: Login Success
     * Tests that when login is successful, the callback returns true with success message
     */
    @Test
    fun login_success_test() {
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)
        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(2)
            callback(true, "Login Success")
            null
        }.`when`(repo).login(eq("test@gmail.com"), eq("123456"), any())
        var successResult = false
        var messageResult = ""

        viewModel.login("test@gmail.com", "123456") { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Login Success", messageResult)

        verify(repo).login(eq("test@gmail.com"), eq("123456"), any())
    }

    /**
     * Test 2: Login Failure
     * Tests that when login fails, the callback returns false with error message
     */
    @Test
    fun login_failure_test() {
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(2)
            callback(false, "Invalid credentials")
            null
        }.`when`(repo).login(eq("wrong@gmail.com"), eq("wrongpass"), any())

        var successResult = true
        var messageResult = ""


        viewModel.login("wrong@gmail.com", "wrongpass") { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertFalse(successResult)
        assertEquals("Invalid credentials", messageResult)
        verify(repo).login(eq("wrong@gmail.com"), eq("wrongpass"), any())
    }

    /**
     * Test 3: Register Success
     * Tests that registration works correctly and returns userId
     */
    @Test
    fun register_success_test() {

        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)


        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String, String) -> Unit>(2)
            callback(true, "Registration success", "user123")
            null
        }.`when`(repo).register(eq("newuser@gmail.com"), eq("password123"), any())

        var successResult = false
        var messageResult = ""
        var userIdResult = ""


        viewModel.register("newuser@gmail.com", "password123") { success, msg, userId ->
            successResult = success
            messageResult = msg
            userIdResult = userId
        }


        assertTrue(successResult)
        assertEquals("Registration success", messageResult)
        assertEquals("user123", userIdResult)
        verify(repo).register(eq("newuser@gmail.com"), eq("password123"), any())
    }

    /**
     * Test 4: Register Failure
     * Tests that registration fails when email already exists
     */
    @Test
    fun register_failure_test() {

        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String, String) -> Unit>(2)
            callback(false, "Email already exists", "")
            null
        }.`when`(repo).register(eq("existing@gmail.com"), eq("password123"), any())

        var successResult = true
        var messageResult = ""
        var userIdResult = "should_be_empty"

        viewModel.register("existing@gmail.com", "password123") { success, msg, userId ->
            successResult = success
            messageResult = msg
            userIdResult = userId
        }

        assertFalse(successResult)
        assertEquals("Email already exists", messageResult)
        assertEquals("", userIdResult)
        verify(repo).register(eq("existing@gmail.com"), eq("password123"), any())
    }

    /**
     * Test 5: Add User to Database Success
     * Tests that user data is successfully added to database
     */
    @Test
    fun addUserToDatabase_success_test() {

        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        val userModel = UserModel(
            userId = "user123",
            fullName = "John Doe",
            location = "Kathmandu",
            email = "john@gmail.com",
            password = "password123"
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(2)
            callback(true, "Registration success")
            null
        }.`when`(repo).addUserToDatabase(eq("user123"), eq(userModel), any())

        var successResult = false
        var messageResult = ""


        viewModel.addUserToDatabase("user123", userModel) { success, msg ->
            successResult = success
            messageResult = msg
        }


        assertTrue(successResult)
        assertEquals("Registration success", messageResult)
        verify(repo).addUserToDatabase(eq("user123"), eq(userModel), any())
    }

    /**
     * Test 6: Get User By Id Success
     * Tests that user data is successfully fetched by userId
     */
    @Test
    fun getUserById_success_test() {

        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        val mockUser = UserModel(
            userId = "user123",
            fullName = "Jane Smith",
            location = "Pokhara",
            email = "jane@gmail.com",
            password = "password123"
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String, UserModel?) -> Unit>(1)
            callback(true, "profile fetched", mockUser)
            null
        }.`when`(repo).getUserById(eq("user123"), any())


        viewModel.getUserById("user123")


        Thread.sleep(100)


        assertNotNull(viewModel.users.value)
        assertEquals("Jane Smith", viewModel.users.value?.fullName)
        assertEquals("Pokhara", viewModel.users.value?.location)
        assertEquals("jane@gmail.com", viewModel.users.value?.email)
        verify(repo).getUserById(eq("user123"), any())
    }

    /**
     * Test 7: Get User By Id Failure
     * Tests that null is returned when user is not found
     */
    @Test
    fun getUserById_failure_test() {
        // Arrange
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String, UserModel?) -> Unit>(1)
            callback(false, "User not found", null)
            null
        }.`when`(repo).getUserById(eq("nonexistent"), any())


        viewModel.getUserById("nonexistent")


        Thread.sleep(100)


        assertNull(viewModel.users.value)
        verify(repo).getUserById(eq("nonexistent"), any())
    }

    /**
     * Test 8: Get All Users Success
     * Tests that all users are successfully fetched
     */
    @Test
    fun getAllUser_success_test() {
        // Arrange
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        val mockUsers = listOf(
            UserModel("user1", "User One", "Kathmandu", "user1@gmail.com", "pass1"),
            UserModel("user2", "User Two", "Pokhara", "user2@gmail.com", "pass2"),
            UserModel("user3", "User Three", "Lalitpur", "user3@gmail.com", "pass3")
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String, List<UserModel>?) -> Unit>(0)
            callback(true, "profile fetched", mockUsers)
            null
        }.`when`(repo).getAllUser(any())

        // Act
        viewModel.getAllUser()

        // Wait a bit for LiveData to update
        Thread.sleep(100)

        // Assert
        assertNotNull(viewModel.allUsers.value)
        assertEquals(3, viewModel.allUsers.value?.size)
        assertEquals("User One", viewModel.allUsers.value?.get(0)?.fullName)
        assertEquals("User Two", viewModel.allUsers.value?.get(1)?.fullName)
        verify(repo).getAllUser(any())
    }

    /**
     * Test 9: Update Profile Success
     * Tests that user profile is successfully updated
     */
    @Test
    fun updateProfile_success_test() {
        // Arrange
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        val updatedUser = UserModel(
            userId = "user123",
            fullName = "Updated Name",
            location = "Updated Location",
            email = "updated@gmail.com",
            password = "newpass123"
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(2)
            callback(true, "Profile updated")
            null
        }.`when`(repo).updateProfile(eq("user123"), eq(updatedUser), any())

        var successResult = false
        var messageResult = ""

        // Act
        viewModel.updateProfile("user123", updatedUser) { success, msg ->
            successResult = success
            messageResult = msg
        }

        // Assert
        assertTrue(successResult)
        assertEquals("Profile updated", messageResult)
        verify(repo).updateProfile(eq("user123"), eq(updatedUser), any())
    }

    /**
     * Test 10: Delete Account Success
     * Tests that user account is successfully deleted
     */
    @Test
    fun deleteAccount_success_test() {
        // Arrange
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Account deleted")
            null
        }.`when`(repo).deleteAccount(eq("user123"), any())

        var successResult = false
        var messageResult = ""

        // Act
        viewModel.deleteAccount("user123") { success, msg ->
            successResult = success
            messageResult = msg
        }

        // Assert
        assertTrue(successResult)
        assertEquals("Account deleted", messageResult)
        verify(repo).deleteAccount(eq("user123"), any())
    }

    /**
     * Test 11: Logout Success
     * Tests that user is successfully logged out
     */
    @Test
    fun logout_success_test() {
        // Arrange
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(0)
            callback(true, "logout successfully")
            null
        }.`when`(repo).logOut(any())

        var successResult = false
        var messageResult = ""

        // Act
        viewModel.logOut { success, msg ->
            successResult = success
            messageResult = msg
        }

        // Assert
        assertTrue(successResult)
        assertEquals("logout successfully", messageResult)
        verify(repo).logOut(any())
    }

    /**
     * Test 12: Forget Password Success
     * Tests that password reset email is successfully sent
     */
    @Test
    fun forgetPassword_success_test() {
        // Arrange
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)

        val email = "user@gmail.com"

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Email send to $email")
            null
        }.`when`(repo).forgetPassword(eq(email), any())

        var successResult = false
        var messageResult = ""

        // Act
        viewModel.forgetPassword(email) { success, msg ->
            successResult = success
            messageResult = msg
        }

        // Assert
        assertTrue(successResult)
        assertEquals("Email send to $email", messageResult)
        verify(repo).forgetPassword(eq(email), any())
    }

    /**
     * Test 13: Get Current User
     * Tests that current user is retrieved from repository
     */
    @Test
    fun getCurrentUser_test() {
        // Arrange
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)
        val mockFirebaseUser = mock<FirebaseUser>()

        doAnswer { mockFirebaseUser }.`when`(repo).getCurrentUser()

        // Act
        val currentUser = viewModel.getCurrentUser()

        // Assert
        assertEquals(mockFirebaseUser, currentUser)
        verify(repo).getCurrentUser()
    }
}