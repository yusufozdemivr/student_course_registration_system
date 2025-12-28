import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentTest {

    @Test
    void constructor_shouldSetIdAndNameCorrectly() {
        Student student = new Student("123", "Ali");

        assertEquals("123", student.getId());
        assertEquals("Ali", student.getName());
    }
}
