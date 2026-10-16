# Functional Requirements

What the AI Chatbot must do. Each requirement has an ID so issues, pull requests and tests can refer to it (for example, "implements FR-2.1").

**Priority**
- **Must:** required for the first working version.
- **Later:** planned, but after the first version works.

**Status:** draft. Items marked **TBD** still need a decision.

---

## FR-1 Accounts

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-1.1 | A visitor can register with an email address and a password. | Must |
| FR-1.2 | Each email address can belong to only one account. Registering with an email that's already used shows an error. | Must |
| FR-1.3 | Passwords must meet the password rules (minimum length and other rules TBD). Registration explains which rule failed. | Must |
| FR-1.4 | A registered user can log in with their email and password. A wrong email or password shows a general error that doesn't say which one was wrong. | Must |
| FR-1.5 | A logged-in user can log out. | Must |
| FR-1.6 | Visitors who aren't logged in can only see the login and registration pages. | Must |
| FR-1.7 | A user can delete their own account. This also deletes all of their chats, and asks for confirmation first. | Must |
| FR-1.8 | A user can reset a forgotten password by email. | Later |
| FR-1.9 | A new account must verify its email address. | Later |
| FR-1.10 | Login does not use OAuth or third-party sign-in (Google, GitHub, etc.). | Must |

## FR-2 Roles

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-2.1 | Every account has a role: **User** or **Admin**. New accounts are Users. | Must |
| FR-2.2 | Admins can manage user accounts. Exactly what they can do (view the user list, disable or delete accounts, view usage) is **TBD**. | Must |
| FR-2.3 | Only Admins can open admin pages. | Must |

## FR-3 Chatting

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-3.1 | A user can start a new chat. | Must |
| FR-3.2 | A user can type a message and receive the chatbot's reply. The full reply appears at once. | Must |
| FR-3.3 | The chatbot remembers the earlier messages in the same chat. The earlier messages are sent to the model with each new message, up to the model's limit. | Must |
| FR-3.4 | The chatbot is a general assistant: it uses the model's default behavior with no special instructions. | Must |
| FR-3.5 | While waiting for a reply, the page shows that the chatbot is working, and the user can't send another message in that chat. | Must |
| FR-3.6 | If the model fails or a limit is reached, the page shows an error, and the user's message isn't lost. | Must |
| FR-3.7 | Replies appear word by word as the model writes them (streaming). | Later |
| FR-3.8 | The chatbot's instructions (system prompt) can be customized. | Later |

## FR-4 Chat history

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-4.1 | Every message and reply is saved to the user's account. | Must |
| FR-4.2 | A user can see a list of their past chats, newest first. | Must |
| FR-4.3 | A user can open a past chat and continue it. | Must |
| FR-4.4 | New chats get a default title (for example, from the first message). A user can rename a chat. | Must |
| FR-4.5 | A user can delete a single chat, after confirming. | Must |
| FR-4.6 | A user can only see, change or delete their own chats. | Must |

## FR-5 Usage limits

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-5.1 | Each user is limited in how many messages they can send in a period of time, so one user can't use up the shared free-tier quota. The exact limits are **TBD**. | Must |
| FR-5.2 | A user who hits the limit sees a message saying so and when they can send again. | Must |
| FR-5.3 | A single message can't be longer than the model allows. Longer messages show an error before anything is sent. | Must |

## FR-6 LLM provider

| ID | Requirement | Priority |
|----|-------------|----------|
| FR-6.1 | The chatbot uses an LLM provider through an API key, starting with a free tier. Which provider is **TBD**. | Must |
| FR-6.2 | The provider and model can be switched by changing configuration, without rewriting the chat features. | Later |

---

## Open decisions

- Password rules (FR-1.3)
- What Admins can do (FR-2.2)
- Message limits per user (FR-5.1)
- LLM provider and model (FR-6.1)

See [NFR.md](NFR.md) for the non-functional requirements.
