# QuizTime 🧠


https://github.com/user-attachments/assets/93a0c4f3-35d5-4dd8-baed-40a72e68adcc


A modern, interactive Java-based quiz application built with JavaFX that provides an engaging trivia experience with questions fetched from the Open Trivia Database API.

## 📋 Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Technology Stack](#technology-stack)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [API Integration](#api-integration)
- [Database Schema](#database-schema)
- [Screenshots](#screenshots)
- [Contributing](#contributing)
- [License](#license)

## 🎯 Overview

QuizTime is a comprehensive quiz application that allows users to:
- Create personalized accounts with login/registration system
- Take quizzes from various categories with customizable difficulty levels
- Choose between Multiple Choice Questions (MCQ) and True/False formats
- Track scores and progress over time
- Experience a modern, responsive UI with smooth animations

## ✨ Features

### 🔐 User Management
- **User Registration & Login**: Secure user authentication system
- **Profile Management**: Track personal quiz history and best scores
- **Local Database**: SQLite database for offline user data storage

### 🎮 Quiz Experience
- **Multiple Quiz Types**: MCQ and True/False questions
- **Category Selection**: Wide variety of topics (Science, History, Sports, etc.)
- **Difficulty Levels**: Easy, Medium, and Hard questions
- **Customizable Quiz Settings**: Choose number of questions per quiz
- **Real-time Scoring**: Instant feedback on answers
- **Timer Functionality**: Timed quiz sessions for added challenge

### 🎨 User Interface
- **Modern Design**: Clean, intuitive interface built with JavaFX
- **Splash Screen**: Loading screen with progress indication
- **Modal Dialogs**: Smooth navigation between different screens
- **AtlantaFX Theming**: Beautiful, consistent styling
- **Responsive Layout**: Adaptive UI components

### 📊 Progress Tracking
- **Score History**: Track your performance over time
- **Best Score Recording**: Remember your highest achievements
- **Quiz Management**: Create and manage custom quiz preferences

## 🛠 Technology Stack

### Core Technologies
- **Java 21**: Latest LTS version with modern language features
- **JavaFX 24**: Rich desktop application framework
- **Maven**: Dependency management and build automation
- **SQLite**: Lightweight, embedded database

### Key Dependencies
- **AtlantaFX Base (2.0.1)**: Modern JavaFX theming framework
- **Ikonli (12.3.1/12.4.0)**: Icon library for JavaFX applications
- **JSON (20250517)**: JSON parsing and manipulation
- **Apache Commons Text (1.14.0)**: Text processing utilities
- **SQLite JDBC (3.46.1.3)**: Database connectivity
- **SLF4J (2.0.17)**: Logging facade
- **JUnit Jupiter (5.12.1)**: Unit testing framework

## 📋 Prerequisites

Before running QuizTime, ensure you have:

- **Java Development Kit (JDK) 21 or higher**
  - Download from [Oracle JDK](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
- **Apache Maven 3.6+**
  - Download from [Maven Official Site](https://maven.apache.org/download.cgi)
- **Internet Connection** (required for fetching quiz questions from Open Trivia DB API)

## 🚀 Installation & Setup

### Method 1: Using Maven (Recommended)

1. **Clone the repository**:
   ```bash
   git clone https://github.com/yourusername/QuizTime.git
   cd QuizTime
   ```

2. **Install dependencies**:
   ```bash
   mvn clean install
   ```

3. **Run the application**:
   ```bash
   mvn javafx:run
   ```

### Method 2: Using IDE (IntelliJ IDEA/Eclipse)

1. **Import as Maven Project**:
   - Open your IDE
   - Import the project as an existing Maven project
   - Wait for dependencies to resolve

2. **Run the Application**:
   - Navigate to `src/main/java/org/helal_anwar/quiz_time/app/Launcher.java`
   - Right-click and select "Run"

### Method 3: Building Executable

1. **Create executable JAR**:
   ```bash
   mvn clean compile javafx:jlink
   ```

2. **Run the generated executable**:
   ```bash
   ./target/app/bin/app
   ```

## 🎮 Usage

### Getting Started

1. **Launch the Application**:
   - The application starts with a splash screen
   - Questions and categories are loaded from the Open Trivia Database API

2. **User Authentication**:
   - **New User**: Click "New User" to register with username and password
   - **Existing User**: Enter your credentials to login

3. **Dashboard**:
   - View your created quizzes
   - Add new quiz configurations
   - Access user settings and navigation

### Creating a Quiz

1. **Click "Add New Quiz"** from the dashboard
2. **Configure Quiz Settings**:
   - Select category (e.g., Science, History, Sports)
   - Choose difficulty level (Easy, Medium, Hard)
   - Pick question type (Multiple Choice or True/False)
   - Set number of questions (5, 10, 15, etc.)
3. **Save Configuration** for future use

### Taking a Quiz

1. **Select a quiz** from your dashboard
2. **Wait for questions to load** from the API
3. **Answer questions**:
   - For MCQ: Select one option and click Submit
   - For True/False: Choose True or False
4. **View results** at the end with:
   - Number of correct/incorrect answers
   - Total score (5 points per correct answer)
   - Time taken

### Features in Detail

- **Real-time Feedback**: Get immediate confirmation of correct/incorrect answers
- **Progress Tracking**: Monitor your improvement over time
- **Score Management**: Your best scores are automatically saved
- **Offline Storage**: User data and preferences stored locally

## 📁 Project Structure

```
QuizTime/
├── src/main/java/org/helal_anwar/quiz_time/app/
│   ├── question/
│   │   ├── Question.java                    # Question model class
│   │   └── QuizService.java                 # API service for fetching questions
│   ├── DatabaseLoader.java                  # SQLite database operations
│   ├── Launcher.java                        # Application entry point
│   ├── QuizTime.java                        # Main JavaFX Application class
│   ├── SplashScreen.java                    # Loading screen controller
│   ├── LoginPage.java                       # Login interface controller
│   ├── QuizDashboard.java                   # Main dashboard controller
│   ├── QuizTaker.java                       # Quiz execution controller
│   ├── McqQuizController.java               # MCQ question handler
│   ├── TrueOrFalseQuizController.java       # True/False question handler
│   ├── QuizPref.java                        # Quiz preferences controller
│   ├── Navigation.java                      # Navigation drawer controller
│   ├── Overview.java                        # Results overview controller
│   └── [Other utility classes...]
├── src/main/resources/org/helal_anwar/quiz_time/app/
│   ├── *.fxml                               # JavaFX layout files
│   ├── *.css                                # Stylesheets
│   ├── *.png, *.jpg, *.svg                  # Image resources
│   └── login_data.sql                       # Database schema
├── pom.xml                                  # Maven configuration
├── module-info.java                         # Java module definition
└── README.md                                # This file
```

### Key Classes

- **`QuizTime.java`**: Main application class, handles window setup and theming
- **`Launcher.java`**: Entry point for the application
- **`SplashScreen.java`**: Manages initial loading and API connectivity
- **`QuizService.java`**: Handles all API interactions with Open Trivia Database
- **`DatabaseLoader.java`**: Manages SQLite database operations
- **`QuizDashboard.java`**: Controls the main user interface
- **`Question.java`**: Model class representing quiz questions

## 🌐 API Integration

QuizTime integrates with the **Open Trivia Database API** (https://opentdb.com/):

### Endpoints Used:
- **Categories**: `https://opentdb.com/api_category.php`
- **Questions**: `https://opentdb.com/api.php?amount={num}&category={cat}&difficulty={level}&type={type}`

### Supported Parameters:
- **Amount**: Number of questions (1-50)
- **Category**: Various topics (Science, History, Sports, etc.)
- **Difficulty**: easy, medium, hard
- **Type**: multiple (MCQ), boolean (True/False)

### Error Handling:
- Internet connectivity checks during splash screen
- Graceful fallback to default questions if API is unavailable
- User-friendly error messages for connection issues

## 🗄 Database Schema

QuizTime uses SQLite with the following schema:

```sql
CREATE TABLE IF NOT EXISTS user (
    user_name TEXT NOT NULL,    -- Username (unique identifier)
    password  TEXT NOT NULL,    -- User password
    best_score TEXT,           -- Highest score achieved
    quizzes   TEXT             -- Serialized quiz configurations
);
```

### Database Features:
- **User Authentication**: Secure login/registration
- **Score Tracking**: Persistent best score storage
- **Quiz Configuration Storage**: Save custom quiz preferences
- **Local Storage**: Database stored in user's home directory as `quiztime.db`

## 🖼 Screenshots

*Note: Add screenshots of your application here showing:*
- Splash screen
- Login/Registration interface
- Main dashboard
- Quiz configuration screen
- Quiz taking interface
- Results overview

## 🤝 Contributing

We welcome contributions to QuizTime! Here's how you can help:

### How to Contribute:

1. **Fork the repository**
2. **Create a feature branch**: `git checkout -b feature/AmazingFeature`
3. **Commit your changes**: `git commit -m 'Add some AmazingFeature'`
4. **Push to the branch**: `git push origin feature/AmazingFeature`
5. **Open a Pull Request**

### Areas for Contribution:
- 🎨 UI/UX improvements
- 🧪 Additional question types
- 🌍 Internationalization (i18n)
- 🔧 Performance optimizations
- 📊 Advanced analytics and statistics
- 🎵 Sound effects and animations

### Development Setup:
- Follow the installation guide above
- Ensure all tests pass: `mvn test`
- Maintain code style consistency
- Add appropriate documentation

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- **Open Trivia Database**: For providing free trivia questions API
- **AtlantaFX**: For the beautiful modern JavaFX theming
- **Ikonli**: For comprehensive icon support
- **JavaFX Community**: For continued support and development

## 📞 Support & Contact

- **Issues**: Report bugs or request features via [GitHub Issues](https://github.com/yourusername/QuizTime/issues)
- **Discussions**: Join conversations in [GitHub Discussions](https://github.com/yourusername/QuizTime/discussions)

---

**Happy Quizzing! 🎉**
<img width="559" height="838" alt="Screenshot 2025-09-11 102928" src="https://github.com/user-attachments/assets/11a8c314-d979-4c17-a033-49fc0e24ca55" />
<img width="750" height="979" alt="Screenshot 2025-09-11 095259" src="https://github.com/user-attachments/assets/02a9cf4e-d3d5-42ce-bce8-8589eebd8534" />
<img width="755" height="974" alt="Screenshot 2025-09-11 095320" src="https://github.com/user-attachments/assets/e7ec9f2f-95a1-4155-b192-8bd1dc6329ca" />
<img width="750" height="975" alt="Screenshot 2025-09-11 095356" src="https://github.com/user-attachments/assets/2f91b54a-75dd-4a12-9f7f-606eb03b2f26" />
<img width="754" height="974" alt="Screenshot 2025-09-11 095422" src="https://github.com/user-attachments/assets/ae86f0dd-234a-4527-b2f6-7eea2741a93c" />
<img width="750" height="979" alt="Screenshot 2025-09-11 095442" src="https://github.com/user-attachments/assets/4e4363fe-12f9-4098-b2d9-691c46c6ec49" />
<img width="758" height="981" alt="Screenshot 2025-09-11 095500" src="https://github.com/user-attachments/assets/5948176a-8c2a-427f-9304-94a30a126d80" />
<img width="747" height="973" alt="Screenshot 2025-09-11 095238" src="https://github.com/user-attachments/assets/51e79ffe-afa4-462a-bb40-1700c6e2a616" />
<img width="557" height="861" alt="Screenshot 2025-09-11 095201" src="https://github.com/user-attachments/assets/6058fb6b-59bc-4025-935d-2c6220be5c0d" />
<img width="558" height="866" alt="Screenshot 2025-09-11 095113" src="https://github.com/user-attachments/assets/a56e733b-78a3-4a98-94a5-451bcabcff22" />
<img width="1030" height="675" alt="Screenshot 2025-09-05 095322" src="https://github.com/user-attachments/assets/c5be3df7-9860-4661-9907-c22b3483b763" />

Made with ❤️ by [Helal Anwar](https://github.com/helal_anwar)
