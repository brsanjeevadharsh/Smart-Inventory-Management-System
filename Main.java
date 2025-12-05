import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n=== Inventory System ===");
            System.out.println("1. Add Product");
            System.out.println("2. List Products");
            System.out.println("3. Sell Product");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int ch = Integer.parseInt(sc.nextLine());

            if (ch == 1) {
                System.out.print("Name: ");
                String name = sc.nextLine();

                System.out.print("Quantity: ");
                int qty = Integer.parseInt(sc.nextLine());

                System.out.print("Price: ");
                double price = Double.parseDouble(sc.nextLine());

                Inventory.addProduct(name, qty, price);
            }
            else if (ch == 2) {
                List<Product> list = Inventory.listProducts();
                for (Product p : list) {
                    System.out.println(p);
                }
            }
            else if (ch == 3) {
                System.out.print("Product ID: ");
                int id = Integer.parseInt(sc.nextLine());

                System.out.print("Quantity: ");
                int qty = Integer.parseInt(sc.nextLine());

                System.out.print("Customer Email: ");
                String email = sc.nextLine();

                Inventory.sellProduct(id, qty, email);
            }
            else if (ch == 4) {
                System.out.println("Exit...");
                break;
            }
            else {
                System.out.println("Invalid choice");
            }
        }
    }
}
