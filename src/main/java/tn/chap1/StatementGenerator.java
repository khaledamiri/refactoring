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
            Play play = plays.get(perf.getPlayID());
            double thisAmount = 0;

            thisAmount = amountFor(perf, play);

            // add volume credits
            volumeCredits += Math.max(perf.getAudience() - 30, 0);

            // add extra credit for every ten comedy attendees
            if ("comedy".equals(play.getType())) {
                volumeCredits += Math.floorDiv(perf.getAudience(), 5);
            }

            // print line for this order
            result += " " + play.getName() + ": " + format.format(thisAmount / 100) + " (" + perf.getAudience() + " seats)\n";
            totalAmount += thisAmount;
        }

        result += "Amount owed is " + format.format(totalAmount / 100) + "\n";
        result += "You earned " + volumeCredits + " credits\n";
        return result;
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