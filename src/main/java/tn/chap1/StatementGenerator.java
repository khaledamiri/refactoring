package tn.chap1;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class StatementGenerator {
    public String generateStatement(Invoice invoice, Map<String, Play> plays) {
        double totalAmount = 0;
        int volumeCredits = 0;
        String result = "Statement for " + invoice.getCustomer() + "\n";
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);

        for (Performance perf : invoice.getPerformances()) {
            double thisAmount = 0;

            thisAmount = amountFor(perf, playFor(plays, perf));

            volumeCredits += volumeCreditsFor(plays, perf);

            // print line for this order
            result += " " + playFor(plays, perf).getName() + ": " + format.format(thisAmount / 100) + " (" + perf.getAudience() + " seats)\n";
            totalAmount += thisAmount;
        }

        result += "Amount owed is " + format.format(totalAmount / 100) + "\n";
        result += "You earned " + volumeCredits + " credits\n";
        return result;
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