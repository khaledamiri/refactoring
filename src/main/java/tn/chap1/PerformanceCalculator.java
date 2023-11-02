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

    int volumeCredits() {
        int result = 0;
        // add volume credits
        result += Math.max(this.performance.getAudience() - 30, 0);

        return result;
    }

    double getAmount() {
        throw new Error("subclass responsibility");
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
