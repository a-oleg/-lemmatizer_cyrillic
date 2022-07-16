package com.github.a_oleg.controllers;

import com.github.a_oleg.dto.WordDto;
import com.github.a_oleg.exceptions.ServerException;
import com.github.a_oleg.service.URLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class URLController {
    Logger logger = LoggerFactory.getLogger(URLController.class);
    private final URLService urlService;
    @Autowired
    public URLController(URLService urlService) {
        this.urlService = urlService;
    }

    @RequestMapping(value = "/downloadResult", produces = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseBody
    public ArrayList<WordDto> parseURL(@RequestParam(name = "URL", required = true) String url) throws ServerException {
        logger.info("First log");
        System.out.println(url);
        if (url == "") {
            return null;
        } else {
            String[] arrayURL = url.split("\r\n");
            ArrayList<String> urlsForCountLematizedWords = new ArrayList<>();
            for (String arrayElement : arrayURL) {
                urlsForCountLematizedWords.add(arrayElement);
            }
            ArrayList<WordDto> LematizedCyrillicAndNonLematizedNonCyrillicWords
                    = urlService.getLematizedWordsByUrls(urlsForCountLematizedWords);
            return LematizedCyrillicAndNonLematizedNonCyrillicWords;
        }
    }
}
