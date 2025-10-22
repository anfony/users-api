package com.anfony.usersapi.config;

import com.anfony.usersapi.model.Address;
import com.anfony.usersapi.model.User;
import com.anfony.usersapi.repository.InMemoryUserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(InMemoryUserRepo repo) {
        return args -> {
            if (repo.findAll().isEmpty()) {
                repo.save(new User(
                        UUID.randomUUID(),
                        "Anfony_jose@hotmail.com",
                        "Anfony Laguna",
                        "+52 55 5618220645",
                        "7c4a8d09ca3762af61e59520943dc26494f8941b",
                        "ALGA950509",
                        LocalDateTime.now(),
                        Arrays.asList(
                            new Address(1L, "workaddress", "Av. Insurgentes Sur No. 123", "MX"),
                            new Address(2L, "homeaddress", "Calle Reforma No. 456", "MX")
                        )
                ));

                repo.save(new User(
                        UUID.randomUUID(),
                        "ricardo.osorio@chakray.com",
                        "Ricardo Osorio",
                        "+52 55 87963214",
                        "7c4a8d09ca3762af61e59520943dc26494f8941b",
                        "OSOR900315",
                        LocalDateTime.now(),
                        Arrays.asList(
                            new Address(3L, "workaddress", "Calle Londres No. 789", "UK"),
                            new Address(4L, "homeaddress", "Av. Revolución No. 101", "MX")
                        )
                ));

                repo.save(new User(
                        UUID.randomUUID(),
                        "karina.jimarez@chakray.com",
                        "Karina Jimarez",
                        "5548760912",
                        "7c4a8d09ca3762af61e59520943dc26494f8941b",
                        "JIMK920720",
                        LocalDateTime.now(),
                        null
                ));

                System.out.println("✅ Usuarios iniciales guardados satisfactoriamente.");
            }
        };
    }
}
