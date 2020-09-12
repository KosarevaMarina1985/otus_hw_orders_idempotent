package com.kosmar.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static lombok.AccessLevel.PACKAGE;

@Service
@AllArgsConstructor(access = PACKAGE)
// bad implementation, just for homework, principle is more important for this work
public class UserService {

    private final UserRepository repository;

    UserFront save(String firstName, String lastName, String email) {
        return toUserFront(repository.save(UserModel.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email).build()
        ));
    }

    List<UserFront> findAll() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(this::toUserFront)
                .collect(Collectors.toList());
    }

    UserFront findById(Long id) {
        return repository.findById(id)
                .map(this::toUserFront)
                .orElse(null);
    }

    private UserFront toUserFront(UserModel entity) {
        return UserFront.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .build();
    }
}
