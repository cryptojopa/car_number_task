package com.jobtest.jobtest.controller;


import com.jobtest.jobtest.service.CarNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/number")
public class CarNumberController {

    @Autowired
    private CarNumberService carNumberService;

    @GetMapping("/random")
    public String getRandomNumber() {
        return carNumberService.generateRandomNumber();
    }

    @GetMapping("/next")
    public String getNextNumber() {
        return carNumberService.generateNextNumber();
    }
}
