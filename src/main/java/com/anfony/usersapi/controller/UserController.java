package com.anfony.usersapi.controller;

import com.anfony.usersapi.model.User;
import com.anfony.usersapi.repository.InMemoryUserRepo;
import com.anfony.usersapi.util.CryptoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final InMemoryUserRepo repo;
    private final CryptoService crypto;

    public UserController(InMemoryUserRepo repo, CryptoService crypto) {
        this.repo = repo;
        this.crypto = crypto;
    }

    @GetMapping
    public List<User> getAllUsers(
            @RequestParam(required = false) String sortedBy,
            @RequestParam(required = false) String filter
    ) {
        List<User> list = new ArrayList<>(repo.findAll());

        if (filter != null && !filter.isBlank()) {
            String[] parts = filter.split("\\+", 3);
            if (parts.length != 3) {
                throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "filter mal formado; usa campo+op+valor");
            }
            String field = parts[0].trim().toLowerCase();
            String op = parts[1].trim().toLowerCase();
            String value = parts[2].trim();

            Function<User, String> extractor = switch (field) {
                case "email" -> u -> nn(u.getEmail());
                case "id" -> u -> u.getId() == null ? "" : u.getId().toString();
                case "name" -> u -> nn(u.getName());
                case "phone" -> u -> nn(u.getPhone());
                case "tax_id" -> u -> nn(u.getTaxId());
                case "created_at" -> u -> u.getCreatedAt() == null ? "" : formatCreatedAt(u);
                default -> throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "campo de filter inválido");
            };

            String v = value.toLowerCase();
            list = list.stream().filter(u -> {
                String f = extractor.apply(u).toLowerCase();
                return switch (op) {
                    case "co" -> f.contains(v);
                    case "eq" -> f.equals(v);
                    case "sw" -> f.startsWith(v);
                    case "ew" -> f.endsWith(v);
                    default -> throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "operador de filter inválido (co|eq|sw|ew)");
                };
            }).collect(Collectors.toList());
        }
        if (sortedBy != null && !sortedBy.isBlank()) {
            Comparator<User> cmp = switch (sortedBy.toLowerCase()) {
                case "email" -> Comparator.comparing(u -> nn(u.getEmail()), String.CASE_INSENSITIVE_ORDER);
                case "id" -> Comparator.comparing(u -> u.getId() == null ? "" : u.getId().toString(), String.CASE_INSENSITIVE_ORDER);
                case "name" -> Comparator.comparing(u -> nn(u.getName()), String.CASE_INSENSITIVE_ORDER);
                case "phone" -> Comparator.comparing(u -> nn(u.getPhone()), String.CASE_INSENSITIVE_ORDER);
                case "tax_id" -> Comparator.comparing(u -> nn(u.getTaxId()), String.CASE_INSENSITIVE_ORDER);
                case "created_at" -> Comparator.comparing(u -> u.getCreatedAt() == null ? "" : formatCreatedAt(u), String.CASE_INSENSITIVE_ORDER);
                default -> throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "sortedBy inválido");
            };
            list.sort(cmp);
        }

        return list;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable UUID id) {
        return repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "usuario no encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody Map<String, Object> body) {
        User user = new User();

        user.setEmail(getStr(body, "email"));
        user.setName(getStr(body, "name"));
        user.setPhone(getStr(body, "phone"));
        user.setTaxId(getStr(body, "taxId"));

        if (user.getTaxId() != null && !user.getTaxId().isBlank()) {
            boolean exists = repo.findAll().stream()
                    .anyMatch(u -> u.getTaxId() != null && u.getTaxId().equalsIgnoreCase(user.getTaxId()));
            if (exists) throw new ResponseStatusException(HttpStatus.CONFLICT, "tax_id ya existe");
        }

        user.setId(UUID.randomUUID());

        user.setCreatedAt(ZonedDateTime.now(ZoneId.of("Indian/Antananarivo")).toLocalDateTime());

        String plain = getStr(body, "password");
        if (plain != null && !plain.isBlank()) {
            user.setPasswordEnc(crypto.encrypt(plain));
        }


        repo.save(user);
        return user;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        var existed = repo.findById(id).isPresent();
        if (!existed) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "usuario no encontrado");
        repo.deleteById(id);
    }

    private static String nn(String s) { return s == null ? "" : s; }

    private static String formatCreatedAt(User u) {
        var dt = u.getCreatedAt();
        if (dt == null) return "";
        return String.format("%02d-%02d-%04d %02d:%02d",
                dt.getDayOfMonth(), dt.getMonthValue(), dt.getYear(),
                dt.getHour(), dt.getMinute());
    }

    private static String getStr(Map<String, Object> body, String key) {
        Object v = body.get(key);
        return v == null ? null : String.valueOf(v);
    }
}
