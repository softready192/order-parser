package com.order.parser.parser;

import com.order.parser.exception.InvalidInputException;
import com.order.parser.parser.impl.CsvParser;
import com.order.parser.parser.impl.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParserFactory {

    private final CsvParser csvParser;

    private final JsonParser jsonParser;

    @Autowired
    public ParserFactory(CsvParser csvParser, JsonParser jsonParser) {
        this.csvParser = csvParser;
        this.jsonParser = jsonParser;
    }

    public Parser getParser(String fileType) {
        if("csv".equalsIgnoreCase(fileType)) {
            return csvParser;
        } else if("json".equalsIgnoreCase(fileType)) {
            return jsonParser;
        }
        throw new InvalidInputException("Invalid file type.");
    }
}
