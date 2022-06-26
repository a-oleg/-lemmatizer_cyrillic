package com.github.a_oleg.service;

import com.github.a_oleg.converter.WordToWordDtoConverter;
import com.github.a_oleg.dto.WordDto;
import com.github.a_oleg.entity.Word;
import com.github.a_oleg.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.*;

@Transactional
@Service
public class URLService {
    private final WordRepository wordRepository;
    @Autowired
    public URLService(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    /**Метод, возвращающий количество повторений лематизированных слов*/
    public ArrayList<WordDto> countLematizedWordsByUrls(ArrayList<String> urls) {
        System.out.println("Парсинг сайтов");
        ArrayList<String> textsByUrls = parseURL(urls);
        HashMap<String, Integer> nonLematizedCyrillicAndNonCyrillicWords = createNotLematizedWordsMap(textsByUrls);

        HashMap<String, Integer> nonLematizedCyrillicWords = new HashMap<>();
        HashMap<String, Integer> nonLematizedNonCyrillicWords = new HashMap<>();
        System.out.println("Проверка слов на кирилицу, количество слов: " + nonLematizedCyrillicAndNonCyrillicWords.size());
        for(Map.Entry<String, Integer> entry: nonLematizedCyrillicAndNonCyrillicWords.entrySet()) {
            if(checkCyrillic(entry.getKey())) {
                nonLematizedCyrillicWords.put(entry.getKey(), entry.getValue());
            } else {
                nonLematizedNonCyrillicWords.put(entry.getKey(), entry.getValue());
            }
        }

        ArrayList<WordDto> LematizedCyrillicAndNonLematizedNonCyrillicWords = new ArrayList<>();

        ArrayList<WordDto> lematizedCyrillicWords;
        if(nonLematizedCyrillicWords.size() != 0) {
            System.out.println("Лематизация кирилического списка слов, количество нелематизированных слов: "  + nonLematizedCyrillicWords.size());
            lematizedCyrillicWords = checkingLematizedForm(nonLematizedCyrillicWords);
            LematizedCyrillicAndNonLematizedNonCyrillicWords.addAll(lematizedCyrillicWords);
        }

        ArrayList<WordDto> nonlematizedNonCyrillicWords;
        if(nonLematizedNonCyrillicWords.size() != 0) {
            System.out.println("Создание не кирилического списка слов, количество слов: "  + nonLematizedNonCyrillicWords.size());
            nonlematizedNonCyrillicWords = creatingListOfNonCyrillicWords(nonLematizedNonCyrillicWords);
            LematizedCyrillicAndNonLematizedNonCyrillicWords.addAll(nonlematizedNonCyrillicWords);
        }
        System.out.println("Сформирован итоговый список объектов WordDto размером " + LematizedCyrillicAndNonLematizedNonCyrillicWords.size() + " элементов");
        return LematizedCyrillicAndNonLematizedNonCyrillicWords;
    }

    /**Метод, парсящий тексты сайтов*/
    private ArrayList<String> parseURL(ArrayList<String> urls) {
        ArrayList<String> urlsAndTexts = new ArrayList<>();
        Document htmlDocument = null;

        for(String url : urls) {
            String bodyTagText;
            try {
                htmlDocument = Jsoup.connect(url).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                bodyTagText = htmlDocument.body().text();
            } catch (NullPointerException e) {
                bodyTagText = null;
            }
            urlsAndTexts.add(bodyTagText);
        }
        return urlsAndTexts;
    }

    /**Метод, формирующий список "Слово-количество повторений" из текстов*/
    private HashMap<String, Integer> createNotLematizedWordsMap(ArrayList<String> texts) {
        HashMap<String, Integer> notLematizedMap = new HashMap<>();
        for(String text : texts) {
            StringTokenizer stringTokenizer = new StringTokenizer(text, " ,.•;:?!<>«»(){}/|\\#$&^–=+_~`'\"\r\n\t\f");
            while (stringTokenizer.hasMoreTokens()) {
                String token = stringTokenizer.nextToken();
                if(notLematizedMap.containsKey(token)) {
                    notLematizedMap.put(token.toLowerCase(), notLematizedMap.get(token)+1);
                } else {
                    notLematizedMap.put(token.toLowerCase(), 1);
                }
            }
        }
        return notLematizedMap;
    }

    /**Метод, определяющий принадлежит ли слово кирилице*/
    private boolean checkCyrillic(String word){
        boolean result = false;
        for (char a : word.toCharArray()) {
            if (Character.UnicodeBlock.of(a) == Character.UnicodeBlock.CYRILLIC) {
                result = !result;
                break;
            }
        }
        return result;
    }

    /**Метод, возвращающий массив кирилических лематизированных слов WordDto*/
    private ArrayList<WordDto> checkingLematizedForm(HashMap<String, Integer> notLematizedWordsMap) {
        ArrayList<WordDto> lematizedWords = new ArrayList<>();
        Word word = null;
        WordDto wordDto;

        for(Map.Entry<String, Integer> entry: notLematizedWordsMap.entrySet()) {
            System.out.println("Анализ слова: " + entry.getKey());
            //Коллекция Set необходима на случай, если в БД будет найдено больше одного значения (особенности БД)
            Set<Word> wordSet = wordRepository.findByWord(entry.getKey());
            //System.out.println("Для слова " + entry.getKey() + " найдено " + wordSet.size() + " элементов в БД");
            for (Word w : wordSet) {
                word = w;
                break;
            }

            if (wordSet.size() == 0) {
                wordDto = new WordDto(0, entry.getKey(), 0, 0, entry.getValue(), true);
                lematizedWords.add(wordDto);
                continue;
            }
            if(word.getCodeParent() == 0) {
                wordDto = WordToWordDtoConverter.convert(word);
            } else {
                while (word.getCodeParent() != 0) {
                    //System.out.println("В процессе поиска родителя с кодом " + word.getCodeParent());
                    Set<Word> wordParentSet = wordRepository.findByCode(word.getCodeParent());
                    //System.out.println("Найдено " + wordParentSet.size() + " родителей");
                    for (Word wordParent : wordParentSet) {
                        word = wordParent;
                        break;
                    }
                }
                wordDto = WordToWordDtoConverter.convert(word);
            }

            if(lematizedWords.contains(wordDto)) {
                int indexElement = lematizedWords.indexOf(wordDto);
                WordDto wordDtoByIndex = lematizedWords.get(indexElement);
                wordDtoByIndex.setCount(wordDtoByIndex.getCount() + entry.getValue());
            } else {
                wordDto.setCount(1);
                lematizedWords.add(wordDto);
            }
        }
        return lematizedWords;
    }

    /**Метод, возвращающий массив не кирилических слов WordDto*/
    private ArrayList<WordDto> creatingListOfNonCyrillicWords(HashMap<String, Integer> nonCyrillicWordsMap) {
        ArrayList<WordDto> nonCyrillicWords = new ArrayList<>();
        for(Map.Entry<String, Integer> entry: nonCyrillicWordsMap.entrySet()) {
            nonCyrillicWords.add(new WordDto(0, entry.getKey(), 0, 0, entry.getValue(), false));
        }
        return nonCyrillicWords;
    }
}
