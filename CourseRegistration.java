
import java.sql.*;
import java.util.Scanner;

public class CourseRegistration {

    static final String URL =
        "jdbc:mysql://localhost:3306/college";
    static final String USER = "root";
    static final String PASSWORD = "your_password";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(
                URL, USER, PASSWORD
            );

            System.out.println("Database Connected!");

            System.out.print("Enter Course Code: ");
            String code = sc.nextLine();

            String query =
                "SELECT * FROM CourseRegistration " +
                "WHERE CourseCode = ?";

            PreparedStatement ps =
                con.prepareStatement(query);

            ps.setString(1, code);

            ResultSet rs = ps.executeQuery();

            boolean found = false;

            System.out.println("\nStudent Details:");

            while (rs.next()) {
                found = true;

                System.out.println("---------------------");

                System.out.println("Student ID: " +
                    rs.getInt("StudentID"));

                System.out.println("Student Name: " +
                    rs.getString("StudentName"));

                System.out.println("Course Code: " +
                    rs.getString("CourseCode"));

                System.out.println("Course Name: " +
                    rs.getString("CourseName"));

                System.out.println("Semester: " +
                    rs.getInt("Semester"));
            }

            if (!found) {
                System.out.println(
                    "No students registered for this course."
                );
            }

            rs.close();
            ps.close();
            con.close();
            sc.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
