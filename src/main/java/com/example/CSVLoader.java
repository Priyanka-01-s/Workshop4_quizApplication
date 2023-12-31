package com.example;

import java.nio.file.Path;
import java.util.HashSet;
import java.io.IOException;
import java.nio.file.Files;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;

public class CSVLoader {
    public static void createFile(String fileName) {
        if (!exists(fileName)) {
            Path path = Path.of(fileName);
            try {
                Files.createFile(path);
                System.out.println("Created file: " + fileName);
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        } else {
            System.out.println("File already exists!");
        }
    }

    public static void writeInstructor(Instructor instructor, String fileName) {
        if (exists(fileName)) {
            HashSet<Integer> uniqueIds = getAllUniqueIDs(fileName);
            if (uniqueIds != null && !uniqueIds.contains(instructor.instructorID)) {
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
                    if (countEntries(fileName) == 0) {
                        writer.println("INSTRUCTOR ID,INSTRUCTOR NAME");
                    }
                    writer.println(instructor.toString());
                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        }
    }

    public static void writeparticipant(Participant participant, String fileName) {
        if (exists(fileName)) {
            HashSet<Integer> uniqueIDs = getAllUniqueIDs(fileName);
            if (uniqueIDs != null && !uniqueIDs.contains(participant.participantID)) {
                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
                    if (countEntries(fileName) == 0) {
                        writer.println("PARTICIPANT ID,PARTICIPANT NAME");
                    }
                    writer.println(participant.toString());
                } catch (IOException exception) {
                    System.out.println(exception.getMessage());
                }
            }
        }
    }

    // public static void writeQuizToCSV(Quiz quiz, String fileName) {
    //     if (exists(fileName)) {
    //         try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
    //             if (countEntries(fileName) == 0) {
    //                 writer.println("Quiz ID,QUESTIONS"); // Add other headers as needed
    //             }
    //             writer.println(quiz.getQuizID() + "," + quiz.getQuestions()); // Add other quiz details as needed
    //         } catch (IOException exception) {
    //             System.out.println(exception.getMessage());
    //         }
    //     }
    // }
    public static void writeQuizToCSV(Quiz quiz, String fileName) {
        if (exists(fileName)) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, true))) {
                if (countEntries(fileName) == 0) {
                    writer.println("QUIZ ID,QUESTIONS"); 
                }
                StringBuilder questionsString = new StringBuilder();
                for (Question question : quiz.getQuestions()) {
                    questionsString.append(question.getQuestion()).append(";");
                }
    
                // Remove the trailing semicolon
                if (questionsString.length() > 0) {
                    questionsString.setLength(questionsString.length() - 1);
                }
    
                writer.println(quiz.getQuizID() + "," + questionsString.toString()); // Add other quiz details as needed
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }
    

    public static HashSet<Integer> getAllUniqueIDs(String fileName) {
        if (exists(fileName)) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String header = reader.readLine();
                HashSet<Integer> uniqueIDs = new HashSet<>();
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] lineData = line.split(",");
                    int id = Integer.parseInt(lineData[0]);
                    uniqueIDs.add(id);
                }
                return uniqueIDs;
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
                return null;
            }
        }
        return null;
    }

    public static int countEntries(String fileName) {
        if (exists(fileName)) {
            int lines = 0;
            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                while (reader.readLine() != null) {
                    lines++;
                }
                return lines;
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
                return -1;
            }
        } else {
            System.out.println("File does not exist.");
            return -1;
        }
    }

    public static boolean exists(String fileName) {
        Path path = Path.of(fileName);
        return Files.exists(path);
    }
}