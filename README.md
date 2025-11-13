# Student Management System (Java – Console)

A simple and beginner-friendly **Java console application** demonstrating:
- OOP concepts  
- CRUD operations with ArrayList  
- CSV file handling  

## Features
- Add, update, delete, and view students  
- Search by ID or name  
- Persistent CSV storage  
- Console menu interface  

## Tech Stack
- Java 8+  
- No external libraries  

## Project Structure
```
student-management/
├── src/
│   └── com/example/sms/
│       ├── Main.java
│       ├── Student.java
│       └── StudentManager.java
├── data/
│   └── students.csv
└── README.md
```

## How to Run
### Compile
```
javac -d out src/com/example/sms/*.java
```

### Run
```
java -cp out com.example.sms.Main
```

## Future Enhancements
- Swing/JavaFX GUI  
- SQLite/MySQL database integration  
- Sorting & filtering options  
- REST API using Spring Boot  

