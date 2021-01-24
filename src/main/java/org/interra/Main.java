package org.interra;

import org.interra.parser.emails.FormattedPrinterImpl;
import org.interra.parser.emails.LineValidatorUserWithEmails;
import org.interra.parser.emails.UserEmailScanner;
import org.interra.parser.interfaces.FormattedPrinter;
import org.interra.parser.interfaces.InputScanner;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<String, StringBuilder> userMailsResult = new HashMap<>(); //структура для хранения результирующих данных
        InputScanner scanner = new UserEmailScanner(userMailsResult);
        System.out.println("У Вас одна попытка, введите данные в соответствии с шаблоном (окончанием ввода является пустая строка без пробелов): \n" +
                "user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru\n" +
                "user2 -> foo@gmail.com, ups@pisem.net\n" +
                "user3 -> xyz@pisem.net, vasya@pupkin.com\n" +
                "user4 -> ups@pisem.net, aaa@bbb.ru\n" +
                "user5 -> xyz@pisem.net\n" +
                "\n");
        System.out.println("Ваш текст?");
        if (scanner.blockedInputScan(System.in, new LineValidatorUserWithEmails())) {
            System.out.println("Вот что получилось:");
            FormattedPrinter printer = new FormattedPrinterImpl(System.out);
            printer.printResult(userMailsResult);
            System.out.println("Отличная работа, спасибо за использование наших продуктов");
        } else {
            System.out.println("Вы неудачно использовали свою попытку");
        }
        System.out.println("Прощайте");
    }
}
