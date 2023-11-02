package tn.chap1;

public class ComedyCalculator extends PerformanceCalculator {
    public ComedyCalculator(Performance aPerformance, Play aPlay) {
        super(aPerformance, aPlay);
    }

    public double getAmount() {
        double result;
        result = 30000;
        if (this.getPerformance().getAudience() > 20) {
            result += 10000 + 500 * (this.getPerformance().getAudience() - 20);
        }
        result += 300 * this.getPerformance().getAudience();
        return result;
    }

    @Override
    int volumeCredits() {
        // add extra credit for every ten comedy attendees
        return super.volumeCredits() + Math.floorDiv(this.getPerformance().getAudience(), 5);
    }
}