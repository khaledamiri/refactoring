package tn.chap1;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public class Statement {
    Map<String, Object> createStatementData(Invoice invoice, Map<String, Play> plays) {
        Map<String, Object> statementData = new HashMap<>();
        statementData.put("customer", invoice.getCustomer());
        statementData.put("performances", invoice.getPerformances().stream()
                .map(performance -> enrichPerformance(performance, plays))
                .collect(Collectors.toList()));
        statementData.put("totalAmount", totalAmount(statementData));
        statementData.put("totalVolumeCredits", totalVolumeCredits(statementData));
        return statementData;
    }

    private Performance enrichPerformance(Performance aPerformance, Map<String, Play> plays) {
        PerformanceCalculator calculator= createPerformanceCalculator(aPerformance, playFor(plays, aPerformance));
        Performance result = new Performance();
        result.setAudience(aPerformance.getAudience());

        result.setPlay(calculator.getPlay());
        result.setAmount(calculator.getAmount());
        result.setVolumeCredits(calculator.volumeCredits());
        return result;
    }

    private static PerformanceCalculator createPerformanceCalculator(Performance aPerformance, Play play) {
        switch (play.getType()) {
            case "tragedy":
                return new TragedyCalculator(aPerformance, play);
            case "comedy":
                return new ComedyCalculator(aPerformance, play);
            default:
                throw new IllegalArgumentException("Unknown type: "+play.getType());
        }
    }

    private static double totalAmount(Map<String, Object> data) {
        double result = 0;
        for (Performance perf : (List<Performance>) data.get("performances")) {
            result += perf.getAmount();
        }
        return result;
    }

    private static int totalVolumeCredits(Map<String, Object> data) {
        int volumeCredits = 0;
        for (Performance perf : (List<Performance>) data.get("performances")) {
            volumeCredits += perf.getVolumeCredits();
        }
        return volumeCredits;
    }

    static String usd(double aNumber) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        return format.format(aNumber / 100);
    }

    private static Play playFor(Map<String, Play> plays, Performance perf) {
        return plays.get(perf.getPlayID());
    }
}
