package com.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class LeaderBoard {

    private List<Participant> participants;

    public LeaderBoard() {
        this.participants = new ArrayList<>();
    }

    public void updateLeaderboard(Map<Integer, Participant> participants, int quizID) {
        // Extract scores for the given quiz
        List<QuizScore> quizScores = new ArrayList<>();
        for (Map.Entry<Integer, Participant> entry : participants.entrySet()) {
            int participantID = entry.getKey();
            Participant participant = entry.getValue();
            Integer score = participant.getScore(quizID);

            if (score != Integer.MIN_VALUE) {
                quizScores.add(new QuizScore(participantID, participant.getName(), score));
            }
        }

        // sort the scores in descending order
        Collections.sort(quizScores, Collections.reverseOrder());

        participants.clear();
        for (int i = 0; i < Math.min(quizScores.size(), 10); i++) {
            QuizScore quizScore = quizScores.get(i);
            participants.put(quizScore.getParticipantID(), new Participant(quizScore.getParticipantID(), quizScore.getParticipantName()));
        }
    }

    public void displayLeaderboard() {
        System.out.println("\n--- Leaderboard ---");
        for (Participant participant : participants) {
            System.out.println(participant.toString());
        }
        System.out.println();
    }

    private static class QuizScore implements Comparable<QuizScore> {
        private int participantID;
        private String participantName;
        private int score;

        public QuizScore(int participantID, String participantName, int score) {
            this.participantID = participantID;
            this.participantName = participantName;
            this.score = score;
        }

        public int getParticipantID() {
            return participantID;
        }

        public String getParticipantName() {
            return participantName;
        }

        @Override
        public int compareTo(QuizScore other) {
            return Integer.compare(this.score, other.score);
        }
    }
}

