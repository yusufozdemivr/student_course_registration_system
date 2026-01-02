import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RegistrationTest {

    // Student bilgisi verilmeden Registration oluşturulmaya çalışıldığında hata fırlatılmasını test eder
    @Test
    void constructor_shouldThrowException_whenStudentIsNull() {
        Course course = new Course("C1", "Math", 3);

        assertThrows(IllegalArgumentException.class, () -> {
            new Registration(null, course);
        });
    }

    // Course bilgisi verilmeden Registration oluşturulmaya çalışıldığında hata fırlatılmasını test eder
    @Test
    void constructor_shouldThrowException_whenCourseIsNull() {
        Student student = new Student("57", "Yusuf");

        assertThrows(IllegalArgumentException.class, () -> {
            new Registration(student, null);
        });
    }

    // Aynı öğrenci ve aynı ders için oluşturulan iki Registration nesnesinin eşit kabul edilmesini test eder
    @Test
    void equals_shouldReturnTrue_whenSameStudentAndSameCourse() {
        Student s1 = new Student("57", "Yusuf");
        Course c1 = new Course("C1", "Math", 3);

        Registration r1 = new Registration(s1, c1);
        Registration r2 = new Registration(s1, c1);

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());
    }

    // Öğrenciler farklı olduğunda Registration nesnelerinin eşit kabul edilmemesini test eder
    @Test
    void equals_shouldReturnFalse_whenDifferentStudent() {
        Student s1 = new Student("57", "Yusuf");
        Student s2 = new Student("58", "Ali");
        Course c1 = new Course("C1", "Math", 3);

        Registration r1 = new Registration(s1, c1);
        Registration r2 = new Registration(s2, c1);

        assertNotEquals(r1, r2);
    }

    // Dersler farklı olduğunda Registration nesnelerinin eşit kabul edilmemesini test eder
    @Test
    void equals_shouldReturnFalse_whenDifferentCourse() {
        Student s1 = new Student("57", "Yusuf");
        Course c1 = new Course("C1", "Math", 3);
        Course c2 = new Course("C2", "Physics", 3);

        Registration r1 = new Registration(s1, c1);
        Registration r2 = new Registration(s1, c2);

        assertNotEquals(r1, r2);
    }

    // Yeni oluşturulan bir Registration nesnesinde not bilgisinin varsayılan olarak 0.0 olmasını test eder
    @Test
    void getGradePoint_shouldBeZeroByDefault() {
        Student student = new Student("57", "Yusuf");
        Course course = new Course("C1", "Math", 3);

        Registration r = new Registration(student, course);

        assertEquals(0.0, r.getGradePoint());
    }

    // Not değeri olarak 0.0 verildiğinde bunun geçerli kabul edilmesini test eder
    @Test
    void setGradePoint_shouldAcceptZero() {
        Student student = new Student("57", "Yusuf");
        Course course = new Course("C1", "Math", 3);

        Registration r = new Registration(student, course);
        r.setGradePoint(0.0);

        assertEquals(0.0, r.getGradePoint());
    }

    // 0.0 ile 4.0 arasındaki geçerli not değerlerinin sorunsuz şekilde atanabildiğini test eder
    @Test
    void setGradePoint_shouldAcceptValidRange() {
        Student student = new Student("57", "Yusuf");
        Course course = new Course("C1", "Math", 3);

        Registration r = new Registration(student, course);

        r.setGradePoint(4.0);
        assertEquals(4.0, r.getGradePoint());

        r.setGradePoint(2.5);
        assertEquals(2.5, r.getGradePoint());
    }

    // Geçerli aralığın dışında kalan not değerleri girildiğinde hata fırlatılmasını test eder
    @Test
    void setGradePoint_shouldThrowException_whenOutOfRange() {
        Student student = new Student("57", "Yusuf");
        Course course = new Course("C1", "Math", 3);

        Registration r = new Registration(student, course);

        assertThrows(IllegalArgumentException.class, () -> r.setGradePoint(-1.0));
        assertThrows(IllegalArgumentException.class, () -> r.setGradePoint(4.1));
        assertThrows(IllegalArgumentException.class, () -> r.setGradePoint(10.0));
    }
}
