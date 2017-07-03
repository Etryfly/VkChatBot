package main.bot;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.Actor;
import com.vk.api.sdk.client.actors.UserActor;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * Created by etryfly on 27.06.17.
 */
public class ChatBot {
    final String[] COMMON_PHRASES = {
            "Нет ничего ценнее слов, сказанных к месту и ко времени.",
            "Порой молчание может сказать больше, нежели уйма слов.",
            "Перед тем как писать/говорить всегда лучше подумать.",
            "Вежливая и грамотная речь говорит о величии души.",
            "Приятно когда текст без орфографических ошибок.",
            "Многословие есть признак неупорядоченного ума.",
            "Слова могут ранить, но могут и исцелять.",
            "Записывая слова, мы удваиваем их силу.",
            "Кто ясно мыслит, тот ясно излагает.",
            "Боюсь Вы что-то не договариваете."};
    final String[] ELUSIVE_ANSWERS = {
            "Вопрос непростой, прошу тайм-аут на раздумья.",
            "Не уверен, что располагаю такой информацией.",
            "Может лучше поговорим о чём-то другом?",
            "Простите, но это очень личный вопрос.",
            "Не уверен, что Вам понравится ответ.",
            "Поверьте, я сам хотел бы это знать.",
            "Вы действительно хотите это знать?",
            "Уверен, Вы уже догадались сами.",
            "Зачем Вам такая информация?",
            "Давайте сохраним интригу?"};

    final Map<String, String> PATTERNS_FOR_ANALYSIS = new HashMap<String, String>() {{
        // hello
        put("хай", "hello");
        put("привет", "hello");
        put("здорово", "hello");
        put("здравствуй", "hello");
        // who
        put("кто\\s.*ты", "who");
        put("ты\\s.*кто", "who");
        // name
        put("как\\s.*зовут", "name");
        put("как\\s.*имя", "name");
        put("есть\\s.*имя", "name");
        put("какое\\s.*имя", "name");
        // howareyou
        put("как\\s.*дела", "howareyou");
        put("как\\s.*жизнь", "howareyou");
        // whatdoyoudoing
        put("зачем\\s.*тут", "whatdoyoudoing");
        put("зачем\\s.*здесь", "whatdoyoudoing");
        put("что\\s.*делаешь", "whatdoyoudoing");
        put("чем\\s.*занимаешься", "whatdoyoudoing");
        // whatdoyoulike
        put("что\\s.*нравится", "whatdoyoulike");
        put("что\\s.*любишь", "whatdoyoulike");
        // iamfeelling
        put("кажется", "iamfeelling");
        put("чувствую", "iamfeelling");
        put("испытываю", "iamfeelling");
        // yes
        put("^да", "yes");
        put("согласен", "yes");
        // whattime
        put("который\\s.*час", "whattime");
        put("сколько\\s.*время", "whattime");
        // bye
        put("прощай", "bye");
        put("увидимся", "bye");
        put("до\\s.*свидания", "bye");
        //meme
        put("мем", "meme");
        put("взрыв", "meme");
    }};
    final Map<String, String> ANSWERS_BY_PATTERNS = new HashMap<String, String>() {{
        put("hello", "Мегумин приветствует тебя");
        put("who", "Я - великий архимаг из клана багровых демонов");
        put("name", "Зови меня Мегумин)");
        put("howareyou", "У меня всё хорошо");
        put("whatdoyoudoing", "Я пробую общаться с людьми");
        put("whatdoyoulike", "Я люблю взрывы...");
        put("iamfeelling", "Как давно это началось? Расскажи чуть подробнее.");
        put("yes", "Согласие есть продукт при полном непротивлении сторон.");
        put("bye", "Пока. Приходи еще)");
        put("meme", "https://pp.userapi.com/c635101/v635101824/d6cb/OjWEu3DtVJI.jpg");
    }};
    Pattern pattern; // for regexp
    Random random; // for random answers
    Date date; // for date and time

    public ChatBot() {
        random = new Random();
        date = new Date();
    }

     public String sayInReturn(String msg) {
       String say = (msg.trim().endsWith("?"))?
               ELUSIVE_ANSWERS[random.nextInt(ELUSIVE_ANSWERS.length)]:
               COMMON_PHRASES[random.nextInt(COMMON_PHRASES.length)];

        String message = String.join(" ", msg.toLowerCase().split("[ {,|.}?]+"));
        for (Map.Entry<String, String> o : PATTERNS_FOR_ANALYSIS.entrySet()) {
            pattern = Pattern.compile(o.getKey());
            if (pattern.matcher(message).find()) {
                if (o.getValue().equals("whattime")) return date.toString();
                else return ANSWERS_BY_PATTERNS.get(o.getValue());
            }
        }


        return say;
    }


}
