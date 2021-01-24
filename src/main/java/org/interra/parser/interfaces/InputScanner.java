package org.interra.parser.interfaces;

import java.io.InputStream;

public interface InputScanner {
    boolean blockedInputScan(InputStream is, LineValidator lineValidator);
}
