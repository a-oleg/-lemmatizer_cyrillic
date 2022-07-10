package com.github.a_oleg.controllers;

import com.github.a_oleg.dto.WordDto;
import com.github.a_oleg.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/wordStatistics")
public class WordStatisticsController {
    private final URLService urlService;
    @Autowired
    public WordStatisticsController(URLService urlService) {
        this.urlService = urlService;
    }

    @GetMapping
    public ArrayList<WordDto> getStatistics(@RequestParam(name = "URL", required = true) String url, Model model) {
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
            return lematizedCyrillicAndNonLematizedNonCyrillicWords;
        }
    }

}
