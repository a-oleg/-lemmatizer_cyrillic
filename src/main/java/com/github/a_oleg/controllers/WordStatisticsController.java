package com.github.a_oleg.controllers;

import com.github.a_oleg.dto.WordDto;
import com.github.a_oleg.exceptions.ServerException;
import com.github.a_oleg.service.URLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/wordStatistics")
public class WordStatisticsController {
    Logger logger = LoggerFactory.getLogger(URLController.class);
    private final URLService urlService;
    @Autowired
    public WordStatisticsController(URLService urlService) {
        this.urlService = urlService;
    }

    @GetMapping
    public ArrayList<WordDto> getStatistics(@RequestParam(name = "URL", required = true) String url) throws ServerException {
        logger.info("Successful launch of the application");
        if (url == "") {
            return null;
        } else {
            String[] arrayURL = url.split("\r\n");
            ArrayList<String> urlsForCountLematizedWords = new ArrayList<>();
            for (String arrayElement : arrayURL) {
                urlsForCountLematizedWords.add(arrayElement);
            }
            ArrayList<WordDto> lematizedCyrillicAndNonLematizedNonCyrillicWords
                    = urlService.getLematizedWordsByUrls(urlsForCountLematizedWords);
            logger.info("Successful completion of the application and data transfer to the REST");
            return lematizedCyrillicAndNonLematizedNonCyrillicWords;
        }
    }

}
