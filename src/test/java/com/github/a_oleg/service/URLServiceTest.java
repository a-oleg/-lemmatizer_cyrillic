package com.github.a_oleg.service;

import com.github.a_oleg.dto.WordDto;
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
    }

    @Test
    void whenParseValidUrlsWithoutHTTPS_thenReturnTrue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<String> urls = new ArrayList<>();
        urls.add("yandex.ru");
        urls.add("ya.ru");
        urls.add("mail.ru");

        Method method = URLService.class.getDeclaredMethod("parseURL", ArrayList.class);
        method.setAccessible(true);
        ArrayList<String> strings = (ArrayList<String>)method.invoke(urlService, urls);
        Assertions.assertEquals(3, strings.size());
    }


    @Test
    void whenParseNotUrls_thenReturnException() throws NoSuchMethodException {
        ArrayList<String> urls = new ArrayList<>();
        urls.add("123");
        urls.add("abc");
        urls.add("абв");

        Method method = URLService.class.getDeclaredMethod("parseURL", ArrayList.class);
        method.setAccessible(true);
        Exception thrown = Assertions.assertThrows(Exception.class, () -> {
            method.invoke(urlService, urls);
        });
        Assertions.assertTrue(((Exception)thrown.getCause()).getMessage().contains("Invalid url parsing:"));
    }

    @Test
    void whenEmptyUrlArray_thenReturnArrayOfSizeZero() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<String> urls = new ArrayList<>();

        Method method = URLService.class.getDeclaredMethod("parseURL", ArrayList.class);
        method.setAccessible(true);

        ArrayList<String> strings = (ArrayList<String>)method.invoke(urlService, urls);
        Assertions.assertEquals(0, strings.size());
    }

    @Test
    void whenFourCorrectTexts_thenReturnHashMap() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<String> texts = new ArrayList<>();
        texts.add("Один, два, три");
        texts.add("Один, четыре, пять");
        texts.add("Шесть, семь, восемь");
        texts.add("Девять, десять, одиннадцать");

        Method method = URLService.class.getDeclaredMethod("createNotLematizedWordsMap", ArrayList.class);
        method.setAccessible(true);
        HashMap<String, Integer> pairStringCount = (HashMap)method.invoke(urlService, texts);
        //Одиннадцать элементов, т.к. слово "Один" повторяется два раза
        Assertions.assertEquals(11, pairStringCount.size());
    }

    @Test
    void whenNullTexts_thenReturnEmptyHashMap() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = URLService.class.getDeclaredMethod("createNotLematizedWordsMap", ArrayList.class);
        ArrayList arrayList = null;
        method.setAccessible(true);
        HashMap<String, Integer> pairStringCount = (HashMap)method.invoke(urlService, arrayList);

        Assertions.assertEquals(0, pairStringCount.size());
    }

    @Test
    void whenCyrillic_thenReturnTrue() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = URLService.class.getDeclaredMethod("checkCyrillic", String.class);
        method.setAccessible(true);
        boolean booleanResult = (boolean)method.invoke(urlService, "абв");
        Assertions.assertTrue(booleanResult);
    }

    @Test
    void whenNonCyrillic_thenReturnFalse() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = URLService.class.getDeclaredMethod("checkCyrillic", String.class);
        method.setAccessible(true);
        boolean booleanResult = (boolean)method.invoke(urlService,"abc");
        Assertions.assertFalse(booleanResult);
    }

    @Test
    void whenCyrillicAndNonCyrillic_thenReturnFalse() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = URLService.class.getDeclaredMethod("checkCyrillic", String.class);
        method.setAccessible(true);
        boolean booleanResult = (boolean)method.invoke(urlService,"абвabc");
        Assertions.assertFalse(booleanResult);
    }

    @Test
    void whenNumber_thenReturnFalse() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = URLService.class.getDeclaredMethod("checkCyrillic", String.class);
        method.setAccessible(true);
        boolean booleanResult = (boolean)method.invoke(urlService,"1");
        Assertions.assertFalse(booleanResult);
    }

    @Test
    void whenEmptyString_thenReturnFalse() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = URLService.class.getDeclaredMethod("checkCyrillic", String.class);
        method.setAccessible(true);
        boolean booleanResult = (boolean)method.invoke(urlService,"");
        Assertions.assertFalse(booleanResult);
    }

    @Test
    void whenNull_thenReturnFalse() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = URLService.class.getDeclaredMethod("checkCyrillic", String.class);
        method.setAccessible(true);
        String testNull = null;
        boolean booleanResult = (boolean)method.invoke(urlService, testNull);
        Assertions.assertFalse(booleanResult);
    }

    @Test
    void whenNotLematizedWordsTwoElements_thenReturnArrayListWordDtoWithTwoElements() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
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

        Method method = URLService.class.getDeclaredMethod("creatingListOfNonCyrillicWords", HashMap.class);
        method.setAccessible(true);

        ArrayList<WordDto> arrayWordDto = (ArrayList)method.invoke(urlService, notLematizedWordsMap);

        Assertions.assertEquals(2, arrayWordDto.size());
    }

        @Test
    void whenNotLematizedWordsThreeElements_thenReturnArrayListWordDtoWithTwoElements() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HashMap<String, Integer> notLematizedWordsMap = new HashMap<>();
        notLematizedWordsMap.put("ромашка", 3);

        Word romashka = new Word();
        romashka.setId(1);
        romashka.setWord("ромашки");
        romashka.setCode(123);
        romashka.setCodeParent(456);

        Set<Word> wordSet = new HashSet<>();
        wordSet.add(romashka);

        Method method = URLService.class.getDeclaredMethod("creatingListOfNonCyrillicWords", HashMap.class);
        method.setAccessible(true);

        ArrayList<WordDto> arrayWordDto = (ArrayList)method.invoke(urlService, notLematizedWordsMap);
        Assertions.assertEquals(1, arrayWordDto.size());
    }

    @Test
    void whenHashMapNonCyrillicWordsWithTwoElements_thenReturnArrayListWithTwoElements() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HashMap<String, Integer> nonCyrillicWordsMap = new HashMap<>();
        nonCyrillicWordsMap.put("Марс", 1);
        nonCyrillicWordsMap.put("Венера", 2);

        Method method = URLService.class.getDeclaredMethod("creatingListOfNonCyrillicWords", HashMap.class);
        method.setAccessible(true);
        ArrayList<WordDto> arrayWordDto = (ArrayList)method.invoke(urlService, nonCyrillicWordsMap);

        Assertions.assertEquals(2, arrayWordDto.size());
    }
}