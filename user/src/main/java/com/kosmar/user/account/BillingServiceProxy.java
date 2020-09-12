package com.kosmar.user.account;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "billing")
public interface BillingServiceProxy {

    @PostMapping("/account")
    void create(@RequestBody CreateAccountRequest request);

}
