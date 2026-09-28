package com.its.base_java.other_service.sse;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class SseTxPublisher {

    private static final long TIMEOUT_MS = Duration.ofHours(6).toMillis();
    private final ObjectMapper om = new ObjectMapper();

    private final ConcurrentMap<String, CopyOnWriteArrayList<SseEmitter>> byTx = new ConcurrentHashMap<>();

    public SseEmitter subscribe(String txId) {
        SseEmitter emitter = new SseEmitter(TIMEOUT_MS);
        byTx.computeIfAbsent(txId, k -> new CopyOnWriteArrayList<>()).add(emitter);

        emitter.onCompletion(() -> remove(txId, emitter));
        emitter.onTimeout(() -> remove(txId, emitter));
        emitter.onError((ex) -> remove(txId, emitter));

        try {
            emitter.send(SseEmitter.event()
                    .name("connected")
                    .data("ok")
                    .reconnectTime(3000));
        } catch (IOException e) {
            remove(txId, emitter);
        }
        return emitter;
    }

    public void publish(String txId, Object payload) {
        List<SseEmitter> list = byTx.get(txId);
        if (list == null || list.isEmpty()) return;

        String json;
        try {
            json = om.writeValueAsString(payload);
        } catch (Exception e) {
            log.error("Serialize SSE payload failed", e);
            return;
        }

        for (SseEmitter emitter : list) {
            try {
                emitter.send(SseEmitter.event()
                        .name("payment_update")
                        .data(json, MediaType.APPLICATION_JSON));
            } catch (Exception ex) {
                remove(txId, emitter);
            }
        }
    }

    private void remove(String txId, SseEmitter emitter) {
        List<SseEmitter> list = byTx.get(txId);
        if (list != null) {
            list.remove(emitter);
            if (list.isEmpty()) byTx.remove(txId);
        }
    }
}
