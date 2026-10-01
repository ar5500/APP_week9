import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Student {
    private String name;
    private int[] marks;
    private int total;
    private double average;
    private String grade;

    public Student(String name, int[] marks) {
        this.name = name;
        this.marks = marks;
        calculateResult();
    }

    private void calculateResult() {
        total = 0;
        for (int m : marks) total += m;
        average = total / (double) marks.length;

        if (average >= 90) grade = "A";
        else if (average >= 75) grade = "B";
        else if (average >= 60) grade = "C";
        else if (average >= 50) grade = "D";
        else grade = "F";
    }

    public String getName() { return name; }
    public int getTotal() { return total; }
    public double getAverage() { return average; }
    public String getGrade() { return grade; }
}


class StudentView extends JFrame {
    JTextField nameField = new JTextField(15);
    JTextField mark1Field = new JTextField(5);
    JTextField mark2Field = new JTextField(5);
    JTextField mark3Field = new JTextField(5);
    JButton calculateButton = new JButton("Calculate Result");
    JTextArea resultArea = new JTextArea(5, 25);

    public StudentView() {
        setTitle("Student Grade Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Student Name:"));
        add(nameField);

        add(new JLabel("Marks in Subject 1:"));
        add(mark1Field);

        add(new JLabel("Marks in Subject 2:"));
        add(mark2Field);

        add(new JLabel("Marks in Subject 3:"));
        add(mark3Field);

        add(calculateButton);

        resultArea.setEditable(false);
        add(new JScrollPane(resultArea));

        setVisible(true);
    }

    public void displayResult(String result) {
        resultArea.setText(result);
    }
}

class StudentController {
    private StudentView view;

    public StudentController(StudentView view) {
        this.view = view;
        this.view.calculateButton.addActionListener(new CalculateListener());
    }

    class CalculateListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                String name = view.nameField.getText();
                int m1 = Integer.parseInt(view.mark1Field.getText());
                int m2 = Integer.parseInt(view.mark2Field.getText());
                int m3 = Integer.parseInt(view.mark3Field.getText());

                Student student = new Student(name, new int[]{m1, m2, m3});

                String result = "Student: " + student.getName() +
                        "\nTotal Marks: " + student.getTotal() +
                        "\nAverage: " + student.getAverage() +
                        "\nGrade: " + student.getGrade();

                view.displayResult(result);
            } catch (NumberFormatException ex) {
                view.displayResult("Please enter valid numeric marks!");
            }
        }
    }
}

public class StudentGradeCalculator {
    public static void main(String[] args) {
        StudentView view = new StudentView();
        new StudentController(view);
    }
}
