import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Student implements Serializable {
    private String name;
    private int rollNumber;
    private String grade;

    public Student(String name, int rollNumber, String grade) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Roll Number: " + rollNumber + ", Grade: " + grade;
    }
}

class StudentManagementSystem {
    private ArrayList<Student> students;

    public StudentManagementSystem() {
        this.students = new ArrayList<>();
        // Load existing student data from file (if any)
        loadStudentsFromFile();
    }

    public void addStudent(Student student) {
        students.add(student);
        System.out.println("Student added successfully.");
    }

    public void removeStudent(int rollNumber) {
        students.removeIf(student -> student.getRollNumber() == rollNumber);
        System.out.println("Student removed successfully.");
    }

    public Student searchStudent(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("All Students:");
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    public void saveStudentsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("students.dat"))) {
            oos.writeObject(students);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void loadStudentsFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("students.dat"))) {
            students = (ArrayList<Student>) ois.readObject();
        } catch (FileNotFoundException e) {
            // Ignore if the file is not found (first run)
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StudentManagementSystem studentManagementSystem = new StudentManagementSystem();

        while (true) {
            System.out.println("\nStudent Management System Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Display All Students");
            System.out.println("5. Save Students to File");
            System.out.println("6. Exit");

            System.out.print("Enter your choice (1-6): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addStudent(studentManagementSystem, scanner);
                    break;
                case 2:
                    removeStudent(studentManagementSystem, scanner);
                    break;
                case 3:
                    searchStudent(studentManagementSystem, scanner);
                    break;
                case 4:
                    studentManagementSystem.displayAllStudents();
                    break;
                case 5:
                    studentManagementSystem.saveStudentsToFile();
                    break;
                case 6:
                    System.out.println("Exiting Student Management System. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        }
    }

    private static void addStudent(StudentManagementSystem system, Scanner scanner) {
        System.out.print("Enter student name: ");
        scanner.nextLine(); // Consume newline left by previous nextInt()
        String name = scanner.nextLine();
        System.out.print("Enter student roll number: ");
        int rollNumber = scanner.nextInt();
        System.out.print("Enter student grade: ");
        String grade = scanner.next();

        Student newStudent = new Student(name, rollNumber, grade);
        system.addStudent(newStudent);
    }

    private static void removeStudent(StudentManagementSystem system, Scanner scanner) {
        System.out.print("Enter roll number of student to remove: ");
        int rollNumber = scanner.nextInt();
        system.removeStudent(rollNumber);
    }

    private static void searchStudent(StudentManagementSystem system, Scanner scanner) {
        System.out.print("Enter roll number of student to search: ");
        int rollNumber = scanner.nextInt();
        Student foundStudent = system.searchStudent(rollNumber);

        if (foundStudent != null) {
            System.out.println("Student found:\n" + foundStudent);
        } else {
            System.out.println("Student not found.");
        }
    }
}
