package com.example.servlet;

import com.example.model.Student;
import com.example.storage.DataStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

/**
 * StudentServlet handles CRUD operations for Student entity.
 *
 * URL mappings:
 * GET    /students         -> list all students
 * GET    /students/{id}     -> get student by ID
 * POST   /students         -> create new student (fullName, email)
 * PUT    /students         -> update student (id, fullName, email)
 * DELETE /students/{id}    -> delete student by ID
 */


public class StudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String idParam = request.getParameter("id");

        if (idParam != null) {
            // Get student by ID
            int id = Integer.parseInt(idParam);
            Optional<Student> studentOpt = DataStore.getStudentById(id);

            if (studentOpt.isPresent()) {
                Student s = studentOpt.get();
                out.println("ID: " + s.getId());
                out.println("Name: " + s.getFullName());
                out.println("Email: " + s.getEmail());
                out.println("Registered Courses: " + s.getRegisteredCoursesIds());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("Student not found with ID: " + id);
            }

        } else {
            // List all students
            List<Student> students = DataStore.getAllStudents();
            for (Student s : students) {
                out.println("ID: " + s.getId());
                out.println("Name: " + s.getFullName());
                out.println("Email: " + s.getEmail());
                out.println("Registered Courses: " + s.getRegisteredCoursesIds());
                out.println("-----");
            }
        }
    }

    //Create new student
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");

        if (fullName == null || email == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Missing parameters: fullName and email are required.");
            return;
        }

        Student student = DataStore.addStudent(fullName, email);
        out.println("Student created with ID: " + student.getId());
    }

    //Update student
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();


        String idParam = request.getParameter("id");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");

        if (idParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Missing parameter: id is required.");
            return;
        }

        int id = Integer.parseInt(idParam);
        Optional<Student> studentOpt = DataStore.getStudentById(id);

        if (studentOpt.isPresent()) {
            Student s = studentOpt.get();
            if (fullName != null) s.setFullName(fullName);
            if (email != null) s.setEmail(email);
            out.println("Student updated: ID " + s.getId());
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.println("Student not found with ID: " + id);
        }
    }

    //Delete student by ID
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String idParam = request.getParameter("id");
        if (idParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Missing parameter: id is required.");
            return;
        }

        int id = Integer.parseInt(idParam);
        boolean removed = DataStore.removeStudent(id);

        if (removed) {
            out.println("Student deleted with ID: " + id);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.println("Student not found with ID: " + id);
        }
    }
}
