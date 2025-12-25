import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReportWriter {

    public static void writeReport(
            Map<String, Student> studentsById,
            Map<String, Course> coursesByCode,
            Map<String, Instructor> instructorsById
    ) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String fileName = "ogrenci_kayit_raporu_" + timestamp + ".txt";

        try (BufferedWriter w = new BufferedWriter(new FileWriter(fileName))) {

            writeHeader(w);

            writeInstructorsSection(w, instructorsById, coursesByCode);

            w.newLine();
            writeCoursesSection(w, coursesByCode, studentsById);

            w.newLine();
            writeStudentsSection(w, studentsById);

            w.newLine();
            w.write("Rapor sonu.");
            w.newLine();

        } catch (IOException e) {
            throw new RuntimeException("Rapor yazdirilamadi: " + e.getMessage(), e);
        }
    }

    private static void writeHeader(BufferedWriter w) throws IOException {
        w.write("========================================");
        w.newLine();
        w.write("      OGRENCI DERS KAYIT SISTEMI RAPORU");
        w.newLine();
        w.write("========================================");
        w.newLine();

        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        w.write("Olusturma Zamani: " + now);
        w.newLine();
        w.newLine();
    }

    private static void writeInstructorsSection(
            BufferedWriter w,
            Map<String, Instructor> instructorsById,
            Map<String, Course> coursesByCode
    ) throws IOException {

        w.write("=== OGRETMENLER ===");
        w.newLine();

        if (instructorsById.isEmpty()) {
            w.write("(Hic ogretmen yok)");
            w.newLine();
            return;
        }

        List<Instructor> instructors = new ArrayList<>(instructorsById.values());
        instructors.sort(Comparator.comparing(Instructor::getId));

        Map<String, List<Course>> coursesByInstructorId = new HashMap<>();
        for (Course c : coursesByCode.values()) {
            Instructor ins = c.getInstructor();
            if (ins == null) continue;
            coursesByInstructorId.computeIfAbsent(ins.getId(), k -> new ArrayList<>()).add(c);
        }
        for (List<Course> list : coursesByInstructorId.values()) {
            list.sort(Comparator.comparing(Course::getCode));
        }

        for (Instructor ins : instructors) {
            w.write("- " + ins.getName() + " (" + ins.getId() + ")");
            w.newLine();

            List<Course> list = coursesByInstructorId.getOrDefault(ins.getId(), Collections.emptyList());
            if (list.isEmpty()) {
                w.write("  Verdigi Dersler: (Yok)");
                w.newLine();
            } else {
                w.write("  Verdigi Dersler:");
                w.newLine();
                for (Course c : list) {
                    w.write("    * " + formatCourseLine(c));
                    w.newLine();
                }
            }
            w.newLine();
        }
    }

    private static void writeCoursesSection(
            BufferedWriter w,
            Map<String, Course> coursesByCode,
            Map<String, Student> studentsById
    ) throws IOException {

        w.write("=== DERSLER ===");
        w.newLine();

        if (coursesByCode.isEmpty()) {
            w.write("(Hic ders yok)");
            w.newLine();
            return;
        }

        List<Course> courses = new ArrayList<>(coursesByCode.values());
        courses.sort(Comparator.comparing(Course::getCode));

        Map<String, List<Student>> enrolledByCourseCode = new HashMap<>();
        for (Student s : studentsById.values()) {
            for (Course c : s.getRegisteredCourses()) {
                enrolledByCourseCode.computeIfAbsent(c.getCode(), k -> new ArrayList<>()).add(s);
            }
        }
        for (List<Student> list : enrolledByCourseCode.values()) {
            list.sort(Comparator.comparing(Student::getId));
        }

        for (Course c : courses) {
            w.write("- " + formatCourseLine(c));
            w.newLine();

            List<Student> enrolled = enrolledByCourseCode.getOrDefault(c.getCode(), Collections.emptyList());
            if (enrolled.isEmpty()) {
                w.write("  Kayitli Ogrenciler: (Yok)");
                w.newLine();
            } else {
                w.write("  Kayitli Ogrenciler (Notlar dahil):");
                w.newLine();
                for (Student s : enrolled) {
                    Double gp = s.getGradePointOf(c);
                    String gpStr = (gp == null) ? "Girilmedi" : format2(gp);
                    w.write("    * " + s.getName() + " (" + s.getId() + ") | Not: " + gpStr);
                    w.newLine();
                }
            }
            w.newLine();
        }
    }

    private static void writeStudentsSection(
            BufferedWriter w,
            Map<String, Student> studentsById
    ) throws IOException {

        w.write("=== OGRENCILER ===");
        w.newLine();

        if (studentsById.isEmpty()) {
            w.write("(Hic ogrenci yok)");
            w.newLine();
            return;
        }

        List<Student> students = new ArrayList<>(studentsById.values());
        students.sort(Comparator.comparing(Student::getId));

        for (Student s : students) {
            w.write("- " + s.getName() + " (" + s.getId() + ")");
            w.newLine();
            w.write("  GPA: " + format2(s.calculateGPA()));
            w.newLine();

            List<Course> courses = s.getRegisteredCourses();
            if (courses.isEmpty()) {
                w.write("  Aldigi Dersler: (Yok)");
                w.newLine();
            } else {
                courses.sort(Comparator.comparing(Course::getCode));
                w.write("  Aldigi Dersler (Ogretmen / Saat / Not):");
                w.newLine();
                for (Course c : courses) {
                    Double gp = s.getGradePointOf(c);
                    String gpStr = (gp == null) ? "Girilmedi" : format2(gp);
                    w.write("    * " + formatCourseLine(c) + " | Not: " + gpStr);
                    w.newLine();
                }
            }
            w.newLine();
        }
    }

    private static String formatCourseLine(Course c) {
        String ins = (c.getInstructor() == null) ? "Atanmamis" : c.getInstructor().getName();
        String schedule = (c.getTimeSlot() == null) ? "Saat Bilgisi Yok" : c.getTimeSlot().toString();

        return c.getCode()
                + " | " + c.getName()
                + " | Kredi: " + c.getCredit()
                + " | Ogretmen: " + ins
                + " | Saat: " + schedule;
    }

    private static String format2(double value) {
        return String.format("%.2f", value);
    }
}
