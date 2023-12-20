package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Quiz {
    int quizID;
    ArrayList<Question> questions;
    private static final int TIME_LIMIT = 30;
    private boolean randomized;

    public Quiz(int quizID) {
        Scanner sc = new Scanner(System.in);
        this.quizID = quizID;
        this.questions = new ArrayList<>();
        this.randomized = false;
        this.createQuiz(sc);
    }

    public int getQuizID() {
        return quizID;
    }

    public void createQuiz(Scanner sc) {
        System.out.print("Enter number of questions for quiz: ");
        int num = sc.nextInt();
        sc.nextLine();

        for (int qNum = 1; qNum <= num; qNum++) {
            System.out.print("\nEnter question " + qNum + ": ");
            String question = sc.nextLine();

            //Check - MCQ or open-ended
            System.out.print("Is it a Multiple Choice Question? (yes/no): ");
            String quesType = sc.nextLine();

            if ("yes".equalsIgnoreCase(quesType)) {
                createMCQQuestion(sc, question);
            } else {
                createOpenEndedQuestion(sc, question);
            }
        }

        System.out.println("Quiz created successfully!\n");
    }

    private void createMCQQuestion(Scanner sc, String question) {
        String[] options = new String[4];
        List<String> categories = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            System.out.print("Enter option " + (i + 1) + ": ");
            options[i] = sc.nextLine();
        }

        System.out.print("Enter correct answer option (1/2/3/4): ");
        int correctOption = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter difficulty level (easy/medium/hard) for the question: ");
        String difficulty = sc.nextLine();
        categories.add(difficulty);

        Question q = new Question(question, options, correctOption, categories);
        this.questions.add(q);
        System.out.println("Question added successfully!\n");
    }

    private void createOpenEndedQuestion(Scanner sc, String question) {
        System.out.print("Enter the correct answer for the open-ended question: ");
        String correctAnswer = sc.nextLine();
        System.out.println("Enter the proper explanation for this");
        String explanation = sc.nextLine();

        Question q = new Question(question, correctAnswer,explanation);
        this.questions.add(q);
        System.out.println("Question added successfully!\n");
    }

    public void randomizedQues() {
        Collections.shuffle(this.questions);
        randomized = true;
        System.out.println("Question shuffled successfully");
    }

    public List<Question> getQuestions() {
        if (randomized) {
            // will return a shuffled question
            return new ArrayList<>(questions);
        } else {
            return questions;
        }
    }

    // taking the quiz by participant
    public Integer TakeQuiz(Scanner sc) {
        int[] choices = new int[this.questions.size()];

        // adding timer for the participant
        Timer t = new Timer();
        for (int i = 0; i < this.questions.size(); i++) {
            System.out.println("Timer has started. You have 30 seconds to answer!!");
            Question currentQuestion = questions.get(i);
            System.out.println(currentQuestion);

            // starting the timer
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("Time's up!");
                    int timeScore = scoreQuiz(choices);
                    System.out.println("\nQuiz submitted! Your score is: " + timeScore);
                    System.exit(0);
                }
            };
            t.schedule(task, TIME_LIMIT * 1000);

            if (currentQuestion.getOptions() != null) {
                // MCQ
                System.out.print("Enter answer option (1/2/3/4): ");
                choices[i] = sc.nextInt();
                sc.nextLine();
            } else {
                // open-ended
                System.out.print("Enter your answer: ");
                String participantAnswer = sc.nextLine();
                currentQuestion.setParticipantAns(participantAnswer);
            }
        }
        displayFeedback();
        return scoreQuiz(choices);
    }

    public Integer scoreQuiz(int[] choices) {
        Integer score = 0;
        for (int i = 0; i < choices.length; i++) {
            if (choices[i] != 0 && this.questions.get(i).checkAnswer(choices[i], null)) {
                score++;
            }
        }
        return score;
    }

    public void addNewQuestion(Scanner sc) {
        System.out.print("\n\tEnter question: ");
        String question = sc.nextLine();

        // Check if the question is MCQ or open-ended
        System.out.print("Is it a Multiple Choice Question? (yes/no): ");
        String quesType = sc.nextLine();

        if ("yes".equalsIgnoreCase(quesType)) {
            createMCQQuestion(sc, question);
        } else {
            createOpenEndedQuestion(sc, question);
        }
    }

    public void editQuestion(int qNum, Scanner sc) {
        deleteQuestion(qNum);
        addNewQuestionAtIndex(qNum - 1, sc);
    }

    public void addNewQuestionAtIndex(int index, Scanner sc) {
        System.out.print("\n\tEnter question: ");
        String question = sc.nextLine();

        // Check if the question is MCQ or open-ended
        System.out.print("Is it a Multiple Choice Question? (yes/no): ");
        String quesType = sc.nextLine();

        if ("yes".equalsIgnoreCase(quesType)) {
            createMCQQuestion(sc, question);
        } else {
            createOpenEndedQuestion(sc, question);
        }
    }

    public void deleteQuestion(int qNum) {
        this.questions.remove(qNum - 1);
    }

    @Override
    public String toString() {
        if (this.questions.isEmpty()) {
            return "Quiz is empty.";
        }
        StringBuilder quiz = new StringBuilder();
        int qNum = 1;
        for (Question question : this.questions) {
            quiz.append("\n").append(qNum).append(")\n").append(question.toString());
            qNum++;
        }
        return quiz.toString();
    }

    private void displayFeedback(){
        System.out.println("-------YOUR FEEDBACK------------");
        for(int i=0;i<this.questions.size();i++){
            Question currQuestion = questions.get(i);
            System.out.println("QUESTION "+(i+1)+" -> "+currQuestion);
            System.out.println("CORRECT ANSWER "+currQuestion.getCorrectAnswer());
            if (currQuestion.getExplanation() != null) {
                System.out.println("EXPLANATION: " + currQuestion.getExplanation());
            }
            System.out.println();
        }
    }
}