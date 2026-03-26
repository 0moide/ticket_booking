package com.example.mywebsite.services;

import java.security.SecureRandom;

public class CodeGenerator {
    private static final SecureRandom random = new SecureRandom();
    
    public static String generateNumericCode() {
        StringBuilder code = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            code.append(random.nextInt(10)); // 0-9
        }
        return code.toString();
    }
}