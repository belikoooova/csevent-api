package com.example.cseventapi.service.parsing;

import com.example.cseventapi.dto.ParsingRequest;
import com.example.cseventapi.dto.ParsingResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Service
public class MetroParser implements Parser {
    private static final String SPLITTER = "%20";
    private static final String USER_AGENT = "Chrome/4.0.249.0 Safari/532.5";
    private static final String REFERRER = "http://www.google.com";
    private static final String SHOP = "METRO";
    private static final String DIV = "div.product-unit-prices__actual-wrapper";
    private static final String BEGIN = "https://online.metro-cc.ru/search?q=";
    private static final String RUB = "от %s руб";
    private static final String NO_PRICE = "Не нашлось";

    @Override
    public ParsingResponse parse(ParsingRequest request) {
        String url = createLink(request);
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .referrer(REFERRER)
                    .get();

            Element element = doc.select(DIV)
                    .first()
                    .firstElementChild()
                    .firstElementChild()
                    .firstElementChild();

            return ParsingResponse.builder()
                    .shop(SHOP)
                    .price(RUB.formatted(element.childNode(0)))
                    .url(url)
                    .founded(true)
                    .build();
        } catch (Exception e) {
             return ParsingResponse.builder()
                    .shop(SHOP)
                    .price(NO_PRICE)
                    .url(url)
                    .founded(false)
                    .build();
        }
    }

    private String createLink(ParsingRequest request) {
        return BEGIN
                + request.getName().replace(" ", SPLITTER)
                + SPLITTER
                + "1"
                + request.getUnit().replace(" ", SPLITTER);
    }
}
