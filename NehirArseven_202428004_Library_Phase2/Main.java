import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- KÜTÜPHANE YÖNETİM SİSTEMİ (PHASE 2) ---");
            System.out.println("1. Add Book");
            System.out.println("2. Add Magazine");
            System.out.println("3. Add DVD");
            System.out.println("4. List All Materials");
            System.out.println("5. List by Type");
            System.out.println("6. Borrow Material");
            System.out.println("7. Return Material");
            System.out.println("8. Reserve Material");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine().trim();
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine().trim();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine().trim();
                    System.out.print("Enter publication Year: ");

                    int year = readInt(scanner);

                    Book book = new Book(isbn, title, author, year);
                    library.addMaterial(book);
                    break;

                case "2":
                    System.out.print("Enter Magazine ID/ISSN: ");
                    String mid = scanner.nextLine().trim();
                    System.out.print("Enter title: ");
                    String mtitle = scanner.nextLine().trim();
                    System.out.print("Enter publication Year: ");

                    int myear = readInt(scanner);

                    Magazine mag = new Magazine(mid, mtitle, myear);
                    library.addMaterial(mag);
                    break;

                case "3":
                    System.out.print("Enter DVD ID: ");
                    String did = scanner.nextLine().trim();
                    System.out.print("Enter title: ");
                    String dtitle = scanner.nextLine().trim();
                    System.out.print("Enter director: ");
                    String director = scanner.nextLine().trim();
                    System.out.print("Enter publication Year: ");

                    int dyear = readInt(scanner);

                    DVD dvd = new DVD(did, dtitle, director, dyear);
                    library.addMaterial(dvd);
                    break;

                case "4":
                    library.listAllMaterials();
                    break;

                case "5":
                    System.out.print("Enter type to list (Book, Magazine, DVD): ");
                    String type = scanner.nextLine().trim();
                    library.listByType(type);
                    break;

                case "6":
                    System.out.print("Enter material ID to borrow: ");
                    String bid = scanner.nextLine().trim();
                    System.out.print("Your name (for reservation check): ");
                    String borrower = scanner.nextLine().trim();
                    library.borrowMaterial(bid, borrower);
                    break;

                case "7":
                    System.out.print("Enter material ID to return: ");
                    String rid = scanner.nextLine().trim();
                    library.returnMaterial(rid);
                    break;

                case "8":
                    System.out.print("Enter material ID to reserve: ");
                    String resid = scanner.nextLine().trim();
                    System.out.print("Your name for reservation: ");
                    String holder = scanner.nextLine().trim();
                    library.reserveMaterial(resid, holder);
                    break;

                case "0":
                    System.out.println("Exiting from the program. Goodbye! :) ");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number. Please enter an integer: ");
            }
        }
    }
}