import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    // Catalogs
    private static final CourseCatalog courseCatalog = new CourseCatalog();
    private static final InstructorCatalog instructorCatalog = new InstructorCatalog();

    // In-memory stores
    private static final Map<String, Student> studentsById = new HashMap<>();
    private static final Map<String, Instructor> instructorsById = new HashMap<>();
    private static final Map<String, Course> coursesByCode = new HashMap<>();

    public static void main(String[] args) {
        while (true) {
            printMainMenu();
            String choice = readLine();

            try {
                switch (choice) {
                    case "1" -> studentMenu();
                    case "2" -> instructorMenu();
                    case "3" -> courseMenu();
                    case "0" -> {
                        ReportWriter.writeReport(studentsById, coursesByCode, instructorsById);
                        System.out.println("Rapor olusturuldu.");
                        System.out.println("Cikis yapiliyor...");
                        return;
                    }
                    default -> System.out.println("Gecersiz secim.");
                }
            } catch (Exception e) {
                System.out.println("Hata: " + e.getMessage());
            }

            System.out.println();
        }
    }

    // ===== MENUS =====

    private static void printMainMenu() {
        System.out.println("===== ANA MENU =====");
        System.out.println("1) Ogrenci Islemleri");
        System.out.println("2) Hoca Islemleri");
        System.out.println("3) Ders Islemleri");
        System.out.println("0) Cikis");
        System.out.print("Secim: ");
    }

    private static void studentMenu() {
        while (true) {
            printStudentMenu();
            String choice = readLine();

            try {
                switch (choice) {
                    case "1" -> addStudent();
                    case "2" -> listStudents();
                    case "3" -> registerCourseToStudent();
                    case "4" -> dropCourseFromStudent();
                    case "5" -> showStudentCourses();
                    case "6" -> showTuition();
                    case "7" -> setGradePointForStudent();
                    case "8" -> showGpa();
                    case "0" -> { return; }
                    default -> System.out.println("Gecersiz secim.");
                }
            } catch (Exception e) {
                System.out.println("Hata: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private static void instructorMenu() {
        while (true) {
            printInstructorMenu();
            String choice = readLine();

            try {
                switch (choice) {
                    case "1" -> addInstructor();
                    case "2" -> listInstructors();
                    case "0" -> { return; }
                    default -> System.out.println("Gecersiz secim.");
                }
            } catch (Exception e) {
                System.out.println("Hata: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private static void courseMenu() {
        while (true) {
            printCourseMenu();
            String choice = readLine();

            try {
                switch (choice) {
                    case "1" -> addCourse();
                    case "2" -> listCoursesInCatalog();
                    case "0" -> { return; }
                    default -> System.out.println("Gecersiz secim.");
                }
            } catch (Exception e) {
                System.out.println("Hata: " + e.getMessage());
            }

            System.out.println();
        }
    }

    private static void printStudentMenu() {
        System.out.println("=== OGRENCI ISLEMLERI ===");
        System.out.println("1) Ogrenci ekle (Student / GraduateStudent)");
        System.out.println("2) Ogrencileri listele");
        System.out.println("3) Ogrenciyi derse kaydet");
        System.out.println("4) Ogrenciyi dersten dusur");
        System.out.println("5) Ogrencinin derslerini goster");
        System.out.println("6) Harc (tuition) hesapla");
        System.out.println("7) Not gir / guncelle (0.0 - 4.0)");
        System.out.println("8) GPA goster");
        System.out.println("0) Geri");
        System.out.print("Secim: ");
    }

    private static void printInstructorMenu() {
        System.out.println("=== HOCA ISLEMLERI ===");
        System.out.println("1) Hoca ekle");
        System.out.println("2) Hocalari listele");
        System.out.println("0) Geri");
        System.out.print("Secim: ");
    }

    private static void printCourseMenu() {
        System.out.println("=== DERS ISLEMLERI ===");
        System.out.println("1) Ders ekle (hocayla birlikte)");
        System.out.println("2) Dersleri listele");
        System.out.println("0) Geri");
        System.out.print("Secim: ");
    }


    private static void addStudent() {
        System.out.print("Ogrenci tipi (1=Student, 2=GraduateStudent): ");
        String type = readLine();

        System.out.print("Ogrenci ID: ");
        String id = readLine();

        if (studentsById.containsKey(id)) {
            throw new IllegalStateException("Bu ID ile ogrenci zaten var: " + id);
        }

        System.out.print("Ogrenci Ad: ");
        String name = readLine();

        Student s;
        if ("2".equals(type)) {
            s = new GraduateStudent(id, name);
        } else if ("1".equals(type)) {
            s = new Student(id, name);
        } else {
            throw new IllegalArgumentException("Gecersiz ogrenci tipi.");
        }

        studentsById.put(id, s);
        System.out.println("Ogrenci eklendi: " + s);
    }

    private static void addInstructor() {
        System.out.print("Hoca ID: ");
        String id = readLine();

        if (instructorsById.containsKey(id)) {
            throw new IllegalStateException("Bu ID ile hoca zaten var: " + id);
        }

        System.out.print("Hoca Ad: ");
        String name = readLine();

        Instructor ins = new Instructor(id, name);

        instructorCatalog.addInstructor(ins);
        instructorsById.put(id, ins);

        System.out.println("Hoca eklendi: " + ins);
    }

    private static void addCourse() {
        if (instructorsById.isEmpty()) {
            guideUser(
                    "Ders eklemek icin once en az 1 hoca eklemelisin.",
                    "Ana Menu > 2) Hoca Islemleri > 1) Hoca ekle"
            );
            return;
        }

        System.out.print("Ders Kodu: ");
        String code = readLine();

        if (coursesByCode.containsKey(code)) {
            throw new IllegalStateException("Bu kod ile ders zaten var: " + code);
        }

        System.out.print("Ders Adi: ");
        String name = readLine();

        System.out.print("Kredi: ");
        int credit = readInt();

        System.out.print("Dersi verecek hoca ID: ");
        String instructorId = readLine();

        Instructor ins = instructorsById.get(instructorId);
        if (ins == null) {
            guideUser(
                    "Bu ID ile hoca bulunamadi: " + instructorId,
                    "Hocalari gormek icin: Ana Menu > 2) Hoca Islemleri > 2) Hocalari listele"
            );
            return;
        }

        Course course = new Course(code, name, credit);

        System.out.println("Ders gunu secin: MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY");
        System.out.print("Gun: ");
        String dayRaw = readLine().toUpperCase();

        Day day;
        try {
            day = Day.valueOf(dayRaw);
        } catch (Exception e) {
            guideUser(
                    "Gecersiz gun: " + dayRaw,
                    "Ornek format: MONDAY veya FRIDAY"
            );
            return;
        }

        System.out.print("Baslangic saati (0-23): ");
        int start = readInt();

        System.out.print("Bitis saati (1-24): ");
        int end = readInt();

        TimeSlot slot = new TimeSlot(day, start, end);
        course.setTimeSlot(slot);


        courseCatalog.addCourse(course, ins);
        coursesByCode.put(code, course);

        System.out.println("Ders eklendi: " + course);
    }

    private static void registerCourseToStudent() {
        if (!ensureStudentsExist()) return;
        if (!ensureCoursesExist()) return;

        Student s = askStudentWithHelp();
        if (s == null) return;

        Course c = askCourseWithHelp();
        if (c == null) return;

        s.registerCourse(c);
        System.out.println("Kayit basarili. Ogrenci: " + s.getId() + " -> Ders: " + c.getCode());
    }

    private static void dropCourseFromStudent() {
        if (!ensureStudentsExist()) return;
        if (!ensureCoursesExist()) return;

        Student s = askStudentWithHelp();
        if (s == null) return;

        Course c = askCourseWithHelp();
        if (c == null) return;

        s.dropCourse(c);
        System.out.println("Ders birakildi. Ogrenci: " + s.getId() + " -> Ders: " + c.getCode());
    }

    private static void listCoursesInCatalog() {
        if (coursesByCode.isEmpty()) {
            guideUser(
                    "Su an katalogda ders yok.",
                    "Ders eklemek icin: Ana Menu > 3) Ders Islemleri > 1) Ders ekle"
            );
            return;
        }

        System.out.println("=== KATALOG DERSLERI ===");
        for (Course c : courseCatalog.listCourses()) {
            System.out.println("- " + c);
        }
    }

    private static void listInstructors() {
        if (instructorsById.isEmpty()) {
            guideUser(
                    "Su an hoca yok.",
                    "Hoca eklemek icin: Ana Menu > 2) Hoca Islemleri > 1) Hoca ekle"
            );
            return;
        }

        System.out.println("=== HOCALAR ===");
        for (Instructor i : instructorCatalog.listInstructors()) {
            System.out.println("- " + i);
        }
    }

    private static void listStudents() {
        if (studentsById.isEmpty()) {
            guideUser(
                    "Su an ogrenci yok.",
                    "Ogrenci eklemek icin: Ana Menu > 1) Ogrenci Islemleri > 1) Ogrenci ekle"
            );
            return;
        }

        System.out.println("=== OGRENCILER ===");
        for (Student s : studentsById.values()) {
            System.out.println("- " + s);
        }
    }

    private static void showStudentCourses() {
        if (!ensureStudentsExist()) return;

        Student s = askStudentWithHelp();
        if (s == null) return;

        System.out.println("=== " + s.getName() + " dersleri ===");
        System.out.println(s.getRegisteredCourses());
    }

    private static void showTuition() {
        if (!ensureStudentsExist()) return;

        Student s = askStudentWithHelp();
        if (s == null) return;

        System.out.println("Tuition (" + s.getId() + "): " + s.calculateTuition());
    }


    private static void setGradePointForStudent() {
        if (!ensureStudentsExist()) return;
        if (!ensureCoursesExist()) return;

        Student s = askStudentWithHelp();
        if (s == null) return;

        Course c = askCourseWithHelp();
        if (c == null) return;

        System.out.println("Bilgi: Not girebilmek icin ogrencinin bu derse kayitli olmasi gerekir. FF notu icin 0.01 notunu giriniz.");
        System.out.print("Grade point gir (0.0 - 4.0): ");
        double gp = readDouble();


        s.setGradePoint(c, gp);

        System.out.println("Not guncellendi: Ogrenci " + s.getId() + " -> " + c.getCode() + " = " + gp);
    }

    private static void showGpa() {
        if (!ensureStudentsExist()) return;

        Student s = askStudentWithHelp();
        if (s == null) return;

        double gpa = s.calculateGPA();
        System.out.println("GPA (" + s.getId() + "): " + format2(gpa));
        System.out.println("Not: Sadece not girilmis dersler GPA'ya dahil edilir.");
    }

    // ===== GUIDANCE / HELPERS =====

    private static boolean ensureStudentsExist() {
        if (studentsById.isEmpty()) {
            guideUser(
                    "Islem icin once en az 1 ogrenci eklemelisin.",
                    "Ana Menu > 1) Ogrenci Islemleri > 1) Ogrenci ekle"
            );
            return false;
        }
        return true;
    }

    private static boolean ensureCoursesExist() {
        if (coursesByCode.isEmpty()) {
            guideUser(
                    "Islem icin once en az 1 ders eklemelisin.",
                    "On kosul: once hoca ekle, sonra ders ekle.\n" +
                            "Hoca eklemek icin: Ana Menu > 2) Hoca Islemleri > 1) Hoca ekle\n" +
                            "Ders eklemek icin: Ana Menu > 3) Ders Islemleri > 1) Ders ekle"
            );
            return false;
        }
        return true;
    }

    private static Student askStudentWithHelp() {
        System.out.print("Ogrenci ID: ");
        String studentId = readLine();

        Student s = studentsById.get(studentId);
        if (s == null) {
            guideUser(
                    "Bu ID ile ogrenci bulunamadi: " + studentId,
                    "Ogrencileri gormek icin: Ogrenci Islemleri > 2) Ogrencileri listele"
            );
            return null;
        }
        return s;
    }

    private static Course askCourseWithHelp() {
        System.out.print("Ders Kodu: ");
        String code = readLine();

        Course c = coursesByCode.get(code);
        if (c == null) {
            guideUser(
                    "Bu kod ile ders bulunamadi: " + code,
                    "Dersleri gormek icin: Ders Islemleri > 2) Dersleri listele\n" +
                            "Ders eklemek icin: Ders Islemleri > 1) Ders ekle"
            );
            return null;
        }
        return c;
    }

    private static void guideUser(String message, String nextStep) {
        System.out.println("Bilgilendirme: " + message);
        System.out.println("Yonlendirme: " + nextStep);
    }

    private static String readLine() {
        return sc.nextLine().trim();
    }

    private static int readInt() {
        String raw = readLine();
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Gecersiz sayi: " + raw);
        }
    }

    private static double readDouble() {
        String raw = readLine();
        try {
            return Double.parseDouble(raw);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Gecersiz sayi: " + raw);
        }
    }

    private static String format2(double value) {
        return String.format("%.2f", value);
    }
}


