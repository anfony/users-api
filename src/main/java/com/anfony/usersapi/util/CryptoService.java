package com.anfony.usersapi.util;

public interface CryptoService {
    String encrypt(String plain);
    String decrypt(String encoded);
}
