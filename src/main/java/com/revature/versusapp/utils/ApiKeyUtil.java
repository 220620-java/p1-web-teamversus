package com.revature.versusapp.utils;

import java.util.Random;

public class ApiKeyUtil {
    public static String generateApiKey() {
        int zero = 48; // numeral '0'
        int zee = 122; // letter 'z'
        int targetStringLength = 14;
        Random random = new Random();
        
        StringBuilder generatedString = random.ints(zero, zee + 1)
                .filter(i -> ((48<= i && i<=57) || (65<= i && i<=90) || (97<= i && i<=122)) )
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append);
        generatedString.setCharAt(4, '-');
        generatedString.setCharAt(9, '-');
        
        return generatedString.toString();
    }
}
