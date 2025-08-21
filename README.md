# 💰 Organizze Clone (Android)

![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)
![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84?logo=android-studio&logoColor=white)
![Java](https://img.shields.io/badge/Java-007396?logo=openjdk&logoColor=white)
![Gradle](https://img.shields.io/badge/Gradle-02303A?logo=gradle&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?logo=firebase&logoColor=black)
![Navigation](https://img.shields.io/badge/AndroidX%20Navigation-4285F4?logo=android&logoColor=white)
![View%20Binding](https://img.shields.io/badge/View%20Binding-34A853?logo=android&logoColor=white)
![clans%2Ffab](https://img.shields.io/badge/com.github.clans%3Afab-000000?logo=github&logoColor=white)

A simple personal finance manager app inspired by Organizze. It uses Firebase for authentication and data storage, AndroidX for modern UI components, and is built with Java.

---

## 🎯 Objectives

- Provide an educational Android project written in Java.
- Demonstrate user authentication (Login/Register) with Firebase Authentication.
- Implement data persistence with Firebase Realtime Database.
- Use modern Android components like Navigation, View Binding, and RecyclerView.
- Showcase a clean architecture by separating concerns into different packages (activities, adapters, config, helpers, models).

---

## 🧩 Components and Technologies

- Android (Java)
- AndroidX (Navigation, RecyclerView, AppCompat)
- View Binding
- Firebase (Authentication, Realtime Database)
- `com.github.clans:fab` for Floating Action Buttons
- Gradle build system

---

## 🗂 Project Structure

    app/
      └─ src/
         └─ main/
            ├─ java/
            │  └─ udemy/java/organizze/
            │     ├─ activities/
            │     │  ├─ ExpenseActivity.java
            │     │  ├─ LoginActivity.java
            │     │  ├─ MainActivity.java
            │     │  ├─ MenuActivity.java
            │     │  ├─ RegisterActivity.java
            │     │  └─ RevenueActivity.java
            │     ├─ adapter/
            │     │  └─ AdapterMovements.java
            │     ├─ config/
            │     │  └─ ConfigurationFirebase.java
            │     ├─ fragment/
            │     │  └─ MovementsFragment.java
            │     ├─ helper/
            │     │  ├─ Base64Custom.java
            │     │  └─ DateCustom.java
            │     └─ model/
            │        ├─ Movements.java
            │        └─ User.java
            └─ res/   (layouts, menus, drawables, navigation)

---

## 📱 Application Functionality

- **Authentication**: Users can register for a new account or log in with existing credentials.
- **Data Management**: Add new revenue and expense transactions.
- **Data Visualization**: View a list of all financial movements in the main screen.
- **User Session**: Log out to securely end the session and return to the main menu.

---

## 🧪 Code Functionality by Class

- **`activities`**
  - `MainActivity`: The main screen after login. Hosts the `MovementsFragment`, handles the logout menu option, and contains the FABs for adding transactions.
  - `LoginActivity` & `RegisterActivity`: Handle user sign-in and sign-up flows using Firebase Authentication.
  - `ExpenseActivity` & `RevenueActivity`: Provide forms to create and save new expense and revenue movements to Firebase.
  - `MenuActivity`: The app's entry point, offering options to log in or register.

- **`adapter`**
  - `AdapterMovements`: A `RecyclerView.Adapter` to bind `Movements` data to the list displayed in `MovementsFragment`.

- **`config`**
  - `ConfigurationFirebase`: A utility class to provide singleton instances of Firebase services like `FirebaseAuth` and `FirebaseDatabase`.

- **`fragment`**
  - `MovementsFragment`: Displays the list of financial movements using a `RecyclerView` and `AdapterMovements`.

- **`helper`**
  - `Base64Custom`: Provides methods to encode/decode strings (like user emails) to be used as safe Firebase keys.
  - `DateCustom`: A utility class for formatting dates.

- **`model`**
  - `Movements`: A data class representing a single financial transaction (expense or revenue).
  - `User`: A data class representing a user's profile information.

---

## 📚 Libraries and Frameworks (brief explanations)

- **AndroidX Navigation**: Manages in-app navigation, including the navigation graph and `NavController`, simplifying fragment transactions and the back stack.
- **View Binding**: Generates type-safe binding classes to access views, eliminating the need for `findViewById` and preventing null pointer exceptions.
- **Firebase Authentication**: Provides backend services for user authentication (email/password, social logins, etc.), used here for registration and login.
- **Firebase Realtime Database**: A cloud-hosted NoSQL database that lets you store and sync data between your users in real time.
- **`com.github.clans:fab`**: A third-party library that provides an enhanced Floating Action Button with a menu.
- **Gradle**: The build automation tool used for Android projects, managing dependencies and the build process.

---

## 🛠️ Setup and Execution

### Prerequisites

- Android Studio `Narwhal Feature Drop | 2025.1.2 Patch 1` or newer.
- JDK 11 (use the one bundled with Android Studio).

### 1) Get the project from GitHub

    git clone https://github.com/<your-username>/<your-repo>.git
    cd <your-repo>

### 2) Open in Android Studio

- `File` → `Open...` and select the cloned project's root directory.
- Wait for Gradle to sync all dependencies.

### 3) Firebase Configuration

- Go to the [Firebase Console](https://console.firebase.google.com/) and create a new project.
- Add an Android app to your Firebase project with the package name `udemy.java.organizze`.
- Download the generated `google-services.json` file and place it in the `app/` directory of the project.
- In the Firebase Console, enable **Authentication** (Email/Password method) and the **Realtime Database**.

### 4) Build and Run

- Select a target device (emulator or physical device).
- Click the **Run 'app'** button (▶) in Android Studio.
- Alternatively, run from the terminal (on Windows):

      .\gradlew.bat installDebug

---

## 🖼️ Screenshots and Icons

*(Replace with your actual screenshots)*

- App Icon
  ![App Icon](docs/images/app_icon.png)

- Main Screen
  ![Main Screen](docs/images/screenshot_main.png)

- Add Expense Screen
  ![Expense](docs/images/screenshot_expense.png)

- Login Screen
  ![Login](docs/images/screenshot_login.png)

---

## ✅ Notes

- Ensure your `google-services.json` file is up-to-date with your Firebase project settings.
- If you change the package name, you must update it in `build.gradle`, `AndroidManifest.xml`, and your Firebase project settings.
