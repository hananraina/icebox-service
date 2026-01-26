package com.icebox.service.webhooks.controller;

import com.icebox.service.webhooks.service.UserSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.JsonNode;

import java.util.UUID;

@RestController
@RequestMapping("/webhooks/fusionauth")
public class FusionAuthWebhookController {
    Logger logger = LoggerFactory.getLogger(FusionAuthWebhookController.class);

    @Autowired
    private UserSyncService userSyncService;

    @PostMapping
    public ResponseEntity<Void> handleWebhook(
            @RequestHeader("X-Webhook-Secret") String secret,
            @RequestBody JsonNode payload) {

        // 1. Simple Security Check
        if (!"secret".equals(secret)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // 2. Determine Event Type
        String type = payload.at("/event/type").textValue();
        logger.info("WebHook Event: {}", type);
        switch (type) {
            case "user.create":
            case "user.update":
            case "user.registration.create":
                userSyncService.syncUser(payload);
                break;
            case "user.delete":
                UUID userId = UUID.fromString(payload.at("/event/user/id").asText());
                userSyncService.deleteUser(userId);
                break;
            default:
                // Ignore other events like "jwt.refresh"
                break;
        }

        return ResponseEntity.ok().build();
    }
}
