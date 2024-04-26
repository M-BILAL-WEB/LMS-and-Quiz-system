package QuizSystem;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JButton;
import javax.swing.Timer;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.Map;
public class Teacher extends JFrame {
    private static LinkedList<String> studentNames;  // LinkedList to store student names

    
    private final JButton createQuestionBankButton;
    private final JButton setTimerButton;
    private final JButton markQuizButton;
    private final JButton studentMarksButton;  // New button
    private final LinkedList<QuestionNode> questionList;
    private final LinkedList<String> studentName;  // LinkedList to store student names

    public Teacher() {
        setTitle("Teacher Dashboard");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        createQuestionBankButton = new JButton("Create Question Bank");
        setTimerButton = new JButton("Set Timer");
        markQuizButton = new JButton("Mark Quiz");
        studentMarksButton = new JButton("Student Marks");  // Initialize the new button
        questionList = new LinkedList<>();
        studentName = new LinkedList<>();  // Initialize the LinkedList for student names

        setLayout(new GridLayout(4, 1));
        add(createQuestionBankButton);
        add(setTimerButton);
        add(markQuizButton);
        add(studentMarksButton);  // Add the new button

        createQuestionBankButton.addActionListener((ActionEvent e) -> {
            createQuestionBank();
        });

        setTimerButton.addActionListener((ActionEvent e) -> {
            setTimer();
        });

        markQuizButton.addActionListener((ActionEvent e) -> {
            markQuiz();
        });

        studentMarksButton.addActionListener((ActionEvent e) -> {
            // Get the student scores from the StudentQuiz instance
            Map<String, Integer> studentScores = getStudentScores();
            // Call the method to display student marks
            showStudentMarks(studentScores);
        });
    }


    private void createQuestionBank() {
        String question = JOptionPane.showInputDialog(this, "Enter the question:");
        String optionA = JOptionPane.showInputDialog(this, "Enter option A:");
        String optionB = JOptionPane.showInputDialog(this, "Enter option B:");
        String optionC = JOptionPane.showInputDialog(this, "Enter option C:");
        String optionD = JOptionPane.showInputDialog(this, "Enter option D:");
        String correctAnswer = JOptionPane.showInputDialog(this, "Enter the correct answer (A, B, C, or D):");

        // Create a new question node and add it to the linked list
        QuestionNode newQuestion = new QuestionNode(question, optionA, optionB, optionC, optionD, correctAnswer);
        questionList.add(newQuestion);

        // Save the question to a file
        try {
            FileWriter writer = new FileWriter("question_bank.txt", true);
            writer.write(question + "\n");
            writer.write(optionA + "\n");
            writer.write(optionB + "\n");
            writer.write(optionC + "\n");
            writer.write(optionD + "\n");
            writer.write(correctAnswer + "\n");
            writer.close();
            JOptionPane.showMessageDialog(this, "Question added to the question bank!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "An error occurred while saving the question.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
     private Map<String, Integer> getStudentScores() {
        // Assuming StudentQuiz is a Singleton or you have a reference to the specific instance
        // Replace 'yourStudentQuizInstance' with the actual reference to your StudentQuiz instance
        StudentQuiz studentQuizInstance = /* instantiate or get reference */;
    return studentQuizInstance != null ? studentQuizInstance.getStudentScores() : null;
    }
      public static void storeStudentScores(Map<String, Integer> studentScores) {
        // Save the student names and scores to "student_scores.txt"
        try (FileWriter writer = new FileWriter("student_scores.txt", true)) {
            for (Map.Entry<String, Integer> entry : studentScores.entrySet()) {
                writer.write(entry.getKey() + "," + entry.getValue() + "\n");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "An error occurred while saving student scores.", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    private void setTimer() {
        String timeLimitString = JOptionPane.showInputDialog(this, "Set the time limit for the quiz (in seconds):");
        int timeLimitInSeconds = Integer.parseInt(timeLimitString);

        Timer timer = new Timer(timeLimitInSeconds * 1000, (ActionEvent e) -> {
            // Timer logic, you can add actions to be performed when the timer expires
        });
        timer.setRepeats(false); // Set the timer to fire only once
        timer.start(); // Start the timer

        JOptionPane.showMessageDialog(this, "Quiz created successfully! You have " + timeLimitInSeconds + " seconds to complete the quiz.");
    }

    private void markQuiz() {
        // Implement logic to mark the quiz using the questionList
        JOptionPane.showMessageDialog(this, "Quiz marked successfully!");
    }
    
   private void showStudentMarks(Map<String, Integer> studentScores) {
        StringBuilder studentMarks = new StringBuilder("Student Marks:\n");

        // Check if studentScores is not null before accessing it
        if (studentScores != null) {
            for (Map.Entry<String, Integer> entry : studentScores.entrySet()) {
                String studentName = entry.getKey();
                int quizScore = entry.getValue();
                studentMarks.append(studentName).append(": ").append(quizScore).append("\n");
            }
        } else {
            studentMarks.append("No student scores available.");
        }

        JOptionPane.showMessageDialog(this, studentMarks.toString(), "Student Marks", JOptionPane.INFORMATION_MESSAGE);
    }
 private Map<String, Integer> readStudentScoresFromFile() {
        Map<String, Integer> studentScores = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("student_scores.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String studentName = parts[0];
                    int quizScore = Integer.parseInt(parts[1]);
                    studentScores.put(studentName, quizScore);
                }
            }
        } catch (IOException | NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while reading student scores.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        return studentScores;
    }

    private int generateRandomQuizScore() {
        // Replace with actual logic to generate random quiz scores
        return (int) (Math.random() * 100);
    }

    public static void main(String[] args) {
        Teacher dashboard = new Teacher();
        dashboard.setVisible(true);
    }
}

class QuestionNode {
    String question;
    String optionA;
    String optionB;
    String optionC;
    String optionD;
    String correctAnswer;

    public QuestionNode(String question, String optionA, String optionB, String optionC, String optionD, String correctAnswer) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
    }
}
