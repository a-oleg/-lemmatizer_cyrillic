package com.github.a_oleg.service;

import com.github.a_oleg.repository.WordRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

}