async function loadTest() {
    const jwt = localStorage.getItem("jwt");
    const params = new URLSearchParams(window.location.search);
    const assignedTestId = params.get("assignedTestId");

    const message = document.getElementById("message");
    const form = document.getElementById("test-form");

    if (!jwt) {
        window.location.href = "login.html";
        return;
    }

    if (!assignedTestId) {
        message.textContent = "Er is geen toets geselecteerd.";
        form.hidden = true;
        return;
    }

    try {
        const response = await fetch(
            `/ToetsPortaal-1.0/api/student/tests/${encodeURIComponent(assignedTestId)}`,
            {
                method: "GET",
                headers: {
                    "Authorization": `Bearer ${jwt}`
                }
            }
        );

        if (!response.ok) {
            form.hidden = true;

            if (response.status === 404) {
                message.textContent =
                    "Deze toets bestaat niet of is niet aan jou toegewezen.";
            } else {
                message.textContent =
                    "De toets kon niet worden geladen.";
            }

            console.error(
                "Toets ophalen mislukt:",
                response.status,
                await response.text()
            );
            return;
        }

        const test = await response.json();

        document.getElementById("test-title").textContent = test.title;
        document.getElementById("test-description").textContent =
            test.description || "";

        const questionsContainer = document.getElementById("questions");
        questionsContainer.replaceChildren();

        test.questions.forEach((question, index) => {
            const fieldset = document.createElement("fieldset");

            const legend = document.createElement("legend");
            legend.textContent =
                `${index + 1}. ${question.text} (${question.points} punten)`;

            fieldset.appendChild(legend);

            question.answerOptions.forEach(option => {
                const label = document.createElement("label");
                const input = document.createElement("input");

                input.type = "radio";
                input.name = `question-${question.id}`;
                input.value = option.id;
                input.required = true;

                label.appendChild(input);
                label.appendChild(
                    document.createTextNode(` ${option.text}`)
                );

                fieldset.appendChild(label);
                fieldset.appendChild(document.createElement("br"));
            });

            questionsContainer.appendChild(fieldset);
            questionsContainer.appendChild(document.createElement("br"));
        });

        // Nog geen antwoorden naar de server sturen of opslaan.
        form.addEventListener("submit", function (event) {
            event.preventDefault();

            message.textContent =
                "Je hebt de toets ingeleverd. Je antwoorden zijn nog niet opgeslagen.";

            form.querySelectorAll("input, button").forEach(element => {
                element.disabled = true;
            });
        });

    } catch (error) {
        console.error("Fout bij het laden van de toets:", error);
        message.textContent =
            "Er ging iets mis bij het laden van de toets.";
        form.hidden = true;
    }
}

loadTest();