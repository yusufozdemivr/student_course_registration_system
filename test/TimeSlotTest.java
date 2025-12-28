import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TimeSlotTest {

    //burada aynu gün içinde saatleri çakışan iki zaman aralığının çakışma olarak algılanmasını test eder
    @Test
    void conflictsWith_shouldReturnTrue_whenOverlappingSameDay() {
        TimeSlot t1 = new TimeSlot(Day.MONDAY, 10, 12);
        TimeSlot t2 = new TimeSlot(Day.MONDAY, 11, 13);

        assertTrue(t1.conflictsWith(t2));
        assertTrue(t2.conflictsWith(t1)); // simetrik olmalı
    }


    //farklı günlerdeki zaman aralıklarının saatleri aynı olsa bile çakışma sayılmasını test eder
    @Test
    void conflictsWith_shouldReturnFalse_whenDifferentDays() {
        TimeSlot t1 = new TimeSlot(Day.MONDAY, 10, 12);
        TimeSlot t2 = new TimeSlot(Day.TUESDAY, 10, 12);

        assertFalse(t1.conflictsWith(t2));
    }

    //bitiş saati ile diğer dersin başlangıç saati aynıysa bunun çakışma sayılmamasını test eder
    @Test
    void conflictsWith_shouldReturnFalse_whenTouchingButNotOverlapping() {
        TimeSlot t1 = new TimeSlot(Day.MONDAY, 10, 12);
        TimeSlot t2 = new TimeSlot(Day.MONDAY, 12, 14);

        assertFalse(t1.conflictsWith(t2));
        assertFalse(t2.conflictsWith(t1));
    }

    //aynı gün farklı saatlerin çakışma yapmadığını test eder
    @Test
    void conflictsWith_shouldReturnFalse_whenCompletelySeparateSameDay() {
        TimeSlot t1 = new TimeSlot(Day.MONDAY, 8, 10);
        TimeSlot t2 = new TimeSlot(Day.MONDAY, 11, 13);

        assertFalse(t1.conflictsWith(t2));
    }

    //karşılaştırılan zaman aralığı null olduğunda çalışma olmadığını test eder
    @Test
    void conflictsWith_shouldReturnFalse_whenOtherIsNull() {
        TimeSlot t1 = new TimeSlot(Day.MONDAY, 10, 12);

        assertFalse(t1.conflictsWith(null));
    }

    //gün bilinmeden timeslot oluşturmaya çalışıldığında hata vermesini test eder
    @Test
    void constructor_shouldThrowException_whenDayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TimeSlot(null, 10, 12);
        });
    }

    //başlangıç saati geçersiz aralıkta verildiğinde timeslot oluşturulmasının engellenmesini test eder
    @Test
    void constructor_shouldThrowException_whenStartHourInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TimeSlot(Day.MONDAY, -1, 12);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new TimeSlot(Day.MONDAY, 24, 12);
        });
    }

    //bitiş saati geçersiz aralıktaysa timeslot oluşturulmasını engellemeyi test eder
    @Test
    void constructor_shouldThrowException_whenEndHourInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TimeSlot(Day.MONDAY, 10, 0);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new TimeSlot(Day.MONDAY, 10, 25);
        });
    }

    //başlangışç saati bitiş saatine eşit veya büyük olduğunda hata vermesini test eder
    @Test
    void constructor_shouldThrowException_whenStartHourGreaterOrEqualEndHour() {
        assertThrows(IllegalArgumentException.class, () -> {
            new TimeSlot(Day.MONDAY, 12, 12);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new TimeSlot(Day.MONDAY, 14, 12);
        });
    }
}
