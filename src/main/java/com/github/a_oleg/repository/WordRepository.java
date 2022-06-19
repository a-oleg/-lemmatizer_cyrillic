package com.github.a_oleg.repository;

import com.github.a_oleg.entity.Word;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

@Repository
public interface WordRepository extends JpaRepository<Word, Integer> {
    Set<Word> findById(int id);
    Set<Word> findByWord(String word);
    Set<Word> findByCode(int code);
    Set<Word> findByCodeParent(int codeParent);
}
