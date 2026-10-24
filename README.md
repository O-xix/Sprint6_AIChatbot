# Sprint 6: AI Chatbot

A web chatbot built with Java and Spring Boot. Users create an account, chat with an AI assistant, and come back to their saved conversations later. The AI replies come from a large language model (LLM) through an API.

> **Status: in development.** The features below are planned. The app doesn't run yet, because the database isn't connected.

## Planned features

- Register and log in with an email and password
- Chat with a general AI assistant that remembers the conversation
- Saved chat history: open, continue, rename and delete past chats
- Delete your account and all your data
- User and Admin roles
- Per-user message limits, to stay within the LLM's free tier

Later: password reset by email, word-by-word replies (streaming), custom chatbot instructions, and phone support.

The full lists are in [docs/FR.md](docs/FR.md) (what the app does) and [docs/NFR.md](docs/NFR.md) (security, testing, performance and more).

## Built with

- Java 17
- Spring Boot 4.1: Spring MVC, Spring Security, Thymeleaf, RestClient
- A shared cloud database (MySQL or Supabase/PostgreSQL, still being decided)
- An LLM API on a free tier (provider still being decided)

## Running it on your computer

### 1. Install what you need

- **JDK 17 or newer** (for example, [Eclipse Temurin](https://adoptium.net/))
- **Git**

You don't need to install Maven. The project includes the Maven wrapper (`mvnw`), which downloads it for you.

### 2. Get the code

```bash
git clone https://github.com/O-xix/Sprint6_AIChatbot.git
cd Sprint6_AIChatbot/demo
```

### 3. Set your secrets

The app needs the database login and an LLM API key. **Never put these in a committed file.** Ask a team member for the values, then set them as environment variables:

| Variable | What it is |
|----------|-----------|
| `DB_URL` | The database connection URL |
| `DB_USERNAME` | The database username |
| `DB_PASSWORD` | The database password |
| `LLM_API_KEY` | The API key for the LLM provider |

> These names are planned. They'll be connected once the database and LLM are set up.

Windows (PowerShell), for the current window only:

```powershell
$env:DB_URL = "..."
$env:DB_USERNAME = "..."
$env:DB_PASSWORD = "..."
$env:LLM_API_KEY = "..."
```

macOS or Linux:

```bash
export DB_URL="..."
export DB_USERNAME="..."
export DB_PASSWORD="..."
export LLM_API_KEY="..."
```

### 4. Start the app

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

macOS or Linux:

```bash
./mvnw spring-boot:run
```

Then open <http://localhost:8080> in your browser.

### 5. Run the tests

```bash
./mvnw test
```

(On Windows, use `.\mvnw.cmd test`.) The tests use a fake LLM, so they don't need an API key.

## Project layout

```
Sprint6_AIChatbot/
├── README.md
├── docs/
│   ├── FR.md              Functional requirements
│   └── NFR.md             Non-functional requirements
└── demo/                  The Spring Boot app
    ├── pom.xml            Dependencies and build settings
    └── src/
        ├── main/java/     Java code (controllers, services, etc.)
        ├── main/resources/
        │   ├── application.properties   App settings (no secrets)
        │   └── templates/               Thymeleaf HTML pages
        └── test/java/     Automated tests
```

## Contributing

1. Update `main` and create a branch named `your-name/what-you're-doing`:
   ```bash
   git switch main
   git pull
   git switch -c your-name/login-page
   ```
2. Make your changes and commit them.
3. Push the branch and open a pull request into `main`:
   ```bash
   git push -u origin your-name/login-page
   gh pr create
   ```
4. Mention the requirement IDs you worked on in the pull request (for example, "Implements FR-1.1 and FR-1.2").

Before committing, check `git status` to make sure no secrets or local config files are included.
