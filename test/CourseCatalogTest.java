import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CourseCatalogTest {

    // Catalog'a null course eklenmeye çalışıldığında hata fırlatılmasını test eder
    @Test
    void addCourse_shouldThrowException_whenCourseIsNull() {
        CourseCatalog catalog = new CourseCatalog();
        Instructor instructor = new Instructor("I1", "Ahmet");

        assertThrows(IllegalArgumentException.class, () -> {
            catalog.addCourse(null, instructor);
        });
    }

    // Catalog'a null instructor ile course eklenmeye çalışıldığında hata fırlatılmasını test eder
    @Test
    void addCourse_shouldThrowException_whenInstructorIsNull() {
        CourseCatalog catalog = new CourseCatalog();
        Course course = new Course("C1", "Math", 3);

        assertThrows(IllegalArgumentException.class, () -> {
            catalog.addCourse(course, null);
        });
    }

    // Instructor verilmeden addCourse çağrıldığında sistemin bunu engellemesini test eder
    @Test
    void addCourse_withoutInstructor_shouldThrowUnsupportedOperation() {
        CourseCatalog catalog = new CourseCatalog();
        Course course = new Course("C1", "Math", 3);

        assertThrows(UnsupportedOperationException.class, () -> {
            catalog.addCourse(course);
        });
    }

    // Geçerli course ve instructor ile ders eklenince katalogda görünmesini test eder
    @Test
    void addCourse_shouldAddCourseAndSetInstructor_whenValid() {
        CourseCatalog catalog = new CourseCatalog();
        Course course = new Course("C1", "Math", 3);
        Instructor instructor = new Instructor("I1", "Ahmet");

        catalog.addCourse(course, instructor);

        List<Course> courses = catalog.listCourses();
        assertTrue(courses.contains(course));
        assertEquals(instructor, course.getInstructor());
    }

    // Aynı ders ikinci kez eklenmeye çalışıldığında hata fırlatılmasını test eder
    @Test
    void addCourse_shouldThrowException_whenDuplicateCourse() {
        CourseCatalog catalog = new CourseCatalog();
        Course course1 = new Course("C1", "Math", 3);
        Course course2 = new Course("C1", "Math-2", 4); // code aynı ise equals true olmalı
        Instructor instructor = new Instructor("I1", "Ahmet");

        catalog.addCourse(course1, instructor);

        assertThrows(IllegalStateException.class, () -> {
            catalog.addCourse(course2, instructor);
        });
    }

    // null course silinmeye çalışıldığında hata fırlatılmasını test eder
    @Test
    void removeCourse_shouldThrowException_whenCourseIsNull() {
        CourseCatalog catalog = new CourseCatalog();

        assertThrows(IllegalArgumentException.class, () -> {
            catalog.removeCourse(null);
        });
    }

    // Var olan ders silinince katalogdan kalkmasını test eder
    @Test
    void removeCourse_shouldRemoveCourse_whenExists() {
        CourseCatalog catalog = new CourseCatalog();
        Course course = new Course("C1", "Math", 3);
        Instructor instructor = new Instructor("I1", "Ahmet");

        catalog.addCourse(course, instructor);
        catalog.removeCourse(course);

        assertFalse(catalog.listCourses().contains(course));
    }

    // Katalogda olmayan bir ders silinmeye çalışıldığında hata atmamasını test eder
    @Test
    void removeCourse_shouldNotThrow_whenCourseNotExists() {
        CourseCatalog catalog = new CourseCatalog();
        Course course = new Course("C1", "Math", 3);

        assertDoesNotThrow(() -> {
            catalog.removeCourse(course);
        });
    }

    // listCourses metodunun dışarıya kopya liste döndürdüğünü test eder (catalog bozulmamalı)
    @Test
    void listCourses_shouldReturnCopy_notInternalList() {
        CourseCatalog catalog = new CourseCatalog();
        Course course = new Course("C1", "Math", 3);
        Instructor instructor = new Instructor("I1", "Ahmet");

        catalog.addCourse(course, instructor);

        List<Course> list = catalog.listCourses();
        list.clear(); // dışarıdaki listeyi bozuyoruz

        assertEquals(1, catalog.listCourses().size());
    }
}
