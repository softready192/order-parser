package com.order.parser.parser.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.order.parser.converter.Converter;
import com.order.parser.parser.Parser;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CsvParser implements Parser {

    @Override
    public void parse(File file, AtomicInteger id) {
        ExecutorService executor = Executors.newCachedThreadPool();
        try (CSVReader reader = new CSVReader(new FileReader(file))) {
            String[] lineInArray;
            int lineNo = 0;
            while ((lineInArray = reader.readNext()) != null) {
                executor.execute(new Converter(lineInArray, null, file.getName(), lineNo++, id.getAndIncrement()));
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

}
