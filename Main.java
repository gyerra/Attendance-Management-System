import java.time.LocalDate;
import java.util.*;
import javax.swing.JOptionPane;

// Student class
class Student {
    private int id;
    private String name;
    private String email;

    public Student(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}

// Course class
class Course {
    private int courseId;
    private String courseName;

    public Course(int courseId, String courseName) {
        this.courseId = courseId;
        this.courseName = courseName;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }
}

// Session class
class Session {
    private int sessionId;
    private Course course;
    private LocalDate date;
    private Map<Student, Boolean> attendanceRecord;

    public Session(int sessionId, Course course, LocalDate date) {
        this.sessionId = sessionId;
        this.course = course;
        this.date = date;
        this.attendanceRecord = new HashMap<>();
    }

    public Course getCourse() {
        return course;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<Student, Boolean> getAttendanceRecord() {
        return attendanceRecord;
    }
}

// AttendanceSystem class
class AttendanceSystem {
    private List<Student> students;
    private List<Course> courses;
    private List<Session> sessions;

    public AttendanceSystem() {
        students = new ArrayList<>();
        courses = new ArrayList<>();
        sessions = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void startSession(Course course, LocalDate date) {
        int sessionId = sessions.size() + 1;
        Session session = new Session(sessionId, course, date);
        sessions.add(session);
    }

    public void markAttendance(Session session, Student student, boolean status) {
        if (sessions.contains(session) && students.contains(student)) {
            session.getAttendanceRecord().put(student, status);
        } else {
            throw new IllegalArgumentException("Session or student not found.");
        }
    }
    
    public List<Session> getSessions() {
        return sessions;
    }
    
    public Student findStudentByName(String name) {
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null; // Return null if student not found
    }
    
    public List<Course> getCourses() {
        return courses;
    }
}

public class Main {
    public static void main(String[] args) {
        // Creating an instance of the AttendanceSystem
        AttendanceSystem attendanceSystem = new AttendanceSystem();

        // Adding students
        Student student1 = new Student(40080, "Gayathri Yerra", "gayathri@example.com");
        Student student2 = new Student(40069, "Laasika Anuga", "laasika@example.com");
        Student student3 = new Student(30385, "Gayathri Vemali", "gayathri_v@example.com");
        Student student4 = new Student(30120, "Charan Veeravalli", "charan@example.com");
        
        attendanceSystem.addStudent(student1);
        attendanceSystem.addStudent(student2);
        attendanceSystem.addStudent(student3);
        attendanceSystem.addStudent(student4);

        // Adding courses
        Course course1 = new Course(101, "Java Programming");
        Course course2 = new Course(102, "Data Structures");
        Course course3 = new Course(103, "Digital Design");
        attendanceSystem.addCourse(course1);
        attendanceSystem.addCourse(course2);
        attendanceSystem.addCourse(course3);

        // Starting sessions
        LocalDate sessionDate = LocalDate.now(); // Current date
        attendanceSystem.startSession(course1, sessionDate);
        attendanceSystem.startSession(course2, sessionDate);
        attendanceSystem.startSession(course3, sessionDate);

        // Input student name
        String studentName = JOptionPane.showInputDialog("Enter student name:");

        // Find student
        Student student = attendanceSystem.findStudentByName(studentName);
        if (student != null) {
            StringBuilder attendanceRecord = new StringBuilder();
            attendanceRecord.append("Attendance for ").append(student.getName()).append(":\n");

            // Iterate through courses and mark attendance
            for (Course course : attendanceSystem.getCourses()) {
                Session session = attendanceSystem.getSessions().get(course.getCourseId() - 101);
                attendanceSystem.markAttendance(session, student, Math.random() < 0.8); // Randomly mark attendance
                attendanceRecord.append(session.getCourse().getCourseName()).append(": ");
                if (session.getAttendanceRecord().get(student)) {
                    attendanceRecord.append("Present\n");
                } else {
                    attendanceRecord.append("Absent\n");
                }
            }

            // Display attendance record in a dialog box
            JOptionPane.showMessageDialog(null, attendanceRecord.toString());
        } else {
            JOptionPane.showMessageDialog(null, "Student not found.");
        }
    }
}
