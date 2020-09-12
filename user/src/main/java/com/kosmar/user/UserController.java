package com.kosmar.user;

import com.kosmar.user.account.BillingServiceProxy;
import com.kosmar.user.account.CreateAccountRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService service;
    private final BillingServiceProxy proxy;

    @GetMapping("/")
    public List<UserFront> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public UserFront findById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping("/")
    @Transactional
    public UserFront save(@RequestBody UserRequest request) {
        UserFront user = service.save(request.getFirstName(), request.getLastName(), request.getEmail());
        CreateAccountRequest createAccountRequest = CreateAccountRequest.builder().userId(user.getId()).build();
        proxy.create(createAccountRequest);
        return user;
    }
}
