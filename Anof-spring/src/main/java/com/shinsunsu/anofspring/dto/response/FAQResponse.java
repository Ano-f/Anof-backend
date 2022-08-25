package com.shinsunsu.anofspring.dto.response;

import com.shinsunsu.anofspring.domain.FAQ;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FAQResponse {
    String title;
    String content;

    public FAQResponse(FAQ faq) {
        this.title = faq.getTitle();
        this.content = faq.getContent();
    }
}
