package com.icebox.service.webhooks.service;

import com.icebox.service.webhooks.domain.UserMirror;
import com.icebox.service.webhooks.repository.UserMirrorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserSyncService {

    @Autowired
    private UserMirrorRepository repository;

    Logger log = LoggerFactory.getLogger(UserSyncService.class);

    @Transactional
    public void syncUser(JsonNode webhookPayload) {
        JsonNode userNode = webhookPayload.at("/event/user");
        UUID userId = UUID.fromString(userNode.get("id").textValue());

        UserMirror mirror = repository.findById(userId).orElse(new UserMirror());
        mirror.setId(userId);
//        mirror.setUsername(userNode.get("username").textValue());
        mirror.setEmail(userNode.get("email").textValue());
        mirror.setFullName(userNode.get("firstName").textValue() + " " + userNode.get("lastName").textValue());

        // Extract roles from registrations
        List<String> roleList = new ArrayList<>();
        JsonNode registrations = userNode.get("registrations");
        if (registrations != null && registrations.isArray()) {
            for (JsonNode reg : registrations) {
                // You can filter by your specific Application ID if needed
                JsonNode rolesNode = reg.get("roles");
                if (rolesNode != null && rolesNode.isArray()) {
                    rolesNode.forEach(role -> roleList.add(role.textValue()));
                }
            }
        }
        mirror.setRoles(roleList);
        log.info("syncUser: {} {}", mirror.getId(), mirror.getEmail());
        repository.save(mirror);
    }

    @Transactional
    public void deleteUser(UUID userId) {
        repository.deleteById(userId);
    }
}
