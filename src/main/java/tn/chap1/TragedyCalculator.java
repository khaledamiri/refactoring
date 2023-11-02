package tn.chap1;

public class TragedyCalculator extends PerformanceCalculator {

    public TragedyCalculator(Performance aPerformance, Play aPlay) {
        super(aPerformance, aPlay);
    }

    public double getAmount() {
        double result;
        result = 40000;
        if (this.getPerformance().getAudience() > 30) {
            result += 1000 * (this.getPerformance().getAudience() - 30);
        }
        return result;
    }
}
