import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    @Test
    void constructor_shouldSetIdAndNameCorrectly() {
        Student student = new Student("57", "Yusuf");

        assertEquals("57", student.getId());
        assertEquals("Yusuf", student.getName());
    }

    @Test
    void constructor_shouldThrowException_whenIdIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student("", "Yusuf");
        });
    }

    @Test
    void constructor_shouldThrowException_whenNameIsBlank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Student("57", "");
        });
    }


    @Test
    void registerCourse_shouldThrowException_whenCourseIsNull() {
        Student student = new Student("57", "Yusuf");

        assertThrows(IllegalArgumentException.class, () -> {
            student.registerCourse(null);
        });
    }

    @Test
    void registerCourse_shouldThrowException_whenTimeSlotIsNull() {
        Student student = new Student("57", "Yusuf");

        Course course = new Course("d1", "oop", 3);
        // timeSlot set edilmedi

        assertThrows(IllegalArgumentException.class, () -> {
            student.registerCourse(course);
        });
    }

    @Test
    void registerCourse_shouldAddCourse_whenValid() {
        Student student = new Student("57", "Yusuf");

        Course course = new Course("d1", "oop", 3);
        course.setTimeSlot(new TimeSlot(Day.MONDAY, 10, 12));

        student.registerCourse(course);

        assertTrue(student.getRegisteredCourses().contains(course));
    }

    @Test
    void registerCourse_shouldThrowException_whenDuplicateCourse() {
        Student student = new Student("57", "Yusuf");

        Course course = new Course("d1", "oop", 3);
        course.setTimeSlot(new TimeSlot(Day.MONDAY, 10, 12));

        student.registerCourse(course);

        assertThrows(IllegalStateException.class, () -> {
            student.registerCourse(course);
        });
    }

    @Test
    void registerCourse_shouldThrowException_whenTimeConflicts() {
        Student student = new Student("57", "Yusuf");

        Course c1 = new Course("d1", "oop", 3);
        c1.setTimeSlot(new TimeSlot(Day.MONDAY, 10, 12));

        Course c2 = new Course("d2", "goruntu", 3);
        c2.setTimeSlot(new TimeSlot(Day.MONDAY, 11, 13)); // çakışıyor

        student.registerCourse(c1);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            student.registerCourse(c2);
        });

        assertTrue(ex.getMessage().toLowerCase().contains("cakis"));
    }

    @Test
    void dropCourse_shouldThrowException_whenCourseIsNull() {
        Student student = new Student("57", "Yusuf");

        assertThrows(IllegalArgumentException.class, () -> {
            student.dropCourse(null);
        });
    }

    @Test
    void dropCourse_shouldRemoveCourse_whenRegistered() {
        Student student = new Student("57", "Yusuf");

        Course course = new Course("d1", "oop", 3);
        course.setTimeSlot(new TimeSlot(Day.MONDAY, 10, 12));

        student.registerCourse(course);
        student.dropCourse(course);

        assertFalse(student.getRegisteredCourses().contains(course));
    }

    @Test
    void dropCourse_shouldThrowException_whenCourseNotRegistered() {
        Student student = new Student("57", "Yusuf");

        Course course = new Course("C1", "Math", 3);

        assertThrows(IllegalStateException.class, () -> {
            student.dropCourse(course);
        });
    }

    @Test
    void getGradePointOf_shouldReturnNull_whenNotRegistered() {
        Student student = new Student("57", "Yusuf");
        Course course = new Course("d1", "oop", 3);

        assertNull(student.getGradePointOf(course));
    }

    @Test
    void setGradePoint_shouldThrowException_whenCourseIsNull() {
        Student student = new Student("57", "Yusuf");

        assertThrows(IllegalArgumentException.class, () -> student.setGradePoint(null, 3.0));
    }

    @Test
    void setGradePoint_shouldThrowException_whenNotRegistered() {
        Student student = new Student("57", "Yusuf");
        Course course = new Course("d1", "oop", 3);

        assertThrows(IllegalStateException.class, () -> student.setGradePoint(course, 3.0));
    }

    @Test
    void setGradePoint_shouldSetValue_whenRegistered() {
        Student student = new Student("57", "Yusuf");

        Course course = new Course("d1", "oop", 3);
        course.setTimeSlot(new TimeSlot(Day.MONDAY, 10, 12));

        student.registerCourse(course);
        student.setGradePoint(course, 3.5);

        assertEquals(3.5, student.getGradePointOf(course));
    }

    @Test
    void calculateGPA_shouldReturnZero_whenAllGradePointsAreZero() {
        Student student = new Student("57", "Yusuf");

        Course course = new Course("d1", "oop", 3);
        course.setTimeSlot(new TimeSlot(Day.MONDAY, 10, 12));

        student.registerCourse(course);

        // gradePoint default 0.0 olduğu için GPA 0 olmalı
        assertEquals(0.0, student.calculateGPA());
    }

    @Test
    void calculateGPA_shouldComputeWeightedAverageCorrectly() {
        Student student = new Student("57", "Yusuf");

        Course c1 = new Course("d1", "oop", 3);
        c1.setTimeSlot(new TimeSlot(Day.MONDAY, 10, 12));

        Course c2 = new Course("d2", "goruntu", 4);
        c2.setTimeSlot(new TimeSlot(Day.TUESDAY, 10, 12)); // çakışmasın

        student.registerCourse(c1);
        student.registerCourse(c2);

        student.setGradePoint(c1, 4.0);
        student.setGradePoint(c2, 2.0);

        // (4*3 + 2*4) / (3+4) = 20/7
        assertEquals(20.0 / 7.0, student.calculateGPA(), 1e-9);
    }

    @Test
    void calculateTuition_shouldMatchTotalCredits() {
        Student student = new Student("57", "Yusuf");

        Course c1 = new Course("d1", "oop", 3);
        c1.setTimeSlot(new TimeSlot(Day.MONDAY, 10, 12));

        Course c2 = new Course("d2", "goruntu", 4);
        c2.setTimeSlot(new TimeSlot(Day.TUESDAY, 10, 12));

        student.registerCourse(c1);
        student.registerCourse(c2);

        assertEquals(1200.0, student.calculateTuition());
    }


    @Test
    void calculateGPA_shouldIgnoreCoursesWithoutGradePoint() {
        Student student = new Student("1", "Ali");

        Course c1 = new Course("C1", "Math", 3);
        c1.setTimeSlot(new TimeSlot(Day.MONDAY, 10, 12));

        Course c2 = new Course("C2", "Physics", 4);
        c2.setTimeSlot(new TimeSlot(Day.TUESDAY, 10, 12));

        student.registerCourse(c1);
        student.registerCourse(c2);


        student.setGradePoint(c1, 4.0);

        assertEquals(4.0, student.calculateGPA(), 0.0001);
    }

}
