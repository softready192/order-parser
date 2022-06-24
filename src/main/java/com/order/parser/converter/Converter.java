package com.order.parser.converter;


import static com.order.parser.util.Constants.AMOUNT_ERROR_MESSAGE;
import static com.order.parser.util.Constants.OK;
import static com.order.parser.util.Constants.ORDER_ID_ERROR_MESSAGE;
import static org.apache.commons.lang3.StringUtils.isEmpty;

import org.apache.commons.lang3.ArrayUtils;
import org.json.simple.JSONObject;

import com.order.parser.model.OrderInfo;


public class Converter implements Runnable {
    private final Integer id;
    private final String[] orderRecord;
    private final String fileName;
    private final Integer lineNo;
    private final JSONObject jsonObject;

    public Converter(String[] orderRecord, JSONObject jObject, String fileName, Integer lineNo, Integer id) {
        this.id = id;
        this.orderRecord = orderRecord;
        this.fileName = fileName;
        this.lineNo = lineNo;
        this.jsonObject = jObject;
    }

    @Override
    public void run() {
        OrderInfo orderInfo = new OrderInfo();
        if (ArrayUtils.isNotEmpty(orderRecord)) {
            orderInfo = getOrderInfo();
        } else if (jsonObject != null && !jsonObject.isEmpty()) {
            orderInfo = getOrderInfoFromJsonObject();
        }
        orderInfo.setId(id);
        orderInfo.setFileName(fileName);
        orderInfo.setLine(lineNo);
        System.out.println(orderInfo);

    }

    private OrderInfo getOrderInfoFromJsonObject() {
        OrderInfo orderInfo = new OrderInfo();
        StringBuilder result = new StringBuilder();
        try {
            orderInfo.setOrderId(Integer.parseInt(jsonObject.get("orderId").toString()));
        } catch (NumberFormatException e) {
            result.append(ORDER_ID_ERROR_MESSAGE);
        }
        try {
            orderInfo.setAmount(Double.parseDouble(jsonObject.get("amount").toString()));
        } catch (NumberFormatException e) {
            result.append(AMOUNT_ERROR_MESSAGE);
        }
        if (isEmpty(result.toString())) {
            orderInfo.setResult(OK);
        }
        orderInfo.setCurrency(jsonObject.get("currency").toString());
        orderInfo.setComment(jsonObject.get("comment").toString());
        return orderInfo;
    }

    private OrderInfo getOrderInfo() {
        OrderInfo orderInfo = new OrderInfo();
        StringBuilder result = new StringBuilder();
        try {
            orderInfo.setOrderId(Integer.parseInt(orderRecord[0]));
        } catch (NumberFormatException e) {
            result.append(ORDER_ID_ERROR_MESSAGE);
        }

        try {
            orderInfo.setAmount(Double.parseDouble(orderRecord[1]));
        } catch (NumberFormatException e) {
            result.append(AMOUNT_ERROR_MESSAGE);
        }
        if (isEmpty(result.toString())) {
            orderInfo.setResult(OK);
        }

        orderInfo.setCurrency(orderRecord[2]);
        orderInfo.setComment(orderRecord[3]);
        return orderInfo;

    }


}
