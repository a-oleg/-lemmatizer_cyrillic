package com.github.a_oleg.converter;

import com.github.a_oleg.dto.WordDto;
import com.github.a_oleg.entity.Word;
import org.springframework.stereotype.Component;

@Component
public class WordToWordDtoConverter {
    public static WordDto convert(Word word, int count, boolean cyrillic) {
        WordDto targetWord = new WordDto();

        targetWord.setId(word.getId());
        targetWord.setWord(word.getWord());
        targetWord.setCode(word.getCode());
        targetWord.setCodeParent(word.getCodeParent());
        targetWord.setCount(count);
        targetWord.setCyrillic(cyrillic);

        return targetWord;
    }

}
