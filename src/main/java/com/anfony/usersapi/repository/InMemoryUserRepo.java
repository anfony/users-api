package com.anfony.usersapi.repository;

import com.anfony.usersapi.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class InMemoryUserRepo {

    private final Map<UUID, User> users = new ConcurrentHashMap<>();

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(users.get(id));
    }

    public Optional<User> findByTaxId(String taxId) {
        return users.values().stream()
                .filter(u -> u.getTaxId().equalsIgnoreCase(taxId))
                .findFirst();
    }

    public Optional<User> findByEmail(String email) {
        return users.values().stream()
                .filter(u -> u.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
        }
        users.put(user.getId(), user);
        return user;
    }

    public void deleteById(UUID id) {
        users.remove(id);
    }

    public void clear() {
        users.clear();
    }
}
