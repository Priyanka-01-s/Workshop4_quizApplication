package com.example;

import java.util.HashMap;
import java.util.Scanner;

import com.exceptions.*;

public class Main {

    public static final String INSTRUCTOR_CSV_PATH = "QuizFile.csv";
    public static final String participant_CSV_PATH = "Participants.csv";
    public static final String QUIZ_CSV_PATH = "Quiz.csv";
    public static HashMap<Integer, Quiz> quizzes = new HashMap<>();
    public static HashMap<Integer, Participant> participants = new HashMap<>();
    public static HashMap<Integer, Instructor> instructors = new HashMap<>();
    public static LeaderBoard leaderBoard = new LeaderBoard();

    public static void main(String args[])
            throws QuizNotFoundException, ParticipantNotFoundException, InstructorNotFoundException {
        CSVLoader.createFile(INSTRUCTOR_CSV_PATH);
        CSVLoader.createFile(participant_CSV_PATH);
        CSVLoader.createFile(QUIZ_CSV_PATH);
        Scanner sc = new Scanner(System.in);
        System.out.println("--------------------QUIZ APPLICATION-------------------");

        while (true) {
            System.out.println("1.Are you an Instructor?");
            System.out.println("2.Are you a Participant?");
            System.out.println("3. View the leaderboard");
            System.out.println("Enter your choice: ");
            int role = sc.nextInt();

            boolean exitLoop = false;

            switch (role) {
                case 1:
                    int instId = createInstructor(sc);

                    if (instructors.containsKey(instId)) {
                        while (!exitLoop) {
                            System.out.println("------------------INSTRUCTOR MENU------------------");
                            System.out.println("1. Add new quiz.");
                            System.out.println("2. Manage quiz.");
                            System.out.println("3. Do you want to randomized the quiz created for the participants?");
                            System.out.println("0. EXIT");
                            System.out.println("Enter your choice: ");
                            int choice = sc.nextInt();
                            sc.nextLine();

                            switch (choice) {
                                case 0:
                                    exitLoop = true;
                                    break;
                                case 1:
                                    addQuiz(sc);
                                    break;
                                case 2:
                                    manageQuiz(sc);
                                    break;
                                case 3:
                                    randomized(sc);
                                    break;

                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                        }
                    } else {
                        System.out.println("Instructor not found. Please enter a valid instructor ID.");
                    }

                    break;
                case 2:
                    int participantId = createparticipant(sc);

                    if (participants.containsKey(participantId)) {
                        while (!exitLoop) {
                            System.out.println("------------------PARTICIPANT MENU------------------");
                            System.out.println("1. Take a quiz.");
                            System.out.println("2. View quiz results.");
                            System.out.println("0. EXIT");
                            System.out.print("Enter your choice: ");
                            int choice = sc.nextInt();
                            sc.nextLine();

                            switch (choice) {
                                case 0:
                                    exitLoop = true;
                                    break;
                                case 1:
                                    playQuiz(sc);
                                    break;
                                case 2:
                                    viewparticipantScore(sc);
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                            }
                        }
                    } else {
                        throw new ParticipantNotFoundException("Participant not found!\n");
                    }
                case 3:
                    leaderBoard.displayLeaderboard();

                default:
                    System.out.println("Invalid role selection. Exiting...");
                    break;
            }

        }
    }

    // create new participant
    public static int createparticipant(Scanner sc) {
        System.out.print("Enter participant ID: ");
        int newParticipantId = sc.nextInt();
        sc.nextLine();

        if (!participants.containsKey(newParticipantId)) {
            System.out.print("Enter participant name: ");
            String newName = sc.nextLine();
            Participant newParticipant = new Participant(newParticipantId, newName);
            participants.put(newParticipantId, newParticipant);
            CSVLoader.writeparticipant(newParticipant, participant_CSV_PATH);
            System.out.println("participant added successfully!\n");

        } else {
            System.out.println("participant already exists!\n");
        }
        return newParticipantId;
    }

    // create new instructor
    public static int createInstructor(Scanner sc) {
        System.out.print("Enter instructor ID: ");
        int newInstId = sc.nextInt();
        sc.nextLine();
        if (!instructors.containsKey(newInstId)) {
            System.out.print("Enter instructor name: ");
            String newName = sc.nextLine();
            Instructor newInstructor = new Instructor(newInstId, newName);
            instructors.put(newInstId, newInstructor);
            CSVLoader.writeInstructor(newInstructor, INSTRUCTOR_CSV_PATH);
            System.out.println("Instructor added successfully.\n");
        } else {
            System.out.println("Instructor already exists!\n");
        }
        return newInstId;
    }

    // method to add new quiz by instructor
    public static void addQuiz(Scanner sc) throws InstructorNotFoundException {
        System.out.print("Enter the instructor ID: ");
        int instId = sc.nextInt();
        sc.nextLine();
        if (instructors.containsKey(instId)) {
            System.out.print("Enter the quiz ID: ");
            int quiz_id = sc.nextInt();
            if (!quizzes.containsKey(quiz_id)) {
                Quiz new_quiz = new Quiz(quiz_id);
                quizzes.put(quiz_id, new_quiz);
                instructors.get(instId).addQuiz(quiz_id, new_quiz);
                CSVLoader.writeQuizToCSV(new_quiz, QUIZ_CSV_PATH);
            } else {
                System.out.println("Quiz already exists!\n");
            }
        } else {
            throw new InstructorNotFoundException("Instructor not found!\n");
        }
    }

    // method to play a quiz by participant
    public static void playQuiz(Scanner sc) throws QuizNotFoundException, ParticipantNotFoundException {
        System.out.print("Enter participant ID: ");
        int participant_id = sc.nextInt();
        sc.nextLine();
        if (participants.containsKey(participant_id)) {
            System.out.print("Enter quiz ID: ");
            int play_quiz_id = sc.nextInt();
            sc.nextLine();
            if (quizzes.containsKey(play_quiz_id)) {
                Quiz participant_quiz = quizzes.get(play_quiz_id);
                Integer score = participant_quiz.TakeQuiz(sc);
                participants.get(participant_id).updateScore(play_quiz_id, score);
                System.out.println("\nQuiz Completed successfully. Your score is " + score);
            } else {
                throw new QuizNotFoundException("Quiz not found!\n");
            }
        } else {
            throw new ParticipantNotFoundException("participant not found!\n");
        }
    }

    // view participant's score
    public static void viewparticipantScore(Scanner sc) throws QuizNotFoundException, ParticipantNotFoundException {
        System.out.print("Enter participant ID: ");
        int participantId = sc.nextInt();
        sc.nextLine();
        if (participants.containsKey(participantId)) {
            System.out.print("Enter quiz ID: ");
            int quizId = sc.nextInt();
            sc.nextLine();
            if (quizzes.containsKey(quizId)) {
                Integer participant_score = participants.get(participantId).getScore(quizId);
                if (participant_score != Double.MIN_VALUE) {
                    System.out.println("RESULT : Participant " + participantId + " scored " + participant_score
                            + " in quiz " + quizId + "\n");
                }
            } else {
                throw new QuizNotFoundException("Quiz not found!\n");
            }
        } else {
            throw new ParticipantNotFoundException("participant not found!\n");
        }
    }

    // manage quiz
    public static void manageQuiz(Scanner sc) throws QuizNotFoundException, InstructorNotFoundException {
        System.out.print("Enter instructor ID: ");
        int instIdManage = sc.nextInt();
        sc.nextLine();
        if (instructors.containsKey(instIdManage)) {
            System.out.print("Enter quiz ID: ");
            int quizManage = sc.nextInt();
            sc.nextLine();
            if (quizzes.containsKey(quizManage)) {
                instructors.get(instIdManage).manageQuiz(quizManage, sc);
            } else {
                throw new QuizNotFoundException("Quiz not found!\n");
            }
        } else {
            throw new InstructorNotFoundException("Instructor not found!\n");
        }
    }

    public static void randomized(Scanner sc) {
        System.out.println("Enter your Instructor ID");
        int instId = sc.nextInt();

        if (instructors.containsKey(instId)) {
            System.out.println("Enter the quiz ID you want to randomized");
            int quizId = sc.nextInt();
            if (quizzes.containsKey(quizId)) {
                Quiz quiz = quizzes.get(quizId);
                quiz.randomizedQues();
                System.out.println("Questions in quiz " + quizId + " randomized successfully!\n");
            } else {
                System.out.println("Quiz not found!\n");
            }
        } else {
            System.out.println("Instructor not found!\n");
        }
    }
}
