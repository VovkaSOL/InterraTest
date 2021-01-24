package org.interra.parser.emails;

import org.interra.parser.interfaces.LineValidator;

public class LineValidatorUserWithEmails implements LineValidator {
    /**
     * @param str проверяемая строка
     * @return true если соответствует требуемому формату:
     * <p>user1 -> xxx@ya.ru, foo@gmail.com, lol@mail.ru
     * <p>false если нет
     */
    @Override
    public boolean validateLine(String str) {
        //регулярку можно ещё прокачать на длины имён и ящиков
        return str.matches("^\\w* ->( \\w*@\\w*.\\w*,)* \\w*@\\w*.\\w*");
    }
}
