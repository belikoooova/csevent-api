package com.example.cseventapi.service.parsing;

import com.example.cseventapi.dto.ParsingRequest;
import com.example.cseventapi.dto.ParsingResponse;
import com.example.cseventapi.entity.Product;
import com.example.cseventapi.repository.ProductDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParsingServiceImpl implements ParsingService {
    private final ProductDao productDao;
    private final List<Parser> parsers;

    @Override
    public List<ParsingResponse> getShops(UUID productId) {
        Product product = productDao.findById(productId).get();
        ParsingRequest request = ParsingRequest.builder()
                .unit(product.getUnit())
                .name(product.getName())
                .build();

        List<ParsingResponse> responses = new ArrayList<>();

        for (Parser parser: parsers) {
            responses.add(parser.parse(request));
        }

        return responses;
    }
}
