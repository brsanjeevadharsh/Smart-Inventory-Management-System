import java.sql.*;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class Inventory {

    // ----------------- Add Product -----------------
    public static void addProduct(String name, int qty, double price) throws Exception {
        Connection con = DB.getConnection();
        String sql = "INSERT INTO products(name, quantity, price) VALUES (?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setString(1, name);
        ps.setInt(2, qty);
        ps.setDouble(3, price);
        ps.executeUpdate();
        con.close();
        System.out.println("Product added.");
    }

    // ----------------- List Products -----------------
    public static List<Product> listProducts() throws Exception {
        Connection con = DB.getConnection();
        String sql = "SELECT * FROM products";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        List<Product> list = new ArrayList<>();

        while (rs.next()) {
            Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("quantity"),
                    rs.getDouble("price")
            );
            list.add(p);
        }
        con.close();
        return list;
    }

    // ----------------- Sell Product -----------------
    public static void sellProduct(int id, int qty, String email) throws Exception {
        Connection con = DB.getConnection();

        // Get current product
        String select = "SELECT * FROM products WHERE id=?";
        PreparedStatement getPs = con.prepareStatement(select);
        getPs.setInt(1, id);
        ResultSet rs = getPs.executeQuery();

        if (!rs.next()) {
            System.out.println("Product not found!");
            return;
        }

        int currentQty = rs.getInt("quantity");
        double price = rs.getDouble("price");
        String name = rs.getString("name");

        if (currentQty < qty) {
            System.out.println("Not enough stock!");
            return;
        }

        // Update quantity
        String update = "UPDATE products SET quantity=? WHERE id=?";
        PreparedStatement updPs = con.prepareStatement(update);
        updPs.setInt(1, currentQty - qty);
        updPs.setInt(2, id);
        updPs.executeUpdate();

        con.close();

        double total = qty * price;
        sendEmail(email, name, qty, total);

        System.out.println("Product sold and bill emailed.");
    }

    // ----------------- Send Email Bill -----------------
    private static void sendEmail(String to, String product, int qty, double total) {

        final String from = "yourgmail@gmail.com";
        final String password = "your_app_password_here";

        String subject = "Purchase Bill";
        String msg = "Thank you!\n\nProduct: " + product +
                "\nQuantity: " + qty +
                "\nTotal: " + total;

        try {

            Properties props = new Properties();

            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(from, password);
                        }
                    });

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(to)
            );

            message.setSubject(subject);
            message.setText(msg);

            Transport.send(message);

        } catch (Exception e) {
            System.out.println("Email failed: " + e);
        }
    }
}
