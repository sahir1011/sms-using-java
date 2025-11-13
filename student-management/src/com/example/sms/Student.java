package com.example.sms;

public class Student {
    private String id;
    private String name;
    private int age;
    private String email;
    private String department;

    public Student(String id, String name, int age, String email, String department) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.department = department;
    }

    // getters & setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return String.format("%s | %s | %d | %s | %s", id, name, age, email, department);
    }

    // Convert to CSV line
    public String toCSV() {
        // simple CSV escaping for commas (quotes)
        return String.format("\"%s\",\"%s\",%d,\"%s\",\"%s\"",
                id.replace("\"","\"\""),
                name.replace("\"","\"\""),
                age,
                email.replace("\"","\"\""),
                department.replace("\"","\"\""));
    }

    // Parse CSV line (expects double-quoted or plain)
    public static Student fromCSV(String line) {
        // simple CSV parse by splitting on comma outside quotes
        // For simplicity we assume well-formed entries
        String[] parts = splitCsv(line);
        String id = parts.length > 0 ? unquote(parts[0]) : "";
        String name = parts.length > 1 ? unquote(parts[1]) : "";
        int age = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;
        String email = parts.length > 3 ? unquote(parts[3]) : "";
        String dept = parts.length > 4 ? unquote(parts[4]) : "";
        return new Student(id, name, age, email, dept);
    }

    private static String[] splitCsv(String line) {
        // naive parser: handles quoted fields containing commas
        java.util.List<String> out = new java.util.ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"' ) {
                // toggle unless doubled
                if (inQuotes && i+1 < line.length() && line.charAt(i+1) == '"') {
                    sb.append('"'); // escaped quote
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                out.add(sb.toString());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        out.add(sb.toString());
        return out.toArray(new String[0]);
    }

    private static String unquote(String s) {
        if (s == null) return "";
        s = s.trim();
        if (s.length() >= 2 && s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length()-1).replace("\"\"", "\"");
        }
        return s;
    }
}
