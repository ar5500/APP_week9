
import java.sql.*;
import java.util.Scanner;

public class ProductManagement {

    static final String URL =
        "jdbc:mysql://localhost:3306/store";
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
                System.out.println("\n1. Insert Product");
                System.out.println("2. Search Product");
                System.out.println("3. Update Quantity");
                System.out.println("4. Display Products Below 10");
                System.out.println("5. Exit");
                System.out.print("Enter Choice: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1:
                        System.out.print("Product ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Product Name: ");
                        String name = sc.nextLine();

                        System.out.print("Price: ");
                        double price = sc.nextDouble();

                        System.out.print("Quantity: ");
                        int quantity = sc.nextInt();

                        String insert =
                            "INSERT INTO Product VALUES (?, ?, ?, ?)";

                        PreparedStatement ps1 =
                            con.prepareStatement(insert);

                        ps1.setInt(1, id);
                        ps1.setString(2, name);
                        ps1.setDouble(3, price);
                        ps1.setInt(4, quantity);

                        ps1.executeUpdate();

                        System.out.println("Product Inserted!");
                        ps1.close();
                        break;

                    case 2:
                        System.out.print("Enter Product ID: ");
                        int searchId = sc.nextInt();

                        String search =
                            "SELECT * FROM Product WHERE ProductID = ?";

                        PreparedStatement ps2 =
                            con.prepareStatement(search);

                        ps2.setInt(1, searchId);

                        ResultSet rs1 = ps2.executeQuery();

                        if (rs1.next()) {
                            System.out.println("Product ID: " +
                                rs1.getInt("ProductID"));
                            System.out.println("Name: " +
                                rs1.getString("ProductName"));
                            System.out.println("Price: " +
                                rs1.getDouble("Price"));
                            System.out.println("Quantity: " +
                                rs1.getInt("Quantity"));
                        } else {
                            System.out.println("Product Not Found!");
                        }

                        rs1.close();
                        ps2.close();
                        break;

                    case 3:
                        System.out.print("Enter Product ID: ");
                        int updateId = sc.nextInt();

                        System.out.print("Enter New Quantity: ");
                        int newQuantity = sc.nextInt();

                        String update =
                            "UPDATE Product SET Quantity = ? " +
                            "WHERE ProductID = ?";

                        PreparedStatement ps3 =
                            con.prepareStatement(update);

                        ps3.setInt(1, newQuantity);
                        ps3.setInt(2, updateId);

                        int rows = ps3.executeUpdate();

                        if (rows > 0) {
                            System.out.println("Quantity Updated!");
                        } else {
                            System.out.println("Product Not Found!");
                        }

                        ps3.close();
                        break;

                    case 4:
                        String display =
                            "SELECT * FROM Product WHERE Quantity < 10";

                        Statement st = con.createStatement();
                        ResultSet rs2 = st.executeQuery(display);

                        System.out.println("\nLow Stock Products:");

                        while (rs2.next()) {
                            System.out.println(
                                rs2.getInt("ProductID") + " " +
                                rs2.getString("ProductName") + " " +
                                rs2.getDouble("Price") + " " +
                                rs2.getInt("Quantity")
                            );
                        }

                        rs2.close();
                        st.close();
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
