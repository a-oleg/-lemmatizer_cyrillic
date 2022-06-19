package com.github.a_oleg.controller;

import com.github.a_oleg.service.URLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class URLController {
    private final URLService urlService;
    @Autowired
    public URLController(URLService urlService) {
        this.urlService = urlService;
    }

    @RequestMapping(value = "/downloadResult", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public void parseURL(@RequestParam(name = "URL", required = true) String url, Model model) {
        if (url == "") {
            //Написать код, отправляющий на фронт сообщение, что никаких url не было указано
        } else {
            String[] arrayURL = url.split("\r\n");
            ArrayList<String> urlsForCountLematizedWords = new ArrayList<>();
            for (String arrayElement : arrayURL) {
                urlsForCountLematizedWords.add(arrayElement);
            }
            HashMap<String, ArrayList> LematizedCyrillicAndNonLematizedNonCyrillicWords
                    = urlService.countLematizedWordsByUrls(urlsForCountLematizedWords);
        }
    }
}
