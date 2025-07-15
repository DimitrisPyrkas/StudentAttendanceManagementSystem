package com.example.storage;

import com.example.model.Student;
import com.example.model.Course;
import com.example.model.AttendanceRecord;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;


public class DataStore {


    // === In-Memory Storage ===
    private static final List<Student> students = new ArrayList<>();
    private static final List<Course> courses = new ArrayList<>();
    private static final List<AttendanceRecord> attendanceRecords = new ArrayList<>();


    // === ID Generators ====
    private static int studentIdCounter = 1;
    private static int courseIdCounter = 1;
    private static int attendanceIdCounter = 1;



    // === Student Methods ====

    //Add a new student.
    public static Student addStudent(String fullName, String email) {
        int id = studentIdCounter++;
        Student student = new Student(id, fullName, email);
        students.add(student);
        return student;
    }

    //Get all students.
    public static List<Student> getAllStudents() {
        return students;
    }

    //Find student by ID.
    public static Optional<Student> getStudentById(int id) {
        return students.stream()
                .filter(s -> s.getId() == id)
                .findFirst();
    }


     //Remove a student by ID.
    public static boolean removeStudent(int id) {
        return students.removeIf(s -> s.getId() == id);
    }


    //Register a student for a course.
    public static boolean registerCourse(int studentId, int courseId) {
        Optional<Student> studentOpt = getStudentById(studentId);
        Optional<Course> courseOpt = getCourseById(courseId);

        if (studentOpt.isPresent() && courseOpt.isPresent()) {
            Student student = studentOpt.get();
            List<Integer> registeredCourses = student.getRegisteredCoursesIds();
            if (!registeredCourses.contains(courseId)) {
                registeredCourses.add(courseId);
                return true;
            }
        }
        return false;
    }


     //Unregister a student from a course.
    public static boolean unregisterCourse(int studentId, int courseId) {
        Optional<Student> studentOpt = getStudentById(studentId);

        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            return student.getRegisteredCoursesIds().removeIf(id -> id.equals(courseId));

        }
        return false;
    }


    // ==== Course Methods =====

    //Add a new course.
    public static Course addCourse(String name, String instructor) {
        int id = courseIdCounter++;
        Course course = new Course(id, name, instructor);
        courses.add(course);
        return course;
    }


     //Get all courses.
    public static List<Course> getAllCourses() {
        return courses;
    }


    //Find course by ID.
    public static Optional<Course> getCourseById(int id) {
        return courses.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    //Remove a course by ID.
    public static boolean removeCourse(int id) {
        return courses.removeIf(c -> c.getId() == id);
    }


    // === AttendanceRecord Methods ===


    //Add a new attendance record.
    public static AttendanceRecord addAttendance(int studentId, int courseId, boolean present) {
        int id = attendanceIdCounter++;
        AttendanceRecord record = new AttendanceRecord(id, studentId, courseId, LocalDate.now(), present);
        attendanceRecords.add(record);
        return record;
    }

    //Get all attendance records for a student and course.
    public static List<AttendanceRecord> getAttendanceByStudentAndCourse(int studentId, int courseId) {
        List<AttendanceRecord> result = new ArrayList<>();
        for (AttendanceRecord ar : attendanceRecords) {
            if (ar.getStudentId() == studentId && ar.getCourseId() == courseId) {
                result.add(ar);
            }
        }
        return result;
    }

    //Find attendance record by ID.
    public static Optional<AttendanceRecord> getAttendanceById(int id) {
        return attendanceRecords.stream()
                .filter(a -> a.getId() == id)
                .findFirst();
    }

    //Remove an attendance record by ID.
    public static boolean removeAttendance(int id) {
        return attendanceRecords.removeIf(a -> a.getId() == id);
    }

}
