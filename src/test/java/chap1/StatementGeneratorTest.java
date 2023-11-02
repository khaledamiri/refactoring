package chap1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tn.chap1.Invoice;
import tn.chap1.Play;
import tn.chap1.CreateStatementData;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class StatementGeneratorTest {

    CreateStatementData statementGenerator;

    @BeforeEach
    void setUp() {
        statementGenerator = new CreateStatementData();
    }

    String play = "{\n" +
            "\"hamlet\": {\"name\": \"Hamlet\", \"type\": \"tragedy\"},\n" +
            "\"as-like\": {\"name\": \"As You Like It\", \"type\": \"comedy\"},\n" +
            "\"othello\": {\"name\": \"Othello\", \"type\": \"tragedy\"}\n" +
            "}";

    String innvoices = "[\n" +
            "{\n" +
            "\"customer\": \"BigCo\",\n" +
            "\"performances\": [\n" +
            "{\n" +
            "\"playID\": \"hamlet\",\n" +
            "\"audience\": 55\n" +
            "},\n" +
            "{\n" +
            "\"playID\": \"as-like\",\n" +
            "\"audience\": 35\n" +
            "},\n" +
            "{\n" +
            "\"playID\": \"othello\",\n" +
            "\"audience\": 40\n" +
            "}\n" +
            "]\n" +
            "}]";

    String playException = "{\n" +
            "\"wrongplayIdType\": {\"name\": \"Police\", \"type\": \"police\"},\n" +
            "\"as-like\": {\"name\": \"As You Like It\", \"type\": \"comedy\"},\n" +
            "\"othello\": {\"name\": \"Othello\", \"type\": \"tragedy\"}\n" +
            "}";
    String innvoicesWithWrongPlay = "[\n" +
            "{\n" +
            "\"customer\": \"BigCo\",\n" +
            "\"performances\": [\n" +
            "{\n" +
            "\"playID\": \"wrongplayIdType\",\n" +
            "\"audience\": 55\n" +
            "}\n" +
            "]\n" +
            "}]";

    @Test
    @DisplayName("Nominal Case")
    void testGenerateStatement() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Play> playMap = objectMapper.readValue(play, new TypeReference<>() {
        });
        List<Invoice> invoices = objectMapper.readValue(innvoices, new TypeReference<>() {
        });
        assertEquals("Statement for BigCo\n" +
                        " Hamlet: $650.00 (55 seats)\n" +
                        " As You Like It: $580.00 (35 seats)\n" +
                        " Othello: $500.00 (40 seats)\n" +
                        "Amount owed is $1,730.00\n" +
                        "You earned 47 credits\n", statementGenerator.statement(invoices.get(0), playMap),
                "Generate Statement should work");

    }

    @Test
    @DisplayName("Throw Exception When Generate Statement With Wrong PlayId Type")
    void shouldThrowExceptionWhenGenerateStatementWithWrongPlayIdType() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Play> playMap = objectMapper.readValue(playException, new TypeReference<>() {
        });
        List<Invoice> invoices = objectMapper.readValue(innvoicesWithWrongPlay, new TypeReference<>() {
        });

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> statementGenerator.statement(invoices.get(0), playMap));
        assertEquals("Unknown type: police", exception.getMessage());

    }
}
