async function getTests() {

    const jwt = localStorage.getItem("jwt");

    const response = await fetch("/ToetsPortaal-1.0/api/tests/my", {

        headers: {
            "Authorization": "Bearer " + jwt
        }

    });

    if (!response.ok) {
        console.log("Status:", response.status);
        console.log(await response.text());
        return;
    }

    const tests = await response.json();

    const ul = document.getElementById("tests");

    tests.forEach((test, index) => {

        ul.innerHTML += `
        <li class="test-item">
            <span class="test-number">${index + 1}</span>

            <div class="test-info">
                <h3>${test.title}</h3>
                <p>${test.description}</p>
            </div>
        </li>
    `;

    });

}

getTests();