// === Add Student ===
document.getElementById("addStudentForm").addEventListener("submit", function (e) {
  e.preventDefault();
  const fullName = document.getElementById("fullName").value;
  const email = document.getElementById("email").value;

  const formData = new URLSearchParams();
  formData.append("fullName", fullName);
  formData.append("email", email);

  fetch("./students", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: formData
  })
    .then(res => res.text())
    .then(data => alert(data))
    .catch(err => alert("Error: " + err));
});

// === Load Students ===
document.getElementById("loadStudents").addEventListener("click", function () {
  fetch("./students")
    .then(res => res.text())
    .then(data => {
      document.getElementById("studentsOutput").innerText = data;
    })
    .catch(err => alert("Error: " + err));
});

// === Add Course ===
document.getElementById("addCourseForm").addEventListener("submit", function (e) {
  e.preventDefault();
  const name = document.getElementById("courseName").value;
  const instructor = document.getElementById("instructor").value;

  const formData = new URLSearchParams();
  formData.append("name", name);
  formData.append("instructor", instructor);

  fetch("./courses", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: formData
  })
    .then(res => res.text())
    .then(data => alert(data))
    .catch(err => alert("Error: " + err));
});

// === Load Courses ===
document.getElementById("loadCourses").addEventListener("click", function () {
  fetch("./courses")
    .then(res => res.text())
    .then(data => {
      document.getElementById("coursesOutput").innerText = data;
    })
    .catch(err => alert("Error: " + err));
});

// === Add Attendance ===
document.getElementById("addAttendanceForm").addEventListener("submit", function (e) {
  e.preventDefault();
  const studentId = document.getElementById("attendanceStudentId").value;
  const courseId = document.getElementById("attendanceCourseId").value;
  const present = document.getElementById("present").value;

  const formData = new URLSearchParams();
  formData.append("studentId", studentId);
  formData.append("courseId", courseId);
  formData.append("present", present);

  fetch("./attendance", {
    method: "POST",
    headers: { "Content-Type": "application/x-www-form-urlencoded" },
    body: formData
  })
    .then(res => res.text())
    .then(data => alert(data))
    .catch(err => alert("Error: " + err));
});

// === Load Attendance ===
document.getElementById("loadAttendanceForm").addEventListener("submit", function (e) {
  e.preventDefault();
  const studentId = document.getElementById("viewStudentId").value;
  const courseId = document.getElementById("viewCourseId").value;

  fetch(`./attendance?studentId=${studentId}&courseId=${courseId}`)
    .then(res => res.text())
    .then(data => {
      document.getElementById("attendanceOutput").innerText = data;
    })
    .catch(err => alert("Error: " + err));
});
