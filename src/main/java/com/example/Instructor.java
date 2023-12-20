package com.example;

import java.util.HashMap;
import java.util.Scanner;

public class Instructor {
    int instructorID;
    String name;
    HashMap<Integer, Quiz> quizs;

    public Instructor(int instructorID, String name) {
        this.instructorID = instructorID;
        this.name = name;
        quizs = new HashMap<>();
    }

    public void addQuiz(int quizID, Quiz quiz) {
        this.quizs.put(quizID, quiz);
    }

    public void manageQuiz(int quizID, Scanner sc) {
        if (this.quizs.containsKey(quizID)) {
            Quiz quiz = quizs.get(quizID);
            System.out.println("Following can be done for a quiz.");
            System.out.println("1. Add new question.");
            System.out.println("2. Edit existing question.");
            System.out.println("3. Delete existing question.");
            System.out.print("0. EXIT ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 0:
                    return;

                case 1:
                    quiz.addNewQuestion(sc);
                    break;

                case 2:
                    System.out.println("Current quiz");
                    System.out.println(quiz);
                    System.out.print("Enter the question number ypu want to edit ");
                    int editQues = sc.nextInt();
                    sc.nextLine();
                    quiz.editQuestion(editQues, sc);
                    break;

                case 3:
                    System.out.println("\nCurrent quiz");
                    System.out.println(quiz);
                    System.out.print("\nEnter the question number ypu want to delete");
                    int delQues = sc.nextInt();
                    sc.nextLine();
                    quiz.deleteQuestion(delQues);
                    System.out.println("Question deleted!!!\n");
                    break;

                default:
                    break;
            }
        } else {
            System.out.println("Quiz not found.");
        }
    }

    @Override
    public String toString() {
        return this.instructorID + "," + this.name;
    }
}