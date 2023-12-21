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
    private int quizID;

    // For MCQ questions
    public Question(String question, String[] options, int correctOption, List<String> categories,int quizID) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
        this.correctAns = null; // No expected answer
        this.explanation = null; 
        this.categories = categories;
        this.quizID = quizID;
    }
    

    // For open-ended questions
    public Question(String question, String correctAns, String explanation,int quizID) {
        this.question = question;
        this.options = null;
        this.correctOption = -1;
        this.correctAns = correctAns;
        this.explanation = explanation;

    }

    public String getQuestion(){
        return question;
    }

    public int getQuizID() {
        return quizID;
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
        StringBuilder result = new StringBuilder(this.question);

        if (options != null) {
            // MCQ
            for (int i = 0; i < options.length; i++) {
                result.append("\nOPTION ").append(i + 1).append("->").append(options[i]);
            }
        }

        return result.toString();
    }
}
