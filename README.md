# Photo Search

Android photo search app built for a Haystack News Android Developer home challenge. The app loads Flickr photos, supports text search, displays results in a 3-column grid, and opens a detail screen for a selected image.

This implementation lives on the `HYSTCK-2` branch. The original `main` branch is only the initial Android project scaffold.

## Screenshots

Screenshots are included in `screenshots/`:

- `home-screen.png`
- `search-example.png`
- `text-search .png`
- `voice-search.png`
- `bug.png`

## Main Features

- Loads a default Flickr photo feed using `flickr.interestingness.getList`.
- Searches Flickr photos by text query using `flickr.photos.search`.
- Displays photos in a `RecyclerView` grid.
- Opens a detail screen with the selected photo and title.
- Loads remote images with Glide.
- Uses Android TV launcher configuration through `LEANBACK_LAUNCHER`.
- Shows loading, empty, and error status text for photo loading/search.

## Architecture

The app uses a lightweight MVVM-style structure:

- UI layer: `MainActivity`, `PhotoGalleryFragment`, `PhotoListAdapter`, and `PhotoDetailActivity`.
- State holder: `PhotoGalleryViewModel`, exposing `StateFlow<PhotoGalleryUiState>`.
- Data layer: `PhotosRepository` and `FlickrRepository`.
- Network layer: Retrofit `FlickrService` and OkHttp-backed `WebClient`.
- Domain model: `Photo`.

The project has package separation for UI, data, network models, and domain models, but it does not include use cases, dependency injection, or local persistence.

## Tech Stack

- Kotlin
- Android Views and XML layouts
- ViewBinding
- AndroidX Fragment, Lifecycle, RecyclerView, AppCompat, ConstraintLayout, and Material
- Kotlin coroutines, `viewModelScope`, `lifecycleScope`, and `StateFlow`
- Retrofit, OkHttp, and Gson
- Glide
- Flickr REST API
- Gradle Android plugin `8.5.2`
- Kotlin plugin `1.9.24`
- Gradle wrapper `8.7`

No Jetpack Compose, Room, Hilt/Dagger, Paging, or Navigation Component is used in this repository.

## Key Patterns

- Repository abstraction for Flickr photo loading.
- DTO-to-domain mapping with `PhotoResponse.toDomain()`.
- Lifecycle-aware `StateFlow` collection using `repeatOnLifecycle`.
- Shared Flickr query parameters added through an OkHttp interceptor.
- Flickr API key loaded from `local.properties` or the `FLICKR_API_KEY` environment variable.
- Basic UI state model for loading, content, empty, and error states.
- Small `ImageView.loadUrl()` extension around Glide.
- Intent extras for detail-screen navigation.

## Folder Structure

```text
app/src/main/java/com/haystack/photosearch/
  data/
    repository/      Repository contract and Flickr implementation
    server/          Retrofit service and WebClient
    server/model/    Flickr response DTOs and mapper
  domain/            Photo domain model
  ui/
    common/          UI extensions
    detail/          Photo detail screen
    gallery/         Search/grid screen, ViewModel, and adapter
```

## Data Flow

1. `MainActivity` hosts `PhotoGalleryFragment`.
2. `PhotoGalleryFragment` observes `PhotoGalleryViewModel.galleryItems`.
3. On startup, the ViewModel requests Flickr interesting photos.
4. On search submit, the ViewModel requests Flickr search results.
5. `FlickrRepository` calls `FlickrService` through Retrofit.
6. `WebClient` appends Flickr API parameters through an OkHttp interceptor.
7. Network DTOs are mapped into `Photo` domain objects.
8. `PhotoListAdapter` binds photos with Glide and opens `PhotoDetailActivity` on click.

## Setup Guide

Clone the repository and use the implementation branch:

```bash
git clone git@github.com:alfredoporto/photo-search.git
cd photo-search
git checkout HYSTCK-2
```

Recommended environment:

- Android Studio
- JDK 11
- Android SDK with API 32 available
- Android TV emulator or device recommended because the app declares a Leanback launcher entry

Create a local, untracked `local.properties` file with your Flickr API key:

```properties
flickrApiKey=YOUR_FLICKR_API_KEY
```

Alternatively, expose the key through the `FLICKR_API_KEY` environment variable before running Gradle.

## How To Run The App

Open the project in Android Studio, let Gradle sync, select the `app` configuration, and run it on an emulator or device.

Expected CLI command:

```bash
./gradlew installDebug
```

## How To Run Tests

Unit tests are included for `PhotoGalleryViewModel`.

Run local unit tests:

```bash
./gradlew test
```

Instrumentation tests are not currently included. Standard command to use once instrumentation tests are added:

```bash
./gradlew connectedAndroidTest
```

## Tradeoffs And Assumptions

- The confirmed stale-image search bug from `HYSTCK-1` is addressed in `HYSTCK-2` by replacing the older Leanback adapter flow with repository-backed suspend calls, `StateFlow`, and RecyclerView adapter replacement.
- Error handling is intentionally small: load/search failures surface as status text, but there is no retry action or rich error UI.
- The ViewModel accepts a repository constructor parameter for tests, but the production default still creates `FlickrRepository` directly.
- The app declares Android TV launcher support, but the current UI is a custom RecyclerView/AppCompat implementation rather than a full Leanback browsing experience.
- Pagination is not implemented.
- Search cancellation and debouncing are not implemented.

## Future Improvements

- Add a retry action and richer error/empty UI.
- Add repository/API tests, ideally with MockWebServer.
- Add UI tests for search, grid refresh, and detail navigation.
- Replace adapter recreation with a retained adapter and DiffUtil/ListAdapter.
- Add pagination for Flickr result sets.
- Add search cancellation or debouncing for repeated queries.
- Improve TV focus behavior and verify voice-search behavior.
