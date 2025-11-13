package com.example.sms;

import java.io.*;
import java.util.*;

public class StudentManager {
    private List<Student> students = new ArrayList<>();
    private final File storageFile;

    public StudentManager(String storagePath) {
        this.storageFile = new File(storagePath);
        load();
    }

    // CRUD operations
    public void addStudent(Student s) {
        if (getById(s.getId()) != null) {
            throw new IllegalArgumentException("Student with ID already exists: " + s.getId());
        }
        students.add(s);
    }

    public Student getById(String id) {
        return students.stream().filter(s -> s.getId().equalsIgnoreCase(id)).findFirst().orElse(null);
    }

    public List<Student> searchByName(String namePart) {
        String lower = namePart.toLowerCase();
        List<Student> result = new ArrayList<>();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(lower)) result.add(s);
        }
        return result;
    }

    public boolean updateStudent(String id, Student updated) {
        Student existing = getById(id);
        if (existing == null) return false;
        existing.setName(updated.getName());
        existing.setAge(updated.getAge());
        existing.setEmail(updated.getEmail());
        existing.setDepartment(updated.getDepartment());
        return true;
    }

    public boolean deleteStudent(String id) {
        Student s = getById(id);
        if (s == null) return false;
        return students.remove(s);
    }

    public List<Student> getAll() {
        return Collections.unmodifiableList(students);
    }

    // Persistence: CSV
    public void load() {
        students.clear();
        if (!storageFile.exists()) {
            // ensure parent dir exists
            try { storageFile.getParentFile().mkdirs(); } catch (Exception ignored) {}
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(storageFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                try {
                    Student s = Student.fromCSV(line);
                    students.add(s);
                } catch (Exception e) {
                    System.err.println("Skipping invalid line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to load students: " + e.getMessage());
        }
    }

    public void save() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(storageFile))) {
            for (Student s : students) {
                pw.println(s.toCSV());
            }
        } catch (IOException e) {
            System.err.println("Failed to save students: " + e.getMessage());
        }
    }
}
