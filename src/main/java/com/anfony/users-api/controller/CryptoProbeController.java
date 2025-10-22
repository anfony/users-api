package com.anfony.usersapi.controller;

import com.anfony.usersapi.util.CryptoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CryptoProbeController {

    private final CryptoService crypto;

    public CryptoProbeController(CryptoService crypto) {
        this.crypto = crypto;
    }

    @GetMapping("/crypto/probe")
    public String probe(@RequestParam("p") String p) {
        String enc = crypto.encrypt(p);
        String dec = crypto.decrypt(enc);
        return dec.equals(p) ? "crypto-ok" : "crypto-fail";
    }
}
