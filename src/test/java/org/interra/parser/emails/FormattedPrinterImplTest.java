package org.interra.parser.emails;

import org.interra.parser.interfaces.FormattedPrinter;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class FormattedPrinterImplTest {
    Map<String, String> test1 = Map.of(
            "user1", "xxx@ya.ru"
    );
    String expectedResult = "user1 -> xxx@ya.ru\n";

    @Test
    public void printResult() throws UnsupportedEncodingException {
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final String utf8 = StandardCharsets.UTF_8.name();
        try (PrintStream ps = new PrintStream(baos, true, utf8)) {
            FormattedPrinter printer = new FormattedPrinterImpl(ps);
            printer.printResult(test1);
        }
        String data = baos.toString(utf8).replaceAll("\r\n", "\n");
        System.out.println(data);
        Assert.assertEquals("Формат вывода нарушен", expectedResult, data);
    }
}