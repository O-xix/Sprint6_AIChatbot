const form = document.getElementById("send-form");
const input = document.getElementById("input");
const sendButton = document.getElementById("send-btn");
const messages = document.getElementById("messages");
const errorBox = document.getElementById("error");
const title = document.getElementById("chat-title");

const chatId = form.dataset.chatId;
const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

function addMessage(role, text) {
    const div = document.createElement("div");
    div.className = "message " + role;
    // textContent shows text exactly as written. Never use innerHTML for messages:
    // a message containing HTML or <script> would run on the page.
    div.textContent = text;
    messages.appendChild(div);
    messages.scrollTop = messages.scrollHeight;
    return div;
}

form.addEventListener("submit", async (event) => {
    event.preventDefault();   // stop the browser from reloading the page
    const text = input.value.trim();
    if (!text) return;

    errorBox.hidden = true;
    addMessage("user", text);
    input.value = "";
    sendButton.disabled = true;   // one message at a time (FR-3.5)
    const thinking = addMessage("assistant thinking", "Thinking...");

    try {
        const response = await fetch(`/api/chat/${chatId}/messages`, {
            method: "POST",
            headers: { "Content-Type": "application/json", [csrfHeader]: csrfToken },
            body: JSON.stringify({ message: text })
        });
        const data = await response.json().catch(() => ({}));
        if (!response.ok) {
            throw new Error(data.error || "Something went wrong. Try reloading the page.");
        }
        thinking.remove();
        addMessage("assistant", data.reply);
        title.textContent = data.title;
    } catch (error) {
        thinking.remove();
        errorBox.textContent = error.message;
        errorBox.hidden = false;
    } finally {
        sendButton.disabled = false;
        input.focus();
    }
});

// Enter sends; Shift+Enter starts a new line
input.addEventListener("keydown", (event) => {
    if (event.key === "Enter" && !event.shiftKey) {
        event.preventDefault();
        form.requestSubmit();
    }
});

// Start scrolled to the newest message
messages.scrollTop = messages.scrollHeight;