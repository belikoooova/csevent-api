package com.example.cseventapi.service.parsing;

import com.example.cseventapi.dto.ParsingRequest;
import com.example.cseventapi.dto.ParsingResponse;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

@Service
public class LentaParser implements Parser {
    private static final String SPLITTER = "%20";
    private static final String USER_AGENT = "Chrome/4.0.249.0 Safari/532.5";
    private static final String REFERRER = "http://www.google.com";
    private static final String SHOP = "Перекрёсток";
    private static final String DIV = "div.price-new";
    private static final String BEGIN = "https://www.perekrestok.ru/cat/search?search=";
    private static final String RUB = "от %s руб";
    private static final String NO_PRICE = "Не нашлось";
    private static final int PRICE_INDEX = 4;

    @Override
    public ParsingResponse parse(ParsingRequest request){
        String url = createLink(request);
        try {
            Document doc = Jsoup.connect(url)
                    .userAgent(USER_AGENT)
                    .referrer(REFERRER)
                    .get();

            String price = doc.select(DIV)
                    .first()
                    .text()
                    .split(",")[0]
                    .substring(PRICE_INDEX);


            return ParsingResponse.builder()
                    .shop(SHOP)
                    .price(RUB.formatted(price))
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
