package com.example.servlet;

import com.example.model.Course;
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
 * CourseServlet handles CRUD operations for Course entity.
 *
 * URL mappings:
 * GET    /courses         -> list all courses
 * GET    /courses/{id}    -> get course by ID
 * POST   /courses         -> create new course (name, instructor)
 * DELETE /courses/{id}    -> delete course by ID
 */


public class CourseServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String idParam = request.getParameter("id");

        if (idParam != null) {
            // Get course by ID
            int id = Integer.parseInt(idParam);
            Optional<Course> courseOpt = DataStore.getCourseById(id);

            if (courseOpt.isPresent()) {
                Course c = courseOpt.get();
                out.println("ID: " + c.getId());
                out.println("Name: " + c.getName());
                out.println("Instructor: " + c.getInstructor());
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("Course not found with ID: " + id);
            }

        } else {
            // List all courses
            List<Course> courses = DataStore.getAllCourses();
            for (Course c : courses) {
                out.println("ID: " + c.getId());
                out.println("Name: " + c.getName());
                out.println("Instructor: " + c.getInstructor());
                out.println("-----");
            }
        }
    }

    //Create new course
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String instructor = request.getParameter("instructor");

        if (name == null || instructor == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Missing parameters: name and instructor are required.");
            return;
        }

        Course course = DataStore.addCourse(name, instructor);
        out.println("Course created with ID: " + course.getId());
    }

    //Delete course by ID
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
        boolean removed = DataStore.removeCourse(id);

        if (removed) {
            out.println("Course deleted with ID: " + id);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.println("Course not found with ID: " + id);
        }
    }
}
