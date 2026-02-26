package com.example.trailmate.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.trailmate.model.PackageModel
import com.example.trailmate.repository.PackageRepo
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

class PackageViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    // Test 1: Add package success
    @Test
    fun addPackage_success_test() {
        val repo = mock<PackageRepo>()
        val viewModel = PackageViewModel(repo)

        val packageModel = PackageModel(
            packageId = "",
            packageName = "Mt. Manaslu Circuit Trek",
            packageDuration = 14,
            packageCapacity = 10,
            packageDifficulty = "hard",
            packagePrice = 1500.0,
            image = ""
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Package added successfully")
            null
        }.`when`(repo).addPackage(eq(packageModel), any())

        var successResult = false
        var messageResult = ""

        viewModel.addPackage(packageModel) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Package added successfully", messageResult)
        verify(repo).addPackage(eq(packageModel), any())
    }

    // Test 2: Add package failure
    @Test
    fun addPackage_failure_test() {
        val repo = mock<PackageRepo>()
        val viewModel = PackageViewModel(repo)

        val packageModel = PackageModel(
            packageId = "",
            packageName = "Everest Base Camp",
            packageDuration = 16,
            packageCapacity = 8,
            packageDifficulty = "hard",
            packagePrice = 2000.0,
            image = ""
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(false, "Failed to add package")
            null
        }.`when`(repo).addPackage(eq(packageModel), any())

        var successResult = true
        var messageResult = ""

        viewModel.addPackage(packageModel) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertFalse(successResult)
        assertEquals("Failed to add package", messageResult)
        verify(repo).addPackage(eq(packageModel), any())
    }

    // Test 3: Delete package success
    @Test
    fun deletePackage_success_test() {
        val repo = mock<PackageRepo>()
        val viewModel = PackageViewModel(repo)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Package deleted successfully")
            null
        }.`when`(repo).deletePackage(eq("package123"), any())

        var successResult = false
        var messageResult = ""

        viewModel.deletePackage("package123") { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Package deleted successfully", messageResult)
        verify(repo).deletePackage(eq("package123"), any())
    }

    // Test 4: Delete package failure
    @Test
    fun deletePackage_failure_test() {
        val repo = mock<PackageRepo>()
        val viewModel = PackageViewModel(repo)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(false, "Package not found")
            null
        }.`when`(repo).deletePackage(eq("nonexistent"), any())

        var successResult = true
        var messageResult = ""

        viewModel.deletePackage("nonexistent") { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertFalse(successResult)
        assertEquals("Package not found", messageResult)
        verify(repo).deletePackage(eq("nonexistent"), any())
    }

    // Test 5: Edit package success
    @Test
    fun editPackage_success_test() {
        val repo = mock<PackageRepo>()
        val viewModel = PackageViewModel(repo)

        val updatedPackage = PackageModel(
            packageId = "package123",
            packageName = "Updated Trek Name",
            packageDuration = 10,
            packageCapacity = 12,
            packageDifficulty = "medium",
            packagePrice = 1200.0,
            image = ""
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback(true, "Package updated successfully")
            null
        }.`when`(repo).editPackage(eq(updatedPackage), any())

        var successResult = false
        var messageResult = ""

        viewModel.editProduct(updatedPackage) { success, msg ->
            successResult = success
            messageResult = msg
        }

        assertTrue(successResult)
        assertEquals("Package updated successfully", messageResult)
        verify(repo).editPackage(eq(updatedPackage), any())
    }

    // Test 6: Get package by id success
    @Test
    fun getPackageById_success_test() {
        val repo = mock<PackageRepo>()
        val viewModel = PackageViewModel(repo)

        val mockPackage = PackageModel(
            packageId = "package123",
            packageName = "Annapurna Circuit",
            packageDuration = 12,
            packageCapacity = 15,
            packageDifficulty = "medium",
            packagePrice = 1100.0,
            image = "https://example.com/image.jpg"
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String, PackageModel?) -> Unit>(1)
            callback(true, "Packages fetched successfully", mockPackage)
            null
        }.`when`(repo).getPackageById(eq("package123"), any())

        viewModel.getPackageById("package123")

        Thread.sleep(100)

        assertNotNull(viewModel.packages.value)
        assertEquals("Annapurna Circuit", viewModel.packages.value?.packageName)
        assertEquals(12, viewModel.packages.value?.packageDuration)
        assertEquals(1100.0, viewModel.packages.value?.packagePrice)
        verify(repo).getPackageById(eq("package123"), any())
    }

    // Test 7: Get package by id failure
    @Test
    fun getPackageById_failure_test() {
        val repo = mock<PackageRepo>()
        val viewModel = PackageViewModel(repo)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String, PackageModel?) -> Unit>(1)
            callback(false, "Package not found", null)
            null
        }.`when`(repo).getPackageById(eq("nonexistent"), any())

        viewModel.getPackageById("nonexistent")

        Thread.sleep(100)

        assertNull(viewModel.packages.value)
        verify(repo).getPackageById(eq("nonexistent"), any())
    }

    // Test 8: Get all packages success
    @Test
    fun getAllPackage_success_test() {
        val repo = mock<PackageRepo>()
        val viewModel = PackageViewModel(repo)

        val mockPackages = listOf(
            PackageModel("p1", "Manaslu Circuit", 14, 10, "hard", 1500.0, ""),
            PackageModel("p2", "Annapurna Base Camp", 10, 12, "medium", 900.0, ""),
            PackageModel("p3", "Langtang Valley", 8, 15, "easy", 700.0, "")
        )

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String, List<PackageModel>?) -> Unit>(0)
            callback(true, "Packages fetched successfully", mockPackages)
            null
        }.`when`(repo).getAllPackage(any())

        viewModel.getAllPackage()

        Thread.sleep(100)

        assertNotNull(viewModel.allPackages.value)
        assertEquals(3, viewModel.allPackages.value?.size)
        assertEquals("Manaslu Circuit", viewModel.allPackages.value?.get(0)?.packageName)
        assertEquals(10, viewModel.allPackages.value?.get(1)?.packageDuration)
        assertEquals(700.0, viewModel.allPackages.value?.get(2)?.packagePrice)
        verify(repo).getAllPackage(any())
    }

    // Test 9: Get all packages failure
    @Test
    fun getAllPackage_failure_test() {
        val repo = mock<PackageRepo>()
        val viewModel = PackageViewModel(repo)

        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String, List<PackageModel>?) -> Unit>(0)
            callback(false, "Failed to fetch packages", null)
            null
        }.`when`(repo).getAllPackage(any())

        viewModel.getAllPackage()

        Thread.sleep(100)

        assertEquals(emptyList<PackageModel>(), viewModel.allPackages.value)
        verify(repo).getAllPackage(any())
    }
}