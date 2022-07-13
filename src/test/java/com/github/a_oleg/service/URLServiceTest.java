package com.github.a_oleg.service;

import com.github.a_oleg.entity.Word;
import com.github.a_oleg.repository.WordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class URLServiceTest {
    @Mock
    WordRepository wordRepository;

    @InjectMocks
    URLService urlService;

    @Test
    void whenParseValidUrls_thenReturnTrue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://yandex.ru/");
        urls.add("https://ya.ru/");
        urls.add("https://mail.ru/");

        Method method = URLService.class.getDeclaredMethod("parseURL", ArrayList.class);
        method.setAccessible(true);
        ArrayList<String> strings = (ArrayList<String>)method.invoke(urlService, urls);
        Assertions.assertEquals(3, strings.size());
        //Assertions.assertEquals(3, urlService.parseURL(urls).size());
    }

    @Test
    void whenParseValidUrlsWithoutHTTPS_thenReturnTrue() {
        ArrayList<String> urls = new ArrayList<>();
        urls.add("yandex.ru");
        urls.add("ya.ru");
        urls.add("mail.ru");

        Assertions.assertEquals(3, urlService.parseURL(urls).size());
    }


//Не знаю, как в коде решить проблему: для парсинга передаются некорректные urls. Я просто сделал printStackTrace
// Нужно посмотреть коды ошибок, возвращаемых при exception
//    @Test
//    void whenParseNotUrls_thenReturnException() {
//        ArrayList<String> urls = new ArrayList<>();
//        urls.add("123");
//        urls.add("abc");
//        urls.add("абв");
//
//        Assertions.assertEquals(3, urlService.parseURL(urls).size());
//    }

//    @Test
//    void whenEmptyUrlArray_thenReturnArrayOfSizeZero() {
//        ArrayList<String> urls = new ArrayList<>();
//        Assertions.assertEquals(0, urlService.parseURL(urls).size());
//    }

//    @Test
//    void whenParseNull_thenReturnArrayOfSizeZero() {
//        Assertions.assertEquals(0, urlService.parseURL(null).size());
//    }

    @Test
    void whenFourCorrectTexts_thenReturnHashMap() {
        ArrayList<String> texts = new ArrayList<>();
        texts.add("Один, два, три");
        texts.add("Один, четыре, пять");
        texts.add("Шесть, семь, восемь");
        texts.add("Девять, десять, одиннадцать");

        Assertions.assertEquals(11, urlService.createNotLematizedWordsMap(texts).size());
    }

    @Test
    void whenNullTexts_thenReturnEmptyHashMap() {
        Assertions.assertEquals(0, urlService.createNotLematizedWordsMap(null).size());
    }

    @Test
    void whenHashMapNonCyrillicWordsWithTwoElements_thenReturnArrayListWithTwoElements() {
        HashMap<String, Integer> nonCyrillicWordsMap = new HashMap<>();
        nonCyrillicWordsMap.put("Марс", 1);
        nonCyrillicWordsMap.put("Венера", 2);

        Assertions.assertEquals(2, urlService.creatingListOfNonCyrillicWords(nonCyrillicWordsMap).size());
    }

    @Test
    void whenCyrillic_thenReturnTrue() {
        Assertions.assertTrue(urlService.checkCyrillic("абв"));
    }

    @Test
    void whenNonCyrillic_thenReturnFalse() {
        Assertions.assertFalse(urlService.checkCyrillic("abc"));
    }

    @Test
    void whenCyrillicAndNonCyrillic_thenReturnFalse() {
        Assertions.assertFalse(urlService.checkCyrillic("абвabc"));
    }

    @Test
    void whenNumber_thenReturnFalse() {
        Assertions.assertFalse(urlService.checkCyrillic("1"));
    }

    @Test
    void whenEmptyString_thenReturnFalse() {
        Assertions.assertFalse(urlService.checkCyrillic(""));
    }

    @Test
    void whenNull_thenReturnFalse() {
        Assertions.assertFalse(urlService.checkCyrillic(null));
    }

    @Test
    void whenNotLematizedWordsTwoElements_thenReturnArrayListWordDtoWithTwoElements() {
        HashMap<String, Integer> notLematizedWordsMap = new HashMap<>();
        notLematizedWordsMap.put("ромашки", 3);
        notLematizedWordsMap.put("лютики", 5);

        Word romashka = new Word();
        romashka.setId(1);
        romashka.setWord("ромашка");
        romashka.setCode(123);
        romashka.setCodeParent(456);

        Set<Word> wordSet = new HashSet<>();
        wordSet.add(romashka);

        when(wordRepository.findByWord(any())).thenReturn(wordSet);
        when(wordRepository.findByCode(anyInt())).thenReturn(wordSet);

        Assertions.assertEquals(2, urlService.checkingLematizedForm(notLematizedWordsMap).size());
    }

    @Test
    void whenNotLematizedWordsThreeElements_thenReturnArrayListWordDtoWithTwoElements() {
        HashMap<String, Integer> notLematizedWordsMap = new HashMap<>();
        notLematizedWordsMap.put("ромашки", 3);
        notLematizedWordsMap.put("ромашек", 5);
        notLematizedWordsMap.put("ромашке", 5);

        Word romashka = new Word();
        romashka.setId(1);
        romashka.setWord("ромашка");
        romashka.setCode(123);
        romashka.setCodeParent(456);

        Set<Word> wordSet = new HashSet<>();
        wordSet.add(romashka);

        when(wordRepository.findByWord(any())).thenReturn(wordSet);
        when(wordRepository.findByCode(anyInt())).thenReturn(wordSet);

        Assertions.assertEquals(1, urlService.checkingLematizedForm(notLematizedWordsMap).size());
    }

    //Предусмотреть тест, где есть несколько слов в разных словоформах
}