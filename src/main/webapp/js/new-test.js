let questionCount = 0;

function addQuestion(){

    questionCount++;
    const div = document.createElement("div");

    div.className="question";
    div.innerHTML = `
        <h3>Vraag ${questionCount}</h3>

        <label>Vraagtekst</label><br>
        <input class="question-text"><br>
        
        <label>Punten</label><br>
        <input class="points" type="number"><br>
        
        <h4>Antwoorden</h4>
        <div class="answers">
            <input class="answer-text">
            <label>
                Goed?
                <input type="checkbox" class="correct">
            </label>

        </div>
        <button onclick="addAnswer(this)">
            Antwoord toevoegen
        </button>
        <hr>
    `;
    document
        .getElementById("questions")
        .appendChild(div);
}

function addAnswer(button){

    const answersDiv =
        button.previousElementSibling;
    const answer =
        document.createElement("div");
    answer.innerHTML=`

        <input class="answer-text">
        <label>
            Goed?
            <input type="checkbox" class="correct">
        </label>
    `;
    answersDiv.appendChild(answer);
}

async function saveTest(){
    const data={
        title:
        document.getElementById("title").value,
        description:
        document.getElementById("description").value,
        questions:[]
    };
    document
        .querySelectorAll(".question")
        .forEach(question=>{
            let q={

                text:
                question.querySelector(".question-text").value,

                points:
                    Number(
                        question.querySelector(".points").value
                    ),
                answerOptions:[]
            };

            question
                .querySelectorAll(".answers div, .answers")
                .forEach(answer=>{

                    const text =
                        answer.querySelector(".answer-text");

                    const correct =
                        answer.querySelector(".correct");

                    if(text){
                        q.answerOptions.push({
                            text:text.value,
                            correct:correct.checked
                        });
                    }
                });
            data.questions.push(q);
        });

    const jwt =
        localStorage.getItem("jwt");

    const response =
        await fetch(
            "/ToetsPortaal-1.0/api/tests",
            {
                method:"POST",
                headers:{
                    "Content-Type":"application/json",
                    "Authorization":
                        "Bearer "+jwt
                },
                body:
                    JSON.stringify(data)
            });



    if(response.ok){
        window.location="teacher.html";
    }

    else{
        console.log("Status:", response.status);
        console.log(await response.text());

        alert("Opslaan mislukt");
    }


}