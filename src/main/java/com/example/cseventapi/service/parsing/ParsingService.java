package com.example.cseventapi.service.parsing;

import com.example.cseventapi.dto.ParsingResponse;

import java.util.List;
import java.util.UUID;

public interface ParsingService {
    List<ParsingResponse> getShops(UUID productId);
}
