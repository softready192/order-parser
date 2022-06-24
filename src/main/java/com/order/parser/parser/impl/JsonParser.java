package com.order.parser.parser.impl;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.order.parser.parser.Parser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.order.parser.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class JsonParser implements Parser {
	int counter = 0;

	@Override
	public void parse(File file, AtomicInteger id) {
		ExecutorService executor = Executors.newCachedThreadPool();
		JSONParser jsonParser = new JSONParser();
		try {
			FileReader reader = new FileReader(file);

			Object obj = jsonParser.parse(reader);

			JSONArray infoList = (JSONArray) obj;

			infoList.forEach(order -> {

				executor.execute(
						new Converter(null, (JSONObject) order, file.getName(), counter++, id.getAndIncrement()));
			});

		} catch (IOException | ParseException e) {
			e.printStackTrace();

		}
	}
}
