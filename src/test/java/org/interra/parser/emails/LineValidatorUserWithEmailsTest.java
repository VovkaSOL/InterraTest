package org.interra.parser.emails;

import org.interra.parser.interfaces.LineValidator;
import org.junit.Assert;
import org.junit.Test;

public class LineValidatorUserWithEmailsTest {

    @Test
    public void validateLine() {
        LineValidator validator = new LineValidatorUserWithEmails();
        Assert.assertTrue("Строка 1 не проходит валидацию формата", validator.validateLine("user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru, ups@pisem.net, aaa@bbb.ru"));
        Assert.assertTrue("Строка 2 не проходит валидацию формата", validator.validateLine("user5 -> xyz@pisem.net"));
    }
}