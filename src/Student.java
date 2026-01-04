import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student implements Registrable {

    private String id;
    private String name;

    private final List<Registration> registrations = new ArrayList<>();

    public Student(String id, String name) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Ogrenci ID numarasi bos birakilamaz.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Ogrenci adi bos birakilamaz.");
        }
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    @Override
    public void registerCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Ders secimi bos olamaz. Lutfen ders ismi giriniz. ");
        }

        if (course.getTimeSlot() == null) {
            throw new IllegalArgumentException("Ders icin gerekli zaman araligi bos olamaz. " + course.getCode());
        }

        Registration registration = new Registration(this, course);
        if (registrations.contains(registration)) {
            throw new IllegalStateException(
                    "Ogrenci zaten bu derse kayitli: " + course.getCode()
            );
        }

        for (Registration r : registrations) {
            Course existing = r.getCourse();

            if (existing.getTimeSlot() == null) continue;

            if (existing.getTimeSlot().conflictsWith(course.getTimeSlot())) {
                throw new IllegalArgumentException(
                        "Derslerin zamani cakisiyor. " + course.getCode() + " ve " + existing.getCode()
                );
            }
        }

        registrations.add(registration);
    }

    @Override
    public void dropCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Ders kismi bos olamaz. Lutfen ders ismi giriniz. ");
        }

        Registration registration = new Registration(this, course);
        boolean removed = registrations.remove(registration);

        if (!removed) throw new IllegalStateException("Gerekli ders bulunamadi. " + course.getCode());
    }

    public List<Course> getRegisteredCourses() {
        List<Course> courses = new ArrayList<>();
        for (Registration r : registrations) {
            courses.add(r.getCourse());
        }
        return courses;
    }

    protected int getTotalCredits() {
        int sum = 0;
        for (Course c : getRegisteredCourses()) {
            sum += c.getCredit();
        }
        return sum;
    }

    public double calculateTuition() {
        double baseFee = 500.0;
        double feePerCredit = 100.0;
        return baseFee + getTotalCredits() * feePerCredit;
    }

    public double calculateGPA() {
        double totalPoints = 0.0;
        double totalCredits = 0.0;

        for (Registration r : registrations) {
            double gp = r.getGradePoint();

            if (gp == 0.0) {
                continue;
            }

            int credit = r.getCourse().getCredit();
            totalPoints += gp * credit;
            totalCredits += credit;
        }

        if (totalCredits == 0.0) return 0.0;
        return totalPoints / totalCredits;
    }


    public void setGradePoint(Course course, double gradePoint) {
        if (course == null) {
            throw new IllegalArgumentException("Ders secimi bos olamaz, lutfen gerekli dersi sisteme isleyiniz. ");
        }

        Registration key = new Registration(this, course);
        int idx = registrations.indexOf(key);

        if (idx < 0) {
            throw new IllegalStateException("Ogrenci bu derse kayitli degil. " + course.getCode());
        }

        registrations.get(idx).setGradePoint(gradePoint);
    }

    public Double getGradePointOf(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Ders kismi bos olamaz.");
        }

        Registration key = new Registration(this, course);
        int idx = registrations.indexOf(key);
        if (idx < 0) {
            return null;
        }
        return registrations.get(idx).getGradePoint();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}
