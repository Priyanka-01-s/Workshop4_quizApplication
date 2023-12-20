package com.example;

import java.util.List;

public class Question {
    String question;
    String[] options;// mcq
    private int correctOption;// mcq
    private String correctAns;// open-end
    private String participantAnswer;//open-end
    private String explanation;//explanation
    private List<String> categories;

    // For MCQ questions
    public Question(String question, String[] options, int correctOption, List<String> categories) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
        this.correctAns = null; // No expected answer
        this.explanation = null; 
        this.categories = categories;
    }

    // For open-ended questions
    public Question(String question, String correctAns, String explanation) {
        this.question = question;
        this.options = null;
        this.correctOption = -1;
        this.correctAns = correctAns;
        this.explanation = explanation;

    }

    public String[] getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAns;
    }

    public String getExplanation() {
        return explanation;
    }

    public List<String> getCategories(){
        return categories;
    }

    public void setParticipantAns(String participantAnswer) {
        this.participantAnswer = participantAnswer;
    }

    public boolean checkAnswer(int choice, String participantAnswer) {
        if (options != null) {
            // MCQ
            return choice == correctOption;
        } else {
            // open-ended
            return participantAnswer.equalsIgnoreCase(correctAns);
        }
    }

    @Override
    public String toString() {
        if (options != null) {
            // MCQ
            return this.question
                    + "\nOPTION 1->" + this.options[0]
                    + "\nOPTION 2-> " + this.options[1]
                    + "\nOPTION 3->" + this.options[2]
                    + "\nOPTION 4->" + this.options[3];
        } else {
            // Open-ended
            return this.question;
        }
    }
}
