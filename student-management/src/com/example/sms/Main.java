package com.example.sms;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final String STORAGE_PATH = "data/students.csv";

    public static void main(String[] args) {
        StudentManager manager = new StudentManager(STORAGE_PATH);
        Scanner sc = new Scanner(System.in);

        boolean running = true;
        while (running) {
            printMenu();
            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1": addStudent(manager, sc); break;
                case "2": updateStudent(manager, sc); break;
                case "3": deleteStudent(manager, sc); break;
                case "4": viewAll(manager); break;
                case "5": search(manager, sc); break;
                case "6": manager.save(); System.out.println("Saved. Exiting..."); running = false; break;
                default: System.out.println("Invalid option, try again."); break;
            }
        }
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n--- Student Management ---");
        System.out.println("1. Add student");
        System.out.println("2. Update student");
        System.out.println("3. Delete student");
        System.out.println("4. View all students");
        System.out.println("5. Search by ID or Name");
        System.out.println("6. Save & Exit");
        System.out.print("Choose: ");
    }

    private static void addStudent(StudentManager mgr, Scanner sc) {
        System.out.println("Enter details to add student:");
        System.out.print("ID: "); String id = sc.nextLine().trim();
        System.out.print("Name: "); String name = sc.nextLine().trim();
        System.out.print("Age: "); int age = parseInt(sc.nextLine().trim());
        System.out.print("Email: "); String email = sc.nextLine().trim();
        System.out.print("Department: "); String dept = sc.nextLine().trim();

        try {
            Student s = new Student(id, name, age, email, dept);
            mgr.addStudent(s);
            System.out.println("Student added.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding: " + e.getMessage());
        }
    }

    private static void updateStudent(StudentManager mgr, Scanner sc) {
        System.out.print("Enter student ID to update: ");
        String id = sc.nextLine().trim();
        Student existing = mgr.getById(id);
        if (existing == null) {
            System.out.println("No student with ID: " + id);
            return;
        }
        System.out.println("Leave blank to keep current value.");
        System.out.println("Current: " + existing);
        System.out.print("New Name: "); String name = sc.nextLine().trim();
        System.out.print("New Age: "); String ageStr = sc.nextLine().trim();
        System.out.print("New Email: "); String email = sc.nextLine().trim();
        System.out.print("New Department: "); String dept = sc.nextLine().trim();

        String newName = name.isEmpty() ? existing.getName() : name;
        int newAge = ageStr.isEmpty() ? existing.getAge() : parseInt(ageStr);
        String newEmail = email.isEmpty() ? existing.getEmail() : email;
        String newDept = dept.isEmpty() ? existing.getDepartment() : dept;

        Student updated = new Student(id, newName, newAge, newEmail, newDept);
        boolean ok = mgr.updateStudent(id, updated);
        System.out.println(ok ? "Updated." : "Update failed.");
    }

    private static void deleteStudent(StudentManager mgr, Scanner sc) {
        System.out.print("Enter ID to delete: ");
        String id = sc.nextLine().trim();
        boolean ok = mgr.deleteStudent(id);
        System.out.println(ok ? "Deleted." : "No student found with that ID.");
    }

    private static void viewAll(StudentManager mgr) {
        List<Student> list = mgr.getAll();
        if (list.isEmpty()) {
            System.out.println("No students available.");
            return;
        }
        System.out.println("ID | Name | Age | Email | Department");
        System.out.println("------------------------------------");
        for (Student s : list) {
            System.out.println(s);
        }
    }

    private static void search(StudentManager mgr, Scanner sc) {
        System.out.print("Search by ID or name: ");
        String q = sc.nextLine().trim();
        Student byId = mgr.getById(q);
        if (byId != null) {
            System.out.println("Found by ID:");
            System.out.println(byId);
            return;
        }
        List<Student> results = mgr.searchByName(q);
        if (results.isEmpty()) {
            System.out.println("No matches.");
        } else {
            System.out.println("Matches:");
            for (Student s : results) System.out.println(s);
        }
    }

    private static int parseInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }
}
