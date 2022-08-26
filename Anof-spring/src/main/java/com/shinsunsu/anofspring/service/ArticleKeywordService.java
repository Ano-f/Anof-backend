package com.shinsunsu.anofspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleKeywordService {
    static public class Morpheme {
        final String text;
        final String type;
        Integer count;

        public Morpheme(String text, String type, Integer count) {
            this.text = text;
            this.type = type;
            this.count = count;
        }
    }

    static public class NameEntity implements Comparable<NameEntity> {
        final String text;
        final String type;
        Integer count;

        public NameEntity(String text, String type, Integer count) {
            this.text = text;
            this.type = type;
            this.count = count;
        }

        @Override
        public int compareTo(NameEntity o) {
            return this.count.compareTo(o.count);
        }
    }

}
