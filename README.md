# 📚 Student Attendance Management System

This is the final project for the B' semester in TechPro Academy. 
This is a simple **Java Servlet** web application for managing students, courses, and attendance records  via RESTful endpoints.

The app uses:
- Plain Java servlets (no frameworks)
- In-memory data storage (no database — all data is lost when the server stops)
- A basic HTML/JavaScript front-end for quick testing

✅ **Deployable on Tomcat**

---

## 🚀 Features

- **Add / View / Upadte / Delete Students**
- **Add / View / Delete Courses**
- **Record Attendance** (with student ID, course ID, present/absent)
- **View & Update Attendance by Student & Course**
- Simple HTML page (`index.html`) with JavaScript for easy testing ( http://localhost:8080/StudentAttendanceSystem/index.html )

---

## ⚙️ How to Build

This project does **not** use Maven or Gradle.  
Build is done manually via the following process.

**To compile and build:**

```bash
# 1️⃣ Clean old build
rm -rf build

# 2️⃣ Create build folder for compiled classes
mkdir build/WEB-INF/classes

# 3️⃣ Compile Java source files
javac -d build/WEB-INF/classes -sourcepath src ^
  src/com/example/model/*.java ^
  src/com/example/storage/*.java ^
  src/com/example/servlet/*.java

# 4️⃣ Copy static files (HTML, JS, web.xml)
xcopy WebContent\* build\ /E /Y

# 5️⃣ Create WAR file
jar -cvf StudentAttendanceSystem.war -C build .
