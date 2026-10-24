# Non-Functional Requirements

How well the AI Chatbot must work: security, data, performance, compatibility, testing and maintenance. IDs work the same way as in [FR.md](FR.md).

**Priority**
- **Must:** required for the first working version.
- **Later:** planned, but after the first version works.

**Status:** draft. Items marked **TBD** still need a decision.

---

## NFR-1 Security

| ID | Requirement | Priority |
|----|-------------|----------|
| NFR-1.1 | Passwords are stored only as hashes (BCrypt, through Spring Security), never as plain text. | Must |
| NFR-1.2 | Secrets (the LLM API key and the database password) are never committed to Git. They're read from environment variables or a git-ignored local config file. | Must |
| NFR-1.3 | All LLM calls happen on the server. The API key is never sent to the browser. | Must |
| NFR-1.4 | Every request that reads or changes a chat checks that the chat belongs to the logged-in user (see FR-4.6). | Must |
| NFR-1.5 | Forms are protected against cross-site request forgery (CSRF), using Spring Security's built-in protection. | Must |
| NFR-1.6 | Text from users and from the model is escaped when shown on the page, so it can't run as HTML or JavaScript. | Must |
| NFR-1.7 | Repeated failed logins are limited to slow down password guessing. | Later |

## NFR-2 Data

| ID | Requirement | Priority |
|----|-------------|----------|
| NFR-2.1 | Data is stored in one shared cloud relational database. Which one (for example, MySQL, or Supabase, which uses PostgreSQL) is **TBD**. | Must |
| NFR-2.2 | The database is accessed through Spring Data JPA, so switching between MySQL and PostgreSQL only needs a driver and configuration change. | Must |
| NFR-2.3 | Deleting a chat or account removes its data from the database, not just from view. | Must |
| NFR-2.4 | Database tables are created and updated in a repeatable way, so everyone sharing the database stays in sync. | Must |

## NFR-3 Performance

| ID | Requirement | Priority |
|----|-------------|----------|
| NFR-3.1 | Pages other than chat replies load in under 2 seconds on a normal connection. | Must |
| NFR-3.2 | Reply time depends on the LLM provider. The app shows a loading indicator (FR-3.5) and gives up with an error after a timeout (length **TBD**). | Must |
| NFR-3.3 | The amount of chat history sent to the model stays within the free-tier model's limits. | Must |

## NFR-4 Compatibility

| ID | Requirement | Priority |
|----|-------------|----------|
| NFR-4.1 | The app works on desktop in current versions of Chrome, Edge and Firefox. | Must |
| NFR-4.2 | The app works well on phones. | Later |

## NFR-5 Maintainability

| ID | Requirement | Priority |
|----|-------------|----------|
| NFR-5.1 | Built with Java 17 and Spring Boot 4.1, using the Maven wrapper so no separate Maven install is needed. | Must |
| NFR-5.2 | LLM calls go through one service class, so the provider can be changed in one place (see FR-6.2). | Must |
| NFR-5.3 | Changes reach `main` through pull requests from separate branches. | Must |

## NFR-6 Testing

| ID | Requirement | Priority |
|----|-------------|----------|
| NFR-6.1 | Automated tests cover registration, login, logout and the password rules. | Must |
| NFR-6.2 | Automated tests cover creating, renaming and deleting chats, and deleting accounts. | Must |
| NFR-6.3 | Automated tests check that a user can't access another user's chats. | Must |
| NFR-6.4 | Tests replace the LLM with a fake, so they don't use the free-tier quota or need an API key. | Must |
| NFR-6.5 | Tests run automatically on every pull request (for example, with GitHub Actions). | Later |

## NFR-7 Deployment

| ID | Requirement | Priority |
|----|-------------|----------|
| NFR-7.1 | Anyone with the setup in the README can run the app on their own computer. | Must |
| NFR-7.2 | The app is hosted online. Where is **TBD**. | Later |

## NFR-8 Documentation

| ID | Requirement | Priority |
|----|-------------|----------|
| NFR-8.1 | The README explains what the project is, how to set it up and run it, and how to contribute. It's written for the team, classmates running the project, and visitors on GitHub. | Must |
| NFR-8.2 | These requirement documents are updated when a decision is made or a requirement changes. | Must |

---

## Open decisions

- Database: MySQL or Supabase/PostgreSQL (NFR-2.1)
- How database tables are created and updated, for example Flyway (NFR-2.4)
- Reply timeout (NFR-3.2)
- Hosting (NFR-7.2)
