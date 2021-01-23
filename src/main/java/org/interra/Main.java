package org.interra;

import org.interra.parser.emails.FormattedPrinterImpl;
import org.interra.parser.emails.UserEmailScanner;
import org.interra.parser.interfaces.FormattedPrinter;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, StringBuilder> userMailsResult = new HashMap<>(); //структура для хранения результирующих данных
        UserEmailScanner parser = new UserEmailScanner(userMailsResult);
        System.out.println("У Вас одна попытка, чтобы ввести данные в соответствии с шаблоном: \n" +
                "```\n" +
                "user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru\n" +
                "user2 -> foo@gmail.com, ups@pisem.net\n" +
                "user3 -> xyz@pisem.net, vasya@pupkin.com\n" +
                "user4 -> ups@pisem.net, aaa@bbb.ru\n" +
                "user5 -> xyz@pisem.net\n" +
                "\n" +
                "```");
        parser.scanInput(System.in);
        FormattedPrinter printer = new FormattedPrinterImpl(System.out);
        printer.printResult(userMailsResult);
        System.out.println("Прощайте");
    }
}
