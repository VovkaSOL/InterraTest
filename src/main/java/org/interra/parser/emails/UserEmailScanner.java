package org.interra.parser.emails;

import org.interra.parser.interfaces.InputScanner;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

/***
 *
 */
public class UserEmailScanner implements InputScanner {
    private HashMap<String, MutableUserName> mailUserSaver = new HashMap<>();
    private HashMap<String, StringBuilder> userMailsResult;
    Validator validator = new Validator();

    public UserEmailScanner(HashMap<String, StringBuilder> userMailsResult) {
        this.userMailsResult = userMailsResult;
    }

    @Override
    public void scanInput(InputStream is) {
        Scanner input = new Scanner(is);
        boolean parsing = true;
        while (parsing) {
            String str = input.nextLine();
            if (str.equals("")) {
                parsing = false;
                continue;
            }
            parseOneLine(str);
        }
    }

    void parseOneLine(String all) { //линейная сложность получившегося алгоритма
        StringBuilder mailStrToAdd = new StringBuilder();
        int parsingIndex = all.indexOf(" -> ");
        String mail;
        String user = all.substring(0, parsingIndex);
        MutableUserName mutableUserName = new MutableUserName();
        mutableUserName.userName = user;
        int emailsStartIndex = parsingIndex + 2;
        parsingIndex = emailsStartIndex;
        do {
            int startIndex = parsingIndex + 2;
            parsingIndex = all.indexOf(",", startIndex);
            if (parsingIndex != -1) {
                mail = all.substring(startIndex, parsingIndex); //берём следующий email из строки
            } else {
                mail = all.substring(startIndex); //берём последниЙ email из строки
            }
            if (mailUserSaver.containsKey(mail)) {
                mutableUserName.userName = mailUserSaver.get(mail).userName; //если такой mail есть то берём имя пользователя
            } else {
                mailUserSaver.put(mail, mutableUserName); //если нет, то кладём новый email-пользователь
                mailStrToAdd.append(", ").append(mail);
            }
        }
        while (parsingIndex != -1);
        String mailStr = all.substring(emailsStartIndex + 2);
        userMailsResult.computeIfPresent(mutableUserName.userName, (s, stringBuilder) ->
                stringBuilder.append(mailStrToAdd)); //добавляем к существующему пользователю все email из строки
        userMailsResult.putIfAbsent(mutableUserName.userName, new StringBuilder(mailStr));
    }

    class MutableUserName {
        public String userName;
    }

}
