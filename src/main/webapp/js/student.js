async function getAssignedTests() {
    const jwt = localStorage.getItem("jwt");

    // Geen token? Ga terug naar de loginpagina.
    if (!jwt) {
        window.location.href = "login.html";
        return;
    }

    const response = await fetch("/ToetsPortaal-1.0/api/student/tests", {
        headers: {
            "Authorization": "Bearer " + jwt
        }
    });

    if (response.status === 401 || response.status === 403) {
        window.location.href = "login.html";
        return;
    }

    if (!response.ok) {
        console.error("Fout bij ophalen toetsen:", response.status);
        console.error(await response.text());
        document.getElementById("tests").textContent =
            "De toetsen konden niet worden geladen.";
        return;
    }

    const tests = await response.json();
    const ul = document.getElementById("tests");

    ul.replaceChildren();

    if (tests.length === 0) {
        ul.textContent = "Je hebt momenteel geen toegewezen toetsen.";
        return;
    }

    tests.forEach((assignedTest, index) => {

        const li = document.createElement("li");

        const title = document.createElement("h3");
        title.textContent = `${index + 1}. ${assignedTest.test.title}`;

        const description = document.createElement("p");
        description.textContent = assignedTest.test.description;

        const status = document.createElement("p");
        status.textContent = "Status: " + assignedTest.status;


        const makeButton = document.createElement("button");
        makeButton.type = "button";
        makeButton.textContent = "Maak toets";

        makeButton.addEventListener("click", () => {
            window.location.href =
                `take-test.html?assignedTestId=${encodeURIComponent(assignedTest.assignedTestId)}`;
        });


        li.append(
            title,
            description,
            status,
            makeButton
        );

        ul.appendChild(li);
    });
}

getAssignedTests();