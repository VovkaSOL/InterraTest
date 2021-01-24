package org.interra.parser.emails;

import org.interra.parser.interfaces.InputScanner;
import org.interra.parser.interfaces.LineValidator;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

/***
 *
 */
public class UserEmailScanner implements InputScanner {
    private final HashMap<String, MutableUserName> mailUserSaver = new HashMap<>();
    private final HashMap<String, StringBuilder> userMailsResult;
    private final LineValidatorUserWithEmails validator = new LineValidatorUserWithEmails();

    public UserEmailScanner(HashMap<String, StringBuilder> userMailsResult) {
        this.userMailsResult = userMailsResult;
    }

    /**
     * @param is            входной поток, будет читаться построчно (строки разделяются символом \n)
     * @param lineValidator проверяет соответсвие считанной линии заданному формату
     * @return true если удалось успешно считать данные, они прошли валидацию и были разложены в результирующую структуру
     * <p> false во всех других случаях
     */
    //TODO заменить boolean на коды ошибок через enum
    @Override
    public boolean blockedInputScan(InputStream is, LineValidator lineValidator) {
        Scanner input = new Scanner(is);
        while (true) {
            String str = input.nextLine();
            if (str.equals("")) {
                return true;
            }
            if (!lineValidator.validateLine(str)) {
                return false;
            }
            parseOneLine(str);
        }
    }

    /**
     * @param all должна приходить только строка, соответствующая требуемому формату (смотри {@link LineValidatorUserWithEmails})
     */
    private void parseOneLine(String all) {
        //линейная сложность получившегося алгоритма
        //не делаем all.split в массив для экономии памяти и времени, парсим посимвольно
        StringBuilder mailStrToAdd = new StringBuilder();
        int parsingIndex = all.indexOf(" -> ");
        String mail;
        String user = all.substring(0, parsingIndex);
        MutableUserName mutableUserName = new MutableUserName(); // описание в javadoc
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

    /**
     * Класс wrapper над именем пользователя,
     * нужен, чтобы подменить имя пользователя за один шаг в произвольном количестве уже лежащих в памяти ссылок на MutableUserName
     * Пример :
     * foo@gmail.com -> MutableUserName -> user2
     * ups@pisem.net -> MutableUserName -> user2
     */
    class MutableUserName {
        private String userName;
    }
}
