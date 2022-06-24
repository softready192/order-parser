package com.order.parser;

import com.order.parser.exception.InvalidInputException;
import com.order.parser.parser.ParserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.Arrays.stream;

@Component
public class FileReader implements CommandLineRunner
 {
	private final ParserFactory parserFactory;

	@Autowired
	public FileReader(ParserFactory parserFactory) {
		this.parserFactory = parserFactory;
	}

	@Override
	public void run(String... args) {
		if(null == args || args.length == 0) {
			throw new InvalidInputException("Input Files not provided");
		}
		if (args.length != 2) {
			throw new InvalidInputException("Invalid number of arguments provided");
		}
		boolean containValidFiles = stream(args).allMatch(x -> x.endsWith("csv") || x.endsWith("json"));
		if(!containValidFiles) {
			throw new InvalidInputException("Invalid file names");
		}
		AtomicInteger idCounter = new AtomicInteger(1);
		stream(args).forEach(inputFile -> {
			File file = new File(inputFile);
			String fileType = inputFile.substring(inputFile.lastIndexOf(".") + 1);
			parserFactory.getParser(fileType).parse(file, idCounter);
		});
	}
}
