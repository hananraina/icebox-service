package com.icebox.service.webhooks.repository;

import com.icebox.service.webhooks.domain.UserMirror;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserMirrorRepository extends CrudRepository<UserMirror, UUID> {
    @Override
    void deleteById(UUID uuid);
}
