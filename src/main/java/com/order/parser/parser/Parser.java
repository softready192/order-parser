package com.order.parser.parser;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

public interface Parser {

    void parse(File file, AtomicInteger id);
}
