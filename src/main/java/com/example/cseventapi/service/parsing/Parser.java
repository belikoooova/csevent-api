package com.example.cseventapi.service.parsing;

import com.example.cseventapi.dto.ParsingRequest;
import com.example.cseventapi.dto.ParsingResponse;

public interface Parser {
    ParsingResponse parse(ParsingRequest request);
}
