package QuizSystem;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class StudentQuiz extends javax.swing.JFrame {
    private final JButton startQuizButton;
    private Map<String, Integer> studentScores;  // Map to store student names and scores

    public StudentQuiz() {
        setTitle("Student Dashboard");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        startQuizButton = new JButton("Start Quiz");

        setLayout(new GridLayout(1, 1));
        add(startQuizButton);

        startQuizButton.addActionListener((ActionEvent e) -> {
            startQuiz();
        });

        studentScores = new HashMap<>();
    }

    private void startQuiz() {
         try {
        FileReader fileReader = new FileReader("question_bank.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line;
        int questionCount = 1;
        int score = 0;

        while ((line = bufferedReader.readLine()) != null) {
            String question = line;
            String optionA = bufferedReader.readLine();
            String optionB = bufferedReader.readLine();
            String optionC = bufferedReader.readLine();
            String optionD = bufferedReader.readLine();
            String correctAnswer = bufferedReader.readLine(); // Read the correct answer

            // Display the question and options to the student
            int selectedOption = JOptionPane.showOptionDialog(
                    this,
                    "Question " + questionCount + ": " + question,
                    "Quiz",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[]{optionA, optionB, optionC, optionD},
                    optionA);

            // Convert the selected option index to the corresponding letter (A, B, C, D)
            String selectedAnswer = String.valueOf((char) ('A' + selectedOption));

            // Check if the selected answer is correct
            if (selectedAnswer.equals(correctAnswer)) {
                score++; // Increase the score if the answer is correct
            }

            questionCount++;
        }

        bufferedReader.close();

        // Display the final score to the student
        String studentName = JOptionPane.showInputDialog(this, "Enter your name:");
        studentScores.put(studentName, score);
        JOptionPane.showMessageDialog(this, "Quiz completed!\nYour score: " + score);

        // Now, you can call the method to send the scores to the Teacher class
        Teacher.storeStudentScores(studentScores);

    } catch (IOException ex) {
        JOptionPane.showMessageDialog(this, "An error occurred while loading the quiz.", "Error", JOptionPane.ERROR_MESSAGE);
    }
}
    public Map<String, Integer> getStudentScores() {
        return studentScores;
    }


    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new StudentQuiz().setVisible(true);
        });
    }
}
