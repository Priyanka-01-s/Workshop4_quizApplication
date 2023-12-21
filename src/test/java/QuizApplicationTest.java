import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

import com.example.Main;
import com.example.Question;
import com.example.Quiz;

public class QuizApplicationTest {

    private Quiz quiz;

    @Before
    public void setUp() {
        quiz = new Quiz(1);
    }

    @Test
    public void testQuizCreation() {
        assertNotNull(quiz);
        assertEquals(1, quiz.getQuizID());
        assertEquals(0, quiz.getQuestions().size());
        assertFalse(quiz.randomized);
    }

    @Test
    public void testAddNewQuestion() {
        assertNotNull(quiz);
        Scanner sc = new Scanner("Test question\nno\n");
        quiz.addNewQuestion(sc);
        assertEquals(1, quiz.getQuestions().size());
    }

    @Test
    public void testMCQQuestionCreation() {
        String question = "What is the capital of India?";
        String[] options = {"Mumbai", "Delhi", "Kolkata", "Chennai"};
        List<String> categories = Collections.singletonList("easy");
        Question mcqQuestion = new Question(question, options, 2, categories, 1);

        assertNotNull(mcqQuestion);
        assertEquals(question, mcqQuestion.getQuestion());
        assertArrayEquals(options, mcqQuestion.getOptions());
        assertEquals(categories, mcqQuestion.getCategories());
        assertEquals(1, mcqQuestion.getQuizID());
    }

    @Test
    public void testOpenEndedQuestionCreation() {
        String question = "12*2";
        String correctAnswer = "24";
        String explanation = "Multiplication";
        Question openEndedQuestion = new Question(question, correctAnswer, explanation, 1);

        assertNotNull(openEndedQuestion);
        assertEquals(question, openEndedQuestion.getQuestion());
        assertEquals(correctAnswer, openEndedQuestion.getCorrectAnswer());
        assertEquals(explanation, openEndedQuestion.getExplanation());
    }
     @Test
    public void testCreateparticipant() {
        Scanner sc = new Scanner("1\nPriyanka Sengupta\n");
        int participantId = Main.createparticipant(sc);

        assertNotNull(Main.participants.get(participantId));
        assertEquals("Priyanka Sengupta", Main.participants.get(participantId).getName());
    }

    @Test
    public void testCreateInstructor() {
        Scanner sc = new Scanner("1\nInstructor 1\n");
        int instructorId = Main.createInstructor(sc);

        assertNotNull(Main.instructors.get(instructorId));
        assertEquals("Instructor 1", Main.instructors.get(instructorId).getName());
    }
}

