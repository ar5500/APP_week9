
import java.sql.*;
import java.util.Scanner;

public class BookManagement {

    static final String URL =
        "jdbc:mysql://localhost:3306/library";
    static final String USER = "root";
    static final String PASSWORD = "your_password";

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection(
                URL, USER, PASSWORD
            );

            System.out.println("Database Connected!");

            while (true) {
                System.out.println("\n1. Insert Book");
                System.out.println("2. Search Book");
                System.out.println("3. Display Available Books");
                System.out.println("4. Issue Book");
                System.out.println("5. Exit");
                System.out.print("Enter Choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1:
                        System.out.print("Book ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Title: ");
                        String title = sc.nextLine();

                        System.out.print("Author: ");
                        String author = sc.nextLine();

                        System.out.print("Price: ");
                        double price = sc.nextDouble();

                        String insert =
                            "INSERT INTO Book VALUES (?, ?, ?, ?, ?)";

                        PreparedStatement ps1 =
                            con.prepareStatement(insert);

                        ps1.setInt(1, id);
                        ps1.setString(2, title);
                        ps1.setString(3, author);
                        ps1.setDouble(4, price);
                        ps1.setBoolean(5, true);

                        ps1.executeUpdate();

                        System.out.println("Book Inserted!");
                        ps1.close();
                        break;

                    case 2:
                        System.out.print("Enter Book ID: ");
                        int searchId = sc.nextInt();

                        String search =
                            "SELECT * FROM Book WHERE BookID = ?";

                        PreparedStatement ps2 =
                            con.prepareStatement(search);

                        ps2.setInt(1, searchId);

                        ResultSet rs1 = ps2.executeQuery();

                        if (rs1.next()) {
                            System.out.println("Book ID: " +
                                rs1.getInt("BookID"));
                            System.out.println("Title: " +
                                rs1.getString("Title"));
                            System.out.println("Author: " +
                                rs1.getString("Author"));
                            System.out.println("Price: " +
                                rs1.getDouble("Price"));
                            System.out.println("Available: " +
                                rs1.getBoolean("Availability"));
                        } else {
                            System.out.println("Book Not Found!");
                        }

                        rs1.close();
                        ps2.close();
                        break;

                    case 3:
                        String display =
                            "SELECT * FROM Book WHERE Availability = TRUE";

                        Statement st = con.createStatement();
                        ResultSet rs2 = st.executeQuery(display);

                        System.out.println("\nAvailable Books:");

                        while (rs2.next()) {
                            System.out.println(
                                rs2.getInt("BookID") + " " +
                                rs2.getString("Title") + " " +
                                rs2.getString("Author") + " " +
                                rs2.getDouble("Price")
                            );
                        }

                        rs2.close();
                        st.close();
                        break;

                    case 4:
                        System.out.print("Enter Book ID to Issue: ");
                        int issueId = sc.nextInt();

                        String update =
                            "UPDATE Book SET Availability = FALSE " +
                            "WHERE BookID = ? AND Availability = TRUE";

                        PreparedStatement ps3 =
                            con.prepareStatement(update);

                        ps3.setInt(1, issueId);

                        int rows = ps3.executeUpdate();

                        if (rows > 0) {
                            System.out.println("Book Issued Successfully!");
                        } else {
                            System.out.println(
                                "Book Not Found or Already Issued!"
                            );
                        }

                        ps3.close();
                        break;

                    case 5:
                        con.close();
                        sc.close();
                        System.out.println("Program Terminated.");
                        return;

                    default:
                        System.out.println("Invalid Choice!");
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
