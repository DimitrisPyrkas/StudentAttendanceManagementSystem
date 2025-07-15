package com.example.servlet;

import com.example.model.AttendanceRecord;
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
 * AttendanceServlet handles CRUD operations for AttendanceRecord entity.
 *
 * URL mappings:
 * GET    /attendance?student{id}&course{id}   -> list attendance records for student & course
 * POST   /attendance                          -> create new attendance (studentId, courseId, present)
 * PUT    /attendance                          -> update attendance (id, present)
 * DELETE /attendance/{id}                     -> delete attendance by ID
 */


public class AttendanceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String studentIdParam = request.getParameter("studentId");
        String courseIdParam = request.getParameter("courseId");

        if (studentIdParam == null || courseIdParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Missing parameters: studentId and courseId are required.");
            return;
        }

        int studentId = Integer.parseInt(studentIdParam);
        int courseId = Integer.parseInt(courseIdParam);

        //List  attendance records for student & course
        List<AttendanceRecord> records = DataStore.getAttendanceByStudentAndCourse(studentId, courseId);

        if (records.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.println("No attendance records found for Student ID: " + studentId + ", Course ID: " + courseId);
        } else {
            for (AttendanceRecord ar : records) {
                out.println("ID: " + ar.getId());
                out.println("Student ID: " + ar.getStudentId());
                out.println("Course ID: " + ar.getCourseId());
                out.println("Date: " + ar.getDate());
                out.println("Present: " + ar.isPresent());
                out.println("-----");
            }
        }
    }

    //Create new attendance
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String studentIdParam = request.getParameter("studentId");
        String courseIdParam = request.getParameter("courseId");
        String presentParam = request.getParameter("present");

        if (studentIdParam == null || courseIdParam == null || presentParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Missing parameters: studentId, courseId, and present are required.");
            return;
        }

        int studentId = Integer.parseInt(studentIdParam);
        int courseId = Integer.parseInt(courseIdParam);
        boolean present = Boolean.parseBoolean(presentParam);

        AttendanceRecord record = DataStore.addAttendance(studentId, courseId, present);
        DataStore.registerCourse(studentId, courseId);
        out.println("Attendance recorded with ID: " + record.getId());
    }

    //Update attendance
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();

        String idParam = request.getParameter("id");
        String presentParam = request.getParameter("present");

        if (idParam == null || presentParam == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Missing parameters: id and present are required.");
            return;
        }

        int id = Integer.parseInt(idParam);
        boolean present = Boolean.parseBoolean(presentParam);

        Optional<AttendanceRecord> recordOpt = DataStore.getAttendanceById(id);

        if (recordOpt.isPresent()) {
            AttendanceRecord record = recordOpt.get();
            record.setPresent(present);
            out.println("Attendance updated: ID " + record.getId());
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.println("Attendance record not found with ID: " + id);
        }
    }

    //Delete attendance
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

        Optional<AttendanceRecord> recordOpt = DataStore.getAttendanceById(id);

        if (recordOpt.isPresent()) {
            AttendanceRecord record = recordOpt.get();
            boolean removed = DataStore.removeAttendance(id);


            DataStore.unregisterCourse(record.getStudentId(), record.getCourseId());

            if (removed) {
                out.println("Attendance record deleted with ID: " + id);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.println("Error deleting attendance record with ID: " + id);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.println("Attendance record not found with ID: " + id);
        }
    }
}
