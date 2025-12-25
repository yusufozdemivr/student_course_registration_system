import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);


    private static final CourseCatalog courseCatalog = new CourseCatalog();
    private static final InstructorCatalog instructorCatalog = new InstructorCatalog();


    private static final Map<String, Student> studentsById = new HashMap<>();
    private static final Map<String, Instructor> instructorsById = new HashMap<>();
    private static final Map<String, Course> coursesByCode = new HashMap<>();

    public static void main(String[] args) {

        while (true) {
            printMenu();
            String choice = sc.nextLine().trim();

            try {
                switch (choice) {
                    case "1" -> addStudent();
                    case "2" -> addInstructor();
                    case "3" -> addCourse();
                    case "4" -> registerCourseToStudent();
                    case "5" -> dropCourseFromStudent();
                    case "6" -> listCoursesInCatalog();
                    case "7" -> listInstructors();
                    case "8" -> listStudents();
                    case "9" -> showStudentCourses();
                    case "10" -> showTuition();
                    case "0" -> {
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

    private static void printMenu() {
        System.out.println("===== OGRENCI DERS KAYIT SISTEMI =====");
        System.out.println("1) Ogrenci ekle (Student / GraduateStudent)");
        System.out.println("2) Hoca ekle");
        System.out.println("3) Ders ekle (hocayla birlikte)");
        System.out.println("4) Ogrenciyi derse kaydet");
        System.out.println("5) Ogrenciyi dersten dusur");
        System.out.println("6) Katalogdaki dersleri listele");
        System.out.println("7) Hocalari listele");
        System.out.println("8) Ogrencileri listele");
        System.out.println("9) Ogrencinin derslerini goster");
        System.out.println("10) Harc (tuition) hesapla");
        System.out.println("0) Cikis");
        System.out.print("Secim: ");
    }


    private static void addStudent() {
        System.out.print("Ogrenci tipi (1=Student, 2=GraduateStudent): ");
        String type = sc.nextLine().trim();

        System.out.print("Ogrenci ID: ");
        String id = sc.nextLine().trim();

        if (studentsById.containsKey(id)) {
            throw new IllegalStateException("Bu ID ile ogrenci zaten var: " + id);
        }

        System.out.print("Ogrenci Ad: ");
        String name = sc.nextLine().trim();

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
        String id = sc.nextLine().trim();

        if (instructorsById.containsKey(id)) {
            throw new IllegalStateException("Bu ID ile hoca zaten var: " + id);
        }

        System.out.print("Hoca Ad: ");
        String name = sc.nextLine().trim();

        Instructor ins = new Instructor(id, name);


        instructorCatalog.addInstructor(ins);
        instructorsById.put(id, ins);

        System.out.println("Hoca eklendi: " + ins);
    }


    private static void addCourse() {
        System.out.print("Ders Kodu: ");
        String code = sc.nextLine().trim();

        if (coursesByCode.containsKey(code)) {
            throw new IllegalStateException("Bu kod ile ders zaten var: " + code);
        }

        System.out.print("Ders Adi: ");
        String name = sc.nextLine().trim();

        System.out.print("Kredi: ");
        int credit = readInt();

        System.out.print("Dersi verecek hoca ID: ");
        String instructorId = sc.nextLine().trim();

        Instructor ins = instructorsById.get(instructorId);
        if (ins == null) {
            throw new IllegalStateException("Hoca bulunamadi: " + instructorId);
        }

        Course course = new Course(code, name, credit);


        courseCatalog.addCourse(course, ins);

        coursesByCode.put(code, course);

        System.out.println("Ders eklendi: " + course);
    }


    private static void registerCourseToStudent() {
        Student s = askStudent();
        Course c = askCourse();

        s.registerCourse(c);
        System.out.println("Kayit basarili. Ogrenci: " + s.getId() + " -> Ders: " + c.getCode());
    }


    private static void dropCourseFromStudent() {
        Student s = askStudent();
        Course c = askCourse();

        s.dropCourse(c);
        System.out.println("Ders birakildi. Ogrenci: " + s.getId() + " -> Ders: " + c.getCode());
    }


    private static void listCoursesInCatalog() {
        System.out.println("=== KATALOG DERSLERI ===");
        for (Course c : courseCatalog.listCourses()) {
            System.out.println("- " + c);
        }
    }


    private static void listInstructors() {
        System.out.println("=== HOCALAR ===");
        for (Instructor i : instructorCatalog.listInstructors()) {
            System.out.println("- " + i);
        }
    }


    private static void listStudents() {
        System.out.println("=== OGRENCILER ===");
        for (Student s : studentsById.values()) {
            System.out.println("- " + s);
        }
    }


    private static void showStudentCourses() {
        Student s = askStudent();
        System.out.println("=== " + s.getName() + " dersleri ===");
        System.out.println(s.getRegisteredCourses());
    }


    private static void showTuition() {
        Student s = askStudent();
        System.out.println("Tuition (" + s.getId() + "): " + s.calculateTuition());
    }


    private static Student askStudent() {
        System.out.print("Ogrenci ID: ");
        String studentId = sc.nextLine().trim();

        Student s = studentsById.get(studentId);
        if (s == null) {
            throw new IllegalStateException("Ogrenci bulunamadi: " + studentId);
        }
        return s;
    }

    private static Course askCourse() {
        System.out.print("Ders Kodu: ");
        String code = sc.nextLine().trim();

        Course c = coursesByCode.get(code);
        if (c == null) {
            throw new IllegalStateException("Ders bulunamadi (once kataloga ekle): " + code);
        }
        return c;
    }

    private static int readInt() {
        String raw = sc.nextLine().trim();
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Gecersiz sayi: " + raw);
        }
    }
}