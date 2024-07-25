package com.jobtest.jobtest.service;

import com.jobtest.jobtest.exception.NoNumbersAvailableException;
import com.jobtest.jobtest.model.CarNumber;
import com.jobtest.jobtest.repository.CarNumberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CarNumberService {
    private static final String REGION_CODE = "116 RUS";
    private static final char[] LETTERS = {'А', 'В', 'Е', 'К', 'М', 'Н', 'О', 'Р', 'С', 'Т', 'У', 'Х'};

    private final CarNumberRepository repository;

    private CarNumber lastNumber;

    @PostConstruct
    private void init() {
        if (repository.count() == 0) {
            for (char firstLetter : LETTERS) {
                for (char secondLetter : LETTERS) {
                    for (char thirdLetter : LETTERS) {
                        for (int num = 0; num <= 999; num++) {
                            CarNumber carNumber = new CarNumber();
                            carNumber.setNumber(String.format("%c%03d%c%c %s", firstLetter, num, secondLetter, thirdLetter, REGION_CODE));
                            repository.save(carNumber);
                        }
                    }
                }
            }
        }
    }

    public String generateRandomNumber() {
        try {
            CarNumber carNumber = repository.findRandomCarNumberByAlreadyUsed(false);
            carNumber.setAlreadyUsed(true);
            repository.save(carNumber);
            lastNumber = carNumber;
            return carNumber.getNumber();
        } catch (NullPointerException ex) {
            throw new NoNumbersAvailableException("Доступные номера не найдены");
        }
    }

    public String generateNextNumber () {
        try {
            while (true) {
                CarNumber nextNumber = repository.findById(lastNumber.getId() + 1);
                if (!nextNumber.isAlreadyUsed()) {
                    nextNumber.setAlreadyUsed(true);
                    repository.save(nextNumber);
                    lastNumber = nextNumber;
                    return lastNumber.getNumber();
                }
            }
        } catch (NullPointerException ex) {
            throw new NoNumbersAvailableException("Доступные номера не найдены или перед первым вызовом /next не был сгенерирован рандомный номер");
        }
    }
}
