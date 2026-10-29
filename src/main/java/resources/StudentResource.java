package resources;

import domain.AnswerOption;
import domain.Question;
import domain.SchoolTest;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import services.TestService;
import domain.AssignedTest;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Path("student")
public class StudentResource {

    @GET
    @Path("tests")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("student")
    public List<AssignedTest> getAssignedTests(
            @Context SecurityContext securityContext
    ) {

        String email =
                securityContext.getUserPrincipal().getName();

        TestService service =
                new TestService();

        return service.getAssignedTests(email);
    }
    @GET
    @Path("tests/{assignedTestId}")
    @RolesAllowed("student")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAssignedTest(
            @PathParam("assignedTestId") int assignedTestId,
            @Context SecurityContext securityContext) {

        if (securityContext.getUserPrincipal() == null) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }

        String email = securityContext.getUserPrincipal().getName();

        SchoolTest test = new TestService()
                .getAssignedTestForStudent(assignedTestId, email);

        if (test == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Maak een antwoord voor de student zonder het correcte antwoord te tonen.
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("id", test.getId());
        response.put("title", test.getTitle());
        response.put("description", test.getDescription());

        List<Map<String, Object>> questionList = new ArrayList<>();

        for (Question question : test.getQuestions()) {
            Map<String, Object> questionData = new LinkedHashMap<>();
            questionData.put("id", question.getId());
            questionData.put("text", question.getText());
            questionData.put("points", question.getPoints());

            List<Map<String, Object>> optionList = new ArrayList<>();

            for (AnswerOption option : question.getAnswerOptions()) {
                Map<String, Object> optionData = new LinkedHashMap<>();
                optionData.put("id", option.getId());
                optionData.put("text", option.getText());

                optionList.add(optionData);
            }

            questionData.put("answerOptions", optionList);
            questionList.add(questionData);
        }

        response.put("questions", questionList);

        return Response.ok(response).build();
    }
}

