async function login() {

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const response = await fetch("/ToetsPortaal-1.0/api/auth", {

        method: "POST",

        headers: {
            "Content-Type": "application/json"
        },

        body: JSON.stringify({
            email: email,
            password: password
        })

    });

    if (!response.ok) {

        document.getElementById("message").innerHTML =
            "Login mislukt";

        return;
    }

    const json = await response.json();

    localStorage.setItem("jwt", json.JWT);

    getProfile();
}

async function getProfile() {

    const jwt = localStorage.getItem("jwt");

    const response = await fetch("/ToetsPortaal-1.0/api/auth/hello-profile", {

        headers: {
            "Authorization": "Bearer " + jwt
        }

    });

    const user = await response.json();

    if (user.role === "teacher") {
        window.location = "teacher.html";
    } else {
        window.location = "student.html";
    }
}