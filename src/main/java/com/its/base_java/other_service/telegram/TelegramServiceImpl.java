package com.its.base_java.other_service.telegram;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class TelegramServiceImpl implements TelegramService {
    private static final int MAX_RETRY = 3;
    private static final long RETRY_DELAY_MS = 2000;
    private static final OkHttpClient client = new OkHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${telegram.api.url}")
    private String telegramApiUrl;

    @Value("${telegram.api.chat_id}")
    private String chatId;

    @Async
    @Override
    public void sendMessageTelegram(String parseMode, String text) {
        int attempts = 0;
        boolean success = false;

        while (attempts < MAX_RETRY && !success) {
            attempts++;
            try {
                callTelegramApi(chatId, parseMode, text);
                success = true;
            } catch (Exception e) {
                if (attempts < MAX_RETRY) {
                    try {
                        Thread.sleep(RETRY_DELAY_MS);
                    } catch (InterruptedException ie) {
                    }
                }
            }
        }
        if (!success) {
            log.error("Gửi telegram thất bại hoàn toàn sau {} lần thử. Nội dung: {}", MAX_RETRY, text);
        }
    }

    private void callTelegramApi(String chatId, String parseMode, String text) {
        try {
            String payload = objectMapper.writeValueAsString(Map.of(
                    "chat_id", chatId,
                    "parse_mode", parseMode,
                    "text", text
            ));

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, payload);

            Request request = new Request.Builder()
                    .url(telegramApiUrl)
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .build();

            try (Response response = client.newCall(request).execute()) {
                int statusCode = response.code();
                String responseBody = response.body() != null ? response.body().string() : "No body";

                if (statusCode < 200 || statusCode >= 300) {
                    throw new RuntimeException("HTTP " + statusCode + " - " + responseBody);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Lỗi IO khi gọi Telegram API: " + e.getMessage(), e);
        }
    }
}
