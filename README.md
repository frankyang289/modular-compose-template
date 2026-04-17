# Modular Compose Template

A production-ready Android application template demonstrating best practices for enterprise-scale mobile development. Built with Jetpack Compose and a clean multi-module architecture, this template provides a scalable foundation for feature-rich Android apps — with a weather app as the reference implementation.

---

## Features

- **Multi-module architecture** with clear separation between `app`, `core`, and `feature` layers
- **Jetpack Compose UI** — no XML layouts, fully declarative
- **Navigation 3** with type-safe `NavKey`-based routing
- **Hilt** for dependency injection throughout
- **Material 3** design system with light/dark/dynamic theming
- **Proto DataStore** for persistent user preferences
- **Room** database infrastructure
- **Location permissions** with runtime rationale dialogs
- **Screenshot regression testing** via Roborazzi
- **StrictMode** enabled in debug builds to surface ANRs and memory leaks

---

## Architecture

The project follows **Clean Architecture** with a feature-driven modular structure. Dependencies flow in one direction:

```
App Module
    └── Feature Modules (feature:currentweather, feature:settings)
            └── Core Modules (core:data, core:domain, core:designsystem, ...)
```

Feature modules are fully decoupled from one another and communicate only through well-defined public APIs.

### Module Overview

**Core Modules** (`/core`)

| Module | Responsibility |
|---|---|
| `core:common` | Shared utilities and base Hilt setup |
| `core:data` | Repositories and data coordination |
| `core:database` | Room database infrastructure |
| `core:datastore` | Proto DataStore for user preferences |
| `core:designsystem` | Material 3 tokens, typography, icons, shared composables |
| `core:domain` | Business logic and use cases |
| `core:model` | Shared data models (`UserData`, `DarkThemeConfig`) |
| `core:navigation` | Navigation 3 runtime and routing infrastructure |
| `core:network` | Network clients and API communication |
| `core:ui` | Shared UI components (loaders, dialogs, etc.) |
| `core:screenshot-testing` | Roborazzi testing utilities |

**Feature Modules** (`/feature`)

| Module | Responsibility |
|---|---|
| `feature:currentweather:api` | Public navigation contract (`CurrentWeatherNavKey`) |
| `feature:currentweather:impl` | `CurrentWeatherScreen` and ViewModel implementation |
| `feature:settings` | Settings dialog and user preference management |

---

## Tech Stack

> All versions are managed in a single place: [`gradle/libs.versions.toml`](gradle/libs.versions.toml).

| Category | Library |
|---|---|
| Language | Kotlin |
| Build System | Gradle (Kotlin DSL) |
| UI | Jetpack Compose BOM |
| Design | Material 3 + Adaptive components |
| Navigation | AndroidX Navigation 3 |
| DI | Hilt |
| Async | Kotlin Coroutines |
| Storage | Room, Proto DataStore |
| Serialization | Kotlin Serialization, Wire |
| Image Loading | Coil |
| Location | Google Play Services Location |
| Testing | JUnit 4, Roborazzi, Robolectric, Espresso |

**SDK targets:** `minSdk 26` · `compileSdk 36` · `targetSdk 36`

---

## Getting Started

### Prerequisites

- Android Studio (latest stable recommended)
- JDK 11+
- Android SDK with API level 36

### Setup

1. Clone the repository:
   ```bash
   git clone <your-repo-url>
   cd modular-compose-template
   ```

2. Open the project in Android Studio, or build from the command line:
   ```bash
   ./gradlew build
   ```

3. Run on a connected device or emulator:
   ```bash
   ./gradlew installDebug
   ```

Always use the included Gradle wrapper (`./gradlew`) rather than a globally installed Gradle to ensure version consistency.

---

## Running Tests

```bash
# Unit tests
./gradlew test

# Instrumented tests (requires connected device or emulator)
./gradlew connectedAndroidTest

# Screenshot regression tests (Roborazzi)
./gradlew recordRoborazziDebug   # Record new baselines
./gradlew verifyRoborazziDebug   # Verify against baselines
```

---

## Project Structure

```
modular-compose-template/
├── app/                         # Application entry point
│   └── src/main/
│       ├── MainActivity.kt      # Entry activity with theme handling
│       ├── YourApplication.kt   # Hilt Application + StrictMode
│       ├── TemplateApp.kt       # Compose root with bottom navigation
│       └── MainActivityViewModel.kt
├── core/
│   ├── common/
│   ├── data/
│   ├── database/
│   ├── datastore/
│   ├── designsystem/
│   ├── domain/
│   ├── model/
│   ├── navigation/
│   ├── network/
│   ├── screenshot-testing/
│   └── ui/
├── feature/
│   ├── currentweather/
│   │   ├── api/                 # Public navigation contract
│   │   └── impl/                # Screen + ViewModel
│   └── settings/
├── gradle/
│   ├── libs.versions.toml       # Centralized dependency versions
│   └── wrapper/
├── build.gradle.kts
└── settings.gradle.kts
```

---

## Theme & Customization

The app supports three theme modes configurable via the Settings screen:

- **Light** — forced light theme
- **Dark** — forced dark theme
- **System default** — follows the device setting

Dynamic color (Material You) is supported on Android 12+ devices. Theme preferences are persisted via Proto DataStore and applied at the `MainActivity` level via a `StateFlow<UiState>`.

To customize the app for your own project, update:
- `app/src/main/res/values/strings.xml` — app name
- `app/src/main/AndroidManifest.xml` — package and permissions
- `core/designsystem` — colors, typography, and icons
- Base package from `com.heavywater.template` to your own

---

## Adding a New Feature

1. Create a new Gradle module under `/feature` (e.g., `feature:myfeature:api` and `feature:myfeature:impl`).
2. Define a `NavKey` in the `api` module for type-safe navigation.
3. Implement your `@Composable` screen and `@HiltViewModel` in the `impl` module.
4. Wire the new destination into the navigation graph in the `app` module.
5. Depend on `core:designsystem`, `core:ui`, and other core modules as needed — never on other feature modules directly.

---

## License

This project is provided as a template. Replace this section with your chosen license.
