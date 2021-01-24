package org.interra.parser.emails;

import org.interra.parser.interfaces.InputScanner;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;

public class UserEmailScannerTest {
    private static final String VALID_INPUT_DATA =
            "user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru\n" +
                    "user2 -> foo@gmail.com, ups@pisem.net\n" +
                    "user3 -> xyz@pisem.net, vasya@pupkin.com\n" +
                    "user4 -> ups@pisem.net, aaa@bbb.ru\n" +
                    "user5 -> xyz@pisem.net\n" +
                    "\n";

    //TODO добавить негативные сценарии
    @Test
    public void blockedInputScan() {
        HashMap<String, StringBuilder> userMailsResult = new HashMap<>(); //структура для хранения результирующих данных
        InputScanner scanner = new UserEmailScanner(userMailsResult);
        InputStream stream = new ByteArrayInputStream(VALID_INPUT_DATA.getBytes());
        Assert.assertTrue("Не удалось считать данные из входного потока", scanner.blockedInputScan(stream, new LineValidatorUserWithEmails()));
        Assert.assertEquals("Количество пользователей не равно 2", 2, userMailsResult.size());
        Assert.assertEquals("user1 содержит неверные email",
                "xxx@ya.ru, foo@gmail.com, lol@mail.ru, ups@pisem.net, aaa@bbb.ru",
                userMailsResult.get("user1").toString());
        Assert.assertEquals("user3 содержит неверные email",
                "xyz@pisem.net, vasya@pupkin.com",
                userMailsResult.get("user3").toString());
    }
}