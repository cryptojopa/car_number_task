package com.jobtest.jobtest.service;

import java.util.Random;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class CarNumberService {
    private static final String REGION_CODE = "116 RUS";
    private static final char[] LETTERS = {'А', 'Е', 'Т', 'О', 'Р', 'Н', 'У', 'К', 'Х', 'С', 'В', 'М'};
    private static final int LETTERS_COUNT = LETTERS.length;
    private static final Set<String> usedNumbers = new HashSet<>();
    private String lastNumber = "";

    public String generateRandomNumber() {
        Random random = new Random();
        String number = Stream.generate(() -> {
                    char letter1 = LETTERS[random.nextInt(LETTERS_COUNT)];
                    char letter2 = LETTERS[random.nextInt(LETTERS_COUNT)];
                    char letter3 = LETTERS[random.nextInt(LETTERS_COUNT)];
                    int digits = random.nextInt(1000);
                    return String.format("%c%03d%c%c %s", letter1, digits, letter2, letter3, REGION_CODE);
                })
                .filter(n -> !usedNumbers.contains(n))
                .findFirst()
                .orElseThrow();

        usedNumbers.add(number);
        lastNumber = number;
        return number;
    }

    public String generateNextNumber() {
        char[] lastNumberArray = lastNumber.toCharArray();
        incrementNumber(lastNumberArray);
        lastNumber = new String(lastNumberArray);
        while (usedNumbers.contains(lastNumber)) {
            incrementNumber(lastNumberArray);
            lastNumber = new String(lastNumberArray);
        }
        usedNumbers.add(lastNumber);
        return lastNumber;
    }

    private void incrementNumber(char[] numberArray) {
        int digits = (numberArray[1] - '0') * 100 + (numberArray[2] - '0') * 10 + (numberArray[3] - '0');
        digits = (digits + 1) % 1000;

        numberArray[1] = (char) ('0' + digits / 100);
        numberArray[2] = (char) ('0' + (digits / 10) % 10);
        numberArray[3] = (char) ('0' + digits % 10);

        if (digits == 0) {
            incrementLetters(numberArray);
        }
    }

    private void incrementLetters(char[] numberArray) {
        if (numberArray[5] == LETTERS[LETTERS_COUNT - 1]) {
            numberArray[5] = LETTERS[0];
            if (numberArray[4] == LETTERS[LETTERS_COUNT - 1]) {
                numberArray[4] = LETTERS[0];
                numberArray[0] = LETTERS[(indexOf(numberArray[0]) + 1) % LETTERS_COUNT];
            } else {
                numberArray[4] = LETTERS[(indexOf(numberArray[4]) + 1)];
            }
        } else {
            numberArray[5] = LETTERS[(indexOf(numberArray[5]) + 1)];
        }
    }

    private int indexOf(char letter) {
        return IntStream.range(0, LETTERS_COUNT)
                .filter(i -> LETTERS[i] == letter)
                .findFirst()
                .orElse(-1);
    }
}
