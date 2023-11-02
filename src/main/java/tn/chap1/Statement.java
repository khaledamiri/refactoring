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
        PerformanceCalculator calculator= new PerformanceCalculator(aPerformance, playFor(plays, aPerformance));
        Performance result = new Performance();
        result.setPlay(calculator.getPlay());
        result.setAudience(aPerformance.getAudience());
        result.setAmount(calculator.amountFor());
        result.setVolumeCredits(volumeCreditsFor(result));
        return result;
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

    private static int volumeCreditsFor(Performance perf) {
        int result = 0;
        // add volume credits
        result += Math.max(perf.getAudience() - 30, 0);

        // add extra credit for every ten comedy attendees
        if ("comedy".equals(perf.getPlay().getType())) {
            result += Math.floorDiv(perf.getAudience(), 5);
        }
        return result;
    }

    private static Play playFor(Map<String, Play> plays, Performance perf) {
        return plays.get(perf.getPlayID());
    }
}
