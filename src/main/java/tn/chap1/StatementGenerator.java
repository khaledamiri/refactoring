package tn.chap1;

import java.text.NumberFormat;
import java.util.*;

public class StatementGenerator {
    public String statement(Invoice invoice, Map<String, Play> plays) {
        Map<String, Object> statementData = new HashMap<>();
        statementData.put("customer",invoice.getCustomer());
        List<Performance> performances = invoice.getPerformances();
        statementData.put("performances", enrichPerformances(performances));
        return renderPlainText(statementData, invoice, plays);
    }

    private static List<Performance> enrichPerformances(List<Performance> performances) {
        return new ArrayList<>(performances);
    }


    private static String renderPlainText(Map<String, Object> data, Invoice invoice, Map<String, Play> plays) {
        String result = "Statement for " + data.get("customer")+ "\n";

        for (Performance perf : (List<Performance>) data.get("performances")) {
            // print line for this order
            result += " " + playFor(plays, perf).getName() + ": " + usd(amountFor(perf, playFor(plays, perf))) + " (" + perf.getAudience() + " seats)\n";
        }

        result += "Amount owed is " + usd(totalAmount(data, invoice, plays)) + "\n";
        result += "You earned " + totalVolumeCredits(data, invoice, plays) + " credits\n";
        return result;
    }

    private static double totalAmount(Map<String, Object> data,Invoice invoice, Map<String, Play> plays) {
        double result = 0;
        for (Performance perf : (List<Performance>) data.get("performances")) {
            result += amountFor(perf, playFor(plays, perf));
        }
        return result;
    }

    private static int totalVolumeCredits(Map<String, Object> data,Invoice invoice, Map<String, Play> plays) {
        int volumeCredits = 0;
        for (Performance perf : (List<Performance>) data.get("performances")) {
            volumeCredits += volumeCreditsFor(plays, perf);
        }
        return volumeCredits;
    }

    private static String usd(double aNumber) {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        return format.format(aNumber / 100);
    }

    private static int volumeCreditsFor(Map<String, Play> plays, Performance perf) {
        int result = 0;
        // add volume credits
        result += Math.max(perf.getAudience() - 30, 0);

        // add extra credit for every ten comedy attendees
        if ("comedy".equals(playFor(plays, perf).getType())) {
            result += Math.floorDiv(perf.getAudience(), 5);
        }
        return result;
    }

    private static Play playFor(Map<String, Play> plays, Performance perf) {
        return plays.get(perf.getPlayID());
    }

    private static double amountFor(Performance aPerformance, Play play) {
        double result;
        switch (play.getType()) {
            case "tragedy":
                result = 40000;
                if (aPerformance.getAudience() > 30) {
                    result += 1000 * (aPerformance.getAudience() - 30);
                }
                break;
            case "comedy":
                result = 30000;
                if (aPerformance.getAudience() > 20) {
                    result += 10000 + 500 * (aPerformance.getAudience() - 20);
                }
                result += 300 * aPerformance.getAudience();
                break;
            default:
                throw new IllegalArgumentException("Unknown type: " + play.getType());
        }
        return result;
    }
}