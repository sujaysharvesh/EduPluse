package com.example.Edupulse.utils;

import com.example.Edupulse.admin.dto.AdminCredentials;
import com.example.Edupulse.school.School;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Component
public class AdminUtils {

    private static final SecureRandom RANDOM = new SecureRandom();

    private static final String PASSWORD_CHARS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    private static final int PASSWORD_LENGTH = 8;

    public AdminCredentials generateAdminCredentials(School school) {


        String username = generateAdminName(school.getSchoolName());

        String password = generatePassword(null);

        AdminCredentials credentials = AdminCredentials.builder()
                .username(username)
                .password(password)
                .build();

        return credentials;
    }

    private String generateAdminName(String schoolName) {

        String cleanedName = schoolName
                .trim()
                .toLowerCase()
                .replaceAll("\\s+", "");

        int randomNumber = 1000 + RANDOM.nextInt(9000);

        return cleanedName + "_admin" + randomNumber;
    }

    private String generatePassword(String supplied) {

        if (supplied != null && !supplied.isBlank()) {
            return supplied;
        }

        StringBuilder sb = new StringBuilder(PASSWORD_LENGTH);

        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            sb.append(
                    PASSWORD_CHARS.charAt(
                            RANDOM.nextInt(PASSWORD_CHARS.length())
                    )
            );
        }

        return sb.toString();
    }
}