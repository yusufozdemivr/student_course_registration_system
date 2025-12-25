public class TimeSlot {

    private final Day day;
    private final int startHour;
    private final int endHour;

    public TimeSlot(Day day, int startHour, int endHour) {
        if (day == null) {
            throw new IllegalArgumentException("Gun girilecek kisim bos birakilamaz.");
        }
        if (startHour < 0 || startHour > 23) {
            throw new IllegalArgumentException("Baslangic saati 0 ve 23 arasinda olmalidir.");
        }
        if (endHour < 1 || endHour > 24) {
            throw new IllegalArgumentException("Bitis saati 1 ve 24 arasinda olmalidir.");
        }
        if (startHour >= endHour) {
            throw new IllegalArgumentException("Başlangıç saati bitiş saatinden daha erken olmalidir.");
        }
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public Day getDay() {
        return day;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public boolean conflictsWith(TimeSlot other) {
        if (other == null) return false;
        if (this.day != other.day) return false;

        return this.startHour < other.endHour && other.startHour < this.endHour;
    }

    @Override
    public String toString() {
        return day + " " + startHour + ":00-" + endHour + ":00";
    }
}
