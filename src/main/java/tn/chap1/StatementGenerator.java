package tn.chap1;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

public class StatementGenerator {
    public String statement(Invoice invoice, Map<String, Play> plays) {

        String result = "Statement for " + invoice.getCustomer() + "\n";

        for (Performance perf : invoice.getPerformances()) {
            // print line for this order
            result += " " + playFor(plays, perf).getName() + ": " + usd(amountFor(perf, playFor(plays, perf))) + " (" + perf.getAudience() + " seats)\n";
        }

        result += "Amount owed is " + usd(totalAmount(invoice, plays)) + "\n";
        result += "You earned " + totalVolumeCredits(invoice, plays) + " credits\n";
        return result;
    }

    private static double totalAmount(Invoice invoice, Map<String, Play> plays) {
        double result = 0;
        for (Performance perf : invoice.getPerformances()) {
            result += amountFor(perf, playFor(plays, perf));
        }
        return result;
    }

    private static int totalVolumeCredits(Invoice invoice, Map<String, Play> plays) {
        int volumeCredits = 0;
        for (Performance perf : invoice.getPerformances()) {
            volumeCredits += volumeCreditsFor(plays, perf);
        }
        return volumeCredits;
    }

    private static String usd(double aNumber) {
        return format().format(aNumber / 100);
    }

    private static NumberFormat format() {
        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        return format;
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