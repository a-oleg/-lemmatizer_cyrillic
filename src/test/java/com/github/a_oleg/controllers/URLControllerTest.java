package com.github.a_oleg.controllers;

import com.github.a_oleg.dto.WordDto;
import com.github.a_oleg.exceptions.ClientException;
import com.github.a_oleg.exceptions.ServerException;
import com.github.a_oleg.service.URLService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class URLControllerTest {
    @Mock
    URLService urlService;

    @InjectMocks
    URLController urlController;

    @Test
    void whenOneUrlParse_thenReturnListOfWords() throws ServerException, ClientException {
        ArrayList<WordDto> text = new ArrayList<>();
        WordDto firstWordDto = new WordDto(1, "Первый", 1, 1, 1, true);
        text.add(firstWordDto);

        when(urlService.getLematizedWordsByUrls(any())).thenReturn(text);

        Assertions.assertEquals(1, urlController.parseURL("https://google.com/").size());
    }

    @Test
    void whenTwoUrlParse_thenReturnListOfWords() throws ServerException, ClientException {
        ArrayList<WordDto> text = new ArrayList<>();
        WordDto firstWordDto = new WordDto(1, "Первый", 1, 1, 1, true);
        WordDto twoWordDto = new WordDto(2, "Two", 2, 2, 2, false);
        text.add(firstWordDto);
        text.add(twoWordDto);

        when(urlService.getLematizedWordsByUrls(any())).thenReturn(text);

        Assertions.assertEquals(2, urlController.parseURL("https://yandex.ru/,https://google.com/").size());
    }


}
