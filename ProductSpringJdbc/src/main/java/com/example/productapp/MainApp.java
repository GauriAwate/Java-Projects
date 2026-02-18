package com.example.productapp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ProductService service = context.getBean(ProductService.class);

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\n1. Add Product\n2. View All\n3. View By ID\n4. Update\n5. Delete\n6. Exit");
            System.out.print("Enter choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> {
                    System.out.print("Name: ");
                    String name = scanner.next();
                    System.out.print("Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Quantity: ");
                    int qty = scanner.nextInt();
                    service.addProduct(new Product(name, price, qty));
                    System.out.println("Product added!");
                }
                case 2 -> service.getAllProducts().forEach(System.out::println);

                case 3 -> {
                    System.out.print("Enter ID: ");
                    int id = scanner.nextInt();
                    System.out.println(service.getProduct(id));
                }
                case 4 -> {
                    System.out.print("ID: ");
                    int id = scanner.nextInt();
                    System.out.print("Name: ");
                    String name = scanner.next();
                    System.out.print("Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Quantity: ");
                    int qty = scanner.nextInt();
                    service.updateProduct(new Product(id, name, price, qty));
                    System.out.println("Updated!");
                }
                case 5 -> {
                    System.out.print("Enter ID: ");
                    int id = scanner.nextInt();
                    service.deleteProduct(id);
                    System.out.println("Deleted!");
                }
                case 6 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice");
            }
        } while (choice != 6);

        context.close();
        scanner.close();
    }
}
