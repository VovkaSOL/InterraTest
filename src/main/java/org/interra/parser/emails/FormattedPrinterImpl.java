package org.interra.parser.emails;

import org.interra.parser.interfaces.FormattedPrinter;

import java.io.PrintStream;
import java.util.Map;

public class FormattedPrinterImpl implements FormattedPrinter {
    private PrintStream os;

    public FormattedPrinterImpl(PrintStream os) {
        this.os = os;
    }

    @Override
    public void printResult(Map<?, ?> map) {
        map.forEach((key, value) -> {
            os.print(key);
            os.print(" -> ");
            os.println(value);
        });
    }
}
