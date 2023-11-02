package tn.chap1;

import java.util.List;
import java.util.Map;

import static tn.chap1.Statement.usd;

public class CreateStatementData {
    public String statement(Invoice invoice, Map<String, Play> plays) {
        return renderPlainText(new Statement().createStatementData(invoice, plays));
    }

    private static String renderPlainText(Map<String, Object> data) {
        String result = "Statement for " + data.get("customer") + "\n";

        for (Performance perf : (List<Performance>) data.get("performances")) {
            // print line for this order
            result += " " + perf.getPlay().getName() + ": " + usd(perf.getAmount()) + " (" + perf.getAudience() + " seats)\n";
        }

        result += "Amount owed is " + usd((Double) data.get("totalAmount")) + "\n";
        result += "You earned " + data.get("totalVolumeCredits") + " credits\n";
        return result;
    }

}