package com.example;

import java.util.HashMap;

public class Participant {
    int participantID;
    String name;
    HashMap<Integer, Integer> participantQuiz;

    public Participant(int participantID, String name) {
        this.participantID = participantID;
        this.name = name;
        this.participantQuiz = new HashMap<>();
    }

    public int getParticipantId(){
        return participantID;
    }

    public String getName(){
        return name;
    }

    public void updateScore(int quizID, int score) {
        this.participantQuiz.put(quizID, score);
    }

    public Integer getScore(int quizID) {
        if (this.participantQuiz.containsKey(quizID)) {
            return this.participantQuiz.get(quizID);
        } else {
            System.out.println("Participant not found\n");
            return Integer.MIN_VALUE;
        }
    }

    @Override
    public String toString() {
        return this.participantID + "," + this.name;
    }
}