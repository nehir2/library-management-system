import java.io.*;
import java.util.ArrayList;

public class Library {
    private ArrayList<Material> materials;
    private static final String FILE_NAME = "library.txt";

    public Library() {
        this.materials = new ArrayList<>();
        loadMaterialsFromFile();
    }

    public void addMaterial(Material material) {
        this.materials.add(material);
        saveToFile();
        System.out.println("Material added successfully.");
    }

    public void listAllMaterials() {
        if (materials.isEmpty()) {
            System.out.println("No materials in the library.");
            return;
        }
        System.out.println("All Materials in the library:");
        int counter = 1;
        for (Material material : materials) {
            System.out.println(counter + ". " + material.toString());
            counter++;
        }
    }

    public void listByType(String type) {
        System.out.println("Listing all " + type + "s:");
        boolean found = false;
        for (Material material : materials) {
            if (type.equalsIgnoreCase("book") && material instanceof Book) {
                System.out.println(material.toString());
                found = true;
            } else if (type.equalsIgnoreCase("magazine") && material instanceof Magazine) {
                System.out.println(material.toString());
                found = true;
            } else if (type.equalsIgnoreCase("dvd") && material instanceof DVD) {
                System.out.println(material.toString());
                found = true;
            }
        }
        if (!found) System.out.println("No materials found of type: " + type);
    }

    public Material findMaterial(String materialID) {
        for (Material material : materials) {
            if (material.getId().equalsIgnoreCase(materialID)) {
                return material;
            }
        }
        return null;
    }

    public void borrowMaterial(String materialID, String borrowerName) {
        Material material = findMaterial(materialID);
        if (material != null) {
            if (material instanceof Borrowable) {
                Borrowable item = (Borrowable) material;

                if (material instanceof Reservable) {
                    Reservable resItem = (Reservable) material;
                    if (resItem.isReserved() && !resItem.getReservationHolder().equalsIgnoreCase(borrowerName)) {
                        System.out.println("Error: Item is reserved by " + resItem.getReservationHolder());
                        return;
                    }
                }

                if (item.isAvailableToBorrow()) {
                    item.borrow();
                    saveToFile();
                    System.out.println("Success: You have borrowed [" + material.getTitle() + "]");
                } else {
                    System.out.println("Warning: This item is already borrowed.");
                }
            } else {
                System.out.println("Error: This item (" + material.getTitle() + ") cannot be borrowed.");
            }
        } else {
            System.out.println("Error: Material not found with this ID.");
        }
    }

    public void returnMaterial(String materialID) {
        Material material = findMaterial(materialID);
        if (material != null) {
            if (material instanceof Borrowable) {
                Borrowable item = (Borrowable) material;
                if (!item.isAvailableToBorrow()) {
                    item.returnItem();
                    saveToFile();
                    System.out.println("Success: You have returned [" + material.getTitle() + "]");
                } else {
                    System.out.println("Warning: This item is already in the library.");
                }
            } else {
                System.out.println("Error: This item (" + material.getTitle() + ") was never borrowable.");
            }
        } else {
            System.out.println("Error: Material not found with this ID.");
        }
    }

    public void reserveMaterial(String materialID, String holderName) {
        Material material = findMaterial(materialID);
        if (material != null) {
            if (material instanceof Reservable) {
                Reservable item = (Reservable) material;
                if (!item.isReserved()) {
                    item.reserve(holderName);
                    saveToFile();
                    System.out.println("Success: [" + material.getTitle() + "] is reserved for " + holderName);
                } else {
                    System.out.println("Warning: Already reserved by " + item.getReservationHolder());
                }
            } else {
                System.out.println("Error: This item (Magazine or DVD) cannot be reserved.");
            }
        } else {
            System.out.println("Error: Material not found with this ID.");
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Material material : materials) {
                writer.write(material.toFileFormat());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
        }
    }

    private void loadMaterialsFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 1) continue;

                String type = parts[0];

                try {
                    if (type.equals("BOOK")) {
                        Book book = new Book(parts[1], parts[2], parts[3], Integer.parseInt(parts[4]));
                        book.setIsBorrowed(Boolean.parseBoolean(parts[5]));
                        if (Boolean.parseBoolean(parts[6])) {
                            book.reserve(parts[7]);
                        }
                        this.materials.add(book);
                    } else if (type.equals("MAG")) {
                        Magazine mag = new Magazine(parts[1], parts[2], Integer.parseInt(parts[3]));
                        mag.setIsBorrowed(Boolean.parseBoolean(parts[4]));
                        this.materials.add(mag);
                    } else if (type.equals("DVD")) {
                        DVD dvd = new DVD(parts[1], parts[2], parts[3], Integer.parseInt(parts[4]));
                        this.materials.add(dvd);
                    }
                } catch (Exception e) {
                    System.err.println("Skipping malformed line: " + line);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading data from file: " + e.getMessage());
        }
    }
}