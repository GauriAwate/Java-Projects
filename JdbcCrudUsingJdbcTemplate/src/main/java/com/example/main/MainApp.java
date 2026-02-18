package com.example.main;

import com.example.config.AppConfig;
import com.example.dao.StudentDAO;
import com.example.model.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class, com.example.dao.StudentDAOImpl.class);
        StudentDAO dao = context.getBean(StudentDAO.class);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n------ STUDENT CRUD MENU ------");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("Enter ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Email: ");
                    String email = sc.nextLine();
                    dao.create(new Student(id, name, email));
                    break;

                case 2:
                    List<Student> list = dao.readAll();
                    if (list.isEmpty()) System.out.println("No records.");
                    else list.forEach(System.out::println);
                    break;

                case 3:
                    System.out.print("Enter ID to update: ");
                    int uid = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter New Name: ");
                    String uname = sc.nextLine();
                    System.out.print("Enter New Email: ");
                    String uemail = sc.nextLine();
                    dao.update(new Student(uid, uname, uemail));
                    break;

                case 4:
                    System.out.print("Enter ID to delete: ");
                    int did = sc.nextInt();
                    dao.delete(did);
                    break;

                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
