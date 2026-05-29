package com.haystack.photosearch.ui.gallery

import com.haystack.photosearch.data.repository.PhotosRepository
import com.haystack.photosearch.domain.Photo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PhotoGalleryViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun loadsDefaultPhotosOnInit() = runTest {
        val photo = photo(id = "1")
        val viewModel = PhotoGalleryViewModel(
            FakePhotosRepository(defaultPhotos = listOf(photo))
        )

        advanceUntilIdle()

        assertEquals(
            PhotoGalleryUiState.Content(listOf(photo), query = null),
            viewModel.uiState.value
        )
    }

    @Test
    fun searchingWithQueryLoadsSearchResults() = runTest {
        val photo = photo(id = "2", title = "Dog")
        val repository = FakePhotosRepository(searchPhotos = listOf(photo))
        val viewModel = PhotoGalleryViewModel(repository)

        viewModel.setQuery("dogs")
        advanceUntilIdle()

        assertEquals(
            PhotoGalleryUiState.Content(listOf(photo), query = "dogs"),
            viewModel.uiState.value
        )
    }

    @Test
    fun blankSearchReloadsDefaultPhotos() = runTest {
        val photo = photo(id = "3")
        val repository = FakePhotosRepository(defaultPhotos = listOf(photo))
        val viewModel = PhotoGalleryViewModel(repository)

        viewModel.setQuery("   ")
        advanceUntilIdle()

        assertEquals(
            PhotoGalleryUiState.Content(listOf(photo), query = null),
            viewModel.uiState.value
        )
    }

    @Test
    fun searchFailureExposesErrorState() = runTest {
        val repository = FakePhotosRepository(
            defaultPhotos = listOf(photo(id = "4")),
            searchError = IllegalStateException("Network failure")
        )
        val viewModel = PhotoGalleryViewModel(repository)

        viewModel.setQuery("cats")
        advanceUntilIdle()

        assertEquals(
            PhotoGalleryUiState.Error(
                "Unable to load photos. Check the Flickr API key and network connection.",
                query = "cats"
            ),
            viewModel.uiState.value
        )
    }

    private fun photo(id: String, title: String = "Photo") = Photo(
        id = id,
        title = title,
        url = "https://example.com/$id.jpg"
    )
}

private class FakePhotosRepository(
    private val defaultPhotos: List<Photo> = emptyList(),
    private val searchPhotos: List<Photo> = emptyList(),
    private val searchError: Throwable? = null
) : PhotosRepository {
    override suspend fun searchPhotos(query: String): List<Photo> {
        searchError?.let { throw it }
        return searchPhotos
    }

    override suspend fun fetchPhotos(): List<Photo> = defaultPhotos
}
