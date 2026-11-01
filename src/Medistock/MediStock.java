   package Medistock;

   import java.util.ArrayList;
   import java.util.Scanner;

public class MediStock {

    static Scanner sc = new Scanner(System.in);

    // Medicine data (same position in every list = same medicine)
    static ArrayList<Integer> ids = new ArrayList<>();
    static ArrayList<String> names = new ArrayList<>();
    static ArrayList<Double> prices = new ArrayList<>();
    static ArrayList<Integer> quantities = new ArrayList<>();
    static ArrayList<Integer> monthsLeft = new ArrayList<>();
    static ArrayList<String> prescriptions = new ArrayList<>(); // "" if not required

    static double dailySales = 0;
    static final double GST = 12.0;

    // ---------- Safe input (does not crash on letters) ----------
    static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    static double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    static String readText(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    // ---------- Helper methods ----------

    // Returns position of medicine in the lists, or -1 if not found
    static int findIndex(int id) {
        for (int i = 0; i < ids.size(); i++) {
            if (ids.get(i) == id) {
                return i;
            }
        }
        return -1;
    }

    // Method overloading: bill without discount
    static double calculateBill(double price, int qty) {
        return price * qty;
    }

    // Method overloading: bill with discount percent
    static double calculateBill(double price, int qty, double discount) {
        double amount = price * qty;
        return amount - (amount * discount / 100);
    }

    static void displayMedicine(int i) {
        System.out.println("--------------------------------");
        System.out.println("Medicine ID   : " + ids.get(i));
        System.out.println("Medicine Name : " + names.get(i));
        System.out.println("Price         : Rs." + prices.get(i));
        System.out.println("Quantity      : " + quantities.get(i));
        System.out.println("Months Left   : " + monthsLeft.get(i));
        System.out.println("--------------------------------");
        if (!prescriptions.get(i).isEmpty()) {
            System.out.println("Prescription  : " + prescriptions.get(i) + " (Rx required)");
            System.out.println("--------------------------------");
        }
    }

    // ---------- Operations ----------

    static void addMedicine() {
        int id = readInt("Enter Medicine ID: ");

        if (findIndex(id) != -1) {
            System.out.println("Medicine ID already exists.");
            return;
        }

        String name = readText("Enter Medicine Name: ");
        double price = readDouble("Enter Price: ");
        int quantity = readInt("Enter Quantity: ");
        int months = readInt("Enter Months Left for Expiry: ");

        if (name.isEmpty() || price <= 0 || quantity < 0 || months < 0) {
            System.out.println("Invalid input values.");
            return;
        }

        String rx = readText("Prescription number (press Enter if not required): ");

        ids.add(id);
        names.add(name);
        prices.add(price);
        quantities.add(quantity);
        monthsLeft.add(months);
        prescriptions.add(rx);

        System.out.println("Medicine added successfully.");
    }

    static void displayMedicines() {
        if (ids.isEmpty()) {
            System.out.println("No medicines available.");
            return;
        }
        for (int i = 0; i < ids.size(); i++) {
            displayMedicine(i);
        }
    }

    static void searchByName() {
        String name = readText("Enter Medicine Name: ");
        boolean found = false;

        for (int i = 0; i < ids.size(); i++) {
            if (names.get(i).equalsIgnoreCase(name)) {
                displayMedicine(i);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Medicine not found.");
        }
    }

    static void searchById() {
        int id = readInt("Enter Medicine ID: ");
        int i = findIndex(id);

        if (i != -1) {
            displayMedicine(i);
        } else {
            System.out.println("Medicine not found.");
        }
    }

    static void deleteMedicine() {
        int id = readInt("Enter Medicine ID to delete: ");
        int i = findIndex(id);

        if (i == -1) {
            System.out.println("Medicine not found.");
            return;
        }

        ids.remove(i);
        names.remove(i);
        prices.remove(i);
        quantities.remove(i);
        monthsLeft.remove(i);
        prescriptions.remove(i);
        System.out.println("Medicine deleted successfully.");
    }

    static void restockMedicine() {
        int id = readInt("Enter Medicine ID: ");
        int i = findIndex(id);

        if (i == -1) {
            System.out.println("Medicine not found.");
            return;
        }

        int qty = readInt("Enter quantity to add: ");

        if (qty <= 0) {
            System.out.println("Enter a valid quantity.");
            return;
        }

        quantities.set(i, quantities.get(i) + qty);
        System.out.println("Stock updated successfully.");
    }

    static void sellMedicine() {
        int id = readInt("Enter Medicine ID: ");
        int i = findIndex(id);

        if (i == -1) {
            System.out.println("Medicine not found.");
            return;
        }

        if (monthsLeft.get(i) <= 0) {
            System.out.println("Medicine is expired. Sale not allowed.");
            return;
        }

        int qty = readInt("Enter quantity to sell: ");

        if (qty <= 0) {
            System.out.println("Invalid quantity.");
            return;
        }

        if (qty > quantities.get(i)) {
            System.out.println("Insufficient stock.");
            return;
        }

        double discount = readDouble("Enter discount % (0 for none): ");

        if (discount < 0 || discount > 100) {
            System.out.println("Invalid discount.");
            return;
        }

        double price = prices.get(i);
        double amount = calculateBill(price, qty, discount);
        double gstAmount = amount * GST / 100;
        double total = amount + gstAmount;

        quantities.set(i, quantities.get(i) - qty);
        dailySales += total;

        System.out.println("\n========== CUSTOMER BILL ==========");
        System.out.println("Medicine Name : " + names.get(i));
        if (!prescriptions.get(i).isEmpty()) {
            System.out.println("Prescription  : " + prescriptions.get(i));
        }
        System.out.println("Quantity      : " + qty);
        System.out.println("Price         : Rs." + price);
        System.out.println("Discount      : " + discount + "%");
        System.out.println("Amount        : Rs." + amount);
        System.out.println("GST (12%)     : Rs." + gstAmount);
        System.out.println("-----------------------------------");
        System.out.println("Total Bill    : Rs." + total);
        System.out.println("===================================");
    }

    static void lowStock() {
        boolean found = false;
        System.out.println("\nLow Stock Medicines:");

        for (int i = 0; i < ids.size(); i++) {
            if (quantities.get(i) <= 5) {
                displayMedicine(i);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No low stock medicines.");
        }
    }

    static void nearExpiry() {
        boolean found = false;
        System.out.println("\nNear Expiry Medicines:");

        for (int i = 0; i < ids.size(); i++) {
            if (monthsLeft.get(i) <= 3) {
                displayMedicine(i);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No near expiry medicines.");
        }
    }

    static void totalStockValue() {
        double total = 0;
        for (int i = 0; i < ids.size(); i++) {
            total += prices.get(i) * quantities.get(i);
        }
        System.out.println("Total Stock Value: Rs." + total);
    }

    static void displaySales() {
        System.out.println("Total Sales: Rs." + dailySales);
    }

    // ---------- Main ----------
    public static void main(String[] args) {
        int choice;

        do {
            System.out.println("\n===== PHARMACY STOCK AND BILLING SYSTEM =====");
            System.out.println("1. Add Medicine");
            System.out.println("2. Display Medicines");
            System.out.println("3. Search Medicine by Name");
            System.out.println("4. Search Medicine by ID");
            System.out.println("5. Delete Medicine");
            System.out.println("6. Restock Medicine");
            System.out.println("7. Sell Medicine / Generate Bill");
            System.out.println("8. Low Stock Medicines");
            System.out.println("9. Near Expiry Medicines");
            System.out.println("10. Total Stock Value");
            System.out.println("11. Daily Sales");
            System.out.println("12. Exit");
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    addMedicine();
                    break;
                case 2:
                    displayMedicines();
                    break;
                case 3:
                    searchByName();
                    break;
                case 4:
                    searchById();
                    break;
                case 5:
                    deleteMedicine();
                    break;
                case 6:
                    restockMedicine();
                    break;
                case 7:
                    sellMedicine();
                    break;
                case 8:
                    lowStock();
                    break;
                case 9:
                    nearExpiry();
                    break;
                case 10:
                    totalStockValue();
                    break;
                case 11:
                    displaySales();
                    break;
                case 12:
                    System.out.println("Thank you for using MediStock.");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 12);

        sc.close();
    }
}