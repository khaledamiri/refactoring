package tn.chap1;

public class PerformanceCalculator {

    private Performance performance;
    private Play play;

    public PerformanceCalculator() {
    }

    public PerformanceCalculator(Performance aPerformance, Play aPlay) {
        this.performance = aPerformance;
        this.play = aPlay;
    }

    double amountFor() {
        double result;
        switch (this.play.getType()) {
            case "tragedy":
                result = 40000;
                if (this.performance.getAudience() > 30) {
                    result += 1000 * (this.performance.getAudience() - 30);
                }
                break;
            case "comedy":
                result = 30000;
                if (this.performance.getAudience() > 20) {
                    result += 10000 + 500 * (this.performance.getAudience() - 20);
                }
                result += 300 * this.performance.getAudience();
                break;
            default:
                throw new IllegalArgumentException("Unknown type: " + this.play.getType());
        }
        return result;
    }

    public Performance getPerformance() {
        return performance;
    }

    public void setPerformance(Performance performance) {
        this.performance = performance;
    }

    public Play getPlay() {
        return play;
    }

    public void setPlay(Play play) {
        this.play = play;
    }
}
