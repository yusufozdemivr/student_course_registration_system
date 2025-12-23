public class Main {
    public static void main(String[] args) {



        Course c1 = new Course("BLML101", "Algoritma programlama", 4);
        Course c2 = new Course("BLML102", "Nesne tabanli pgramlama", 5);

        CourseCatalog catalog = new CourseCatalog();

        Instructor i1 = new Instructor("I01", "Ahmet Hoca");
        Instructor i2 = new Instructor("I02", "Mehmet Hoca");

        catalog.addCourse(c1, i1);
        catalog.addCourse(c2, i2);


        Student s1 = new Student("S1", "Yusuf");
        GraduateStudent g1 = new GraduateStudent("S2", "Ahmet");

        s1.registerCourse(c1);
        s1.registerCourse(c2);


        try {
            s1.registerCourse(c1);
        } catch (Exception e) {
            System.out.println("Duplicate registration blocked: " + e.getMessage());
        }


        System.out.println("Student courses: " + s1.getRegisteredCourses());


        System.out.println("Student tuition: " + s1.calculateTuition());
        System.out.println("Graduate tuition: " + g1.calculateTuition());


        s1.dropCourse(c1);
        System.out.println("After drop: " + s1.getRegisteredCourses());
    }
}
