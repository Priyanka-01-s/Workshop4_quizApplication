import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.Participant;
import com.example.Question;
import com.example.Quiz;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

class QuizApplicationTest {

    private Quiz quiz;
    private InputStream originalSystemIn;

    @BeforeEach
    void setUp() {
        quiz = new Quiz(1);
        originalSystemIn = System.in;
    }

    @Test
    void testCreateQuizAndAddQuestions() {
        String simulatedInput = "3\n" + //number of questions
                "What is the capital of India?\n" +
                "yes\n" + // MCQ
                "Mumbai\n" +
                "Delhi\n" +
                "Kolkata\n" +
                "Chennai\n" +
                "2\n" + 
                "easy\n" + 

                "Sun rises from which direction?\n" +
                "no\n" + // Open-ended
                "East\n" +
                "sun rises from East\n"; 

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        quiz.createQuiz(new Scanner(System.in));
        List<Question> questions = quiz.getQuestions();

        System.setIn(originalSystemIn);
        assertNotNull(questions, "Questions list should be initialized");
        assertEquals(3, questions.size(), "There should be 3 questions in the quiz");

        Question firstQuestion = questions.get(0);
        assertTrue(firstQuestion.getOptions() != null, "The first question should be MCQ");
        assertEquals("easy", firstQuestion.getCategories().get(0), "Difficulty level should be 'easy'");

        Question secondQuestion = questions.get(1);
        assertTrue(secondQuestion.getOptions() == null, "The second question should be open-ended");
        assertEquals("medium", secondQuestion.getCategories().get(0), "Difficulty level should be 'medium'");
        assertEquals("Explanation for the open-ended question", secondQuestion.getExplanation(), "Explanation should match");

        Question thirdQuestion = questions.get(2);
        assertTrue(thirdQuestion.getOptions() != null, "The third question should be MCQ");
        assertEquals("medium", thirdQuestion.getCategories().get(0), "Difficulty level should be 'medium'");
    }

    @Test
    void testParticipantRegistration() {
        int participantId = 1;
        String participantName = "PSG"; 

        Participant participant = new Participant(participantId, participantName);
        assertEquals(participantId, participant.getParticipantId(), "Participant ID should be set correctly");
        assertEquals(participantName, participant.getName(), "Participant name should be set correctly");
    }

    @Test
    void testQuizCreationAndAttempt() {
        int quizID = 1;
        Quiz quiz = new Quiz(quizID);
        Participant participant = new Participant(1, "John Doe");
        String simulatedInput = "1\n2\n3\n4\n1\neasy\n";
        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner simulatedScanner = new Scanner(inputStream);
        int participantScore = quiz.TakeQuiz(simulatedScanner);

        // Assert
        assertNotNull(participant.getScore(quizID), "Participant score should not be null");
        assertTrue(participantScore >= 0, "Participant score should be non-negative");
    }

    @Test
    void testRandomizedQuestions() {
        int quizID = 2;
        Quiz quiz = new Quiz(quizID);
        int numQuestionsBeforeRandomization = quiz.getQuestions().size();
        quiz.randomizedQues();
        int numQuestionsAfterRandomization = quiz.getQuestions().size();
        assertEquals(numQuestionsBeforeRandomization, numQuestionsAfterRandomization,
                "Number of questions should remain the same after randomization");
    }

}
