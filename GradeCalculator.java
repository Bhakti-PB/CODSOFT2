import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GradeCalculator extends JFrame {
    private JTextField subjectField;
    private JButton submitSubjectsBtn;
    private JPanel marksPanel;
    private JButton calculateBtn;
    private JTextArea resultArea;
    private int subjects;

    public GradeCalculator() {
        setTitle("Grade Calculator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel (input subjects)
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Enter number of subjects: "));
        subjectField = new JTextField(5);
        topPanel.add(subjectField);
        submitSubjectsBtn = new JButton("Submit");
        topPanel.add(submitSubjectsBtn);
        add(topPanel, BorderLayout.NORTH);

        // Marks input panel
        marksPanel = new JPanel();
        marksPanel.setLayout(new GridLayout(0, 2, 10, 10));
        add(marksPanel, BorderLayout.CENTER);

        // Result display
        resultArea = new JTextArea(5, 40);
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        // Button action
        submitSubjectsBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    subjects = Integer.parseInt(subjectField.getText());
                    if (subjects <= 0) {
                        JOptionPane.showMessageDialog(null, "Enter a valid number of subjects!");
                        return;
                    }

                    marksPanel.removeAll(); // clear old inputs
                    for (int i = 1; i <= subjects; i++) {
                        marksPanel.add(new JLabel("Marks for Subject " + i + " (0-100):"));
                        marksPanel.add(new JTextField());
                    }

                    calculateBtn = new JButton("Calculate Grade");
                    marksPanel.add(calculateBtn);

                    calculateBtn.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent ev) {
                            calculateGrade();
                        }
                    });

                    marksPanel.revalidate();
                    marksPanel.repaint();

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid number!");
                }
            }
        });
    }

    private void calculateGrade() {
        int totalMarks = 0;
        Component[] components = marksPanel.getComponents();
        int index = 1;

        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof JTextField) {
                JTextField field = (JTextField) components[i];
                try {
                    int marks = Integer.parseInt(field.getText());
                    if (marks < 0 || marks > 100) {
                        JOptionPane.showMessageDialog(null, "Marks must be between 0 and 100 for subject " + index);
                        return;
                    }
                    totalMarks += marks;
                    index++;
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Enter valid marks for subject " + index);
                    return;
                }
            }
        }

        double averagePercentage = (double) totalMarks / subjects;

        String grade;
        if (averagePercentage >= 90) {
            grade = "A+";
        } else if (averagePercentage >= 80) {
            grade = "A";
        } else if (averagePercentage >= 70) {
            grade = "B";
        } else if (averagePercentage >= 60) {
            grade = "C";
        } else if (averagePercentage >= 50) {
            grade = "D";
        } else {
            grade = "F";
        }

        resultArea.setText("RESULT:\n");
        resultArea.append("Total Marks: " + totalMarks + "\n");
        resultArea.append(String.format("Average Percentage: %.2f%%\n", averagePercentage));
        resultArea.append("Grade: " + grade);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GradeCalculator().setVisible(true));
    }
}
