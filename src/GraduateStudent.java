public class GraduateStudent extends Student {

    public GraduateStudent(String id, String name) {
        super(id, name);
    }

    @Override
    public double calculateTuition(){
        double baseFee = 800.0;
        double feePerCredit = 150.0;

        return baseFee + getTotalCredits() * feePerCredit;
    }
}
