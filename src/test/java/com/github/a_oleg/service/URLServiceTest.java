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
    void whenCyrillic_thenReturnTrue() {
        Assertions.assertTrue(urlService.checkCyrillic("абв"));
    }

    @Test
    void whenNonCyrillic_thenReturnFalse() {
        Assertions.assertFalse(urlService.checkCyrillic("abc"));
    }

//    @Test
//    void whenCyrillicAndNonCyrillic_thenReturnFalse() {
//        Assertions.assertFalse(urlService.checkCyrillic("абвabc"));
//    }

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
    void whenFourCorrectTexts_thenReturnHashMap() {
        ArrayList<String> texts = new ArrayList<>();
        texts.add("Славься, Отечество наше свободное,");
        texts.add("Братских народов союз вековой,");
        texts.add("Предками данная мудрость народная!");
        texts.add("Славься, страна! Мы гордимся тобой!");

        Assertions.assertEquals(16, urlService.createNotLematizedWordsMap(texts).size());
    }

    @Test
    void whenNullTexts_thenReturnEmptyHashMap() {
        Assertions.assertEquals(0, urlService.createNotLematizedWordsMap(null).size());
    }

    @Test
    void whenHashMapNonCyrillicWordsWithTwoElements_thenReturnArrayListWithTwoElements() {
        HashMap<String, Integer> nonCyrillicWordsMap = new HashMap<>();
        nonCyrillicWordsMap.put("Олег", 1);
        nonCyrillicWordsMap.put("Семён", 2);

        Assertions.assertEquals(2, urlService.creatingListOfNonCyrillicWords(nonCyrillicWordsMap).size());
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

}