package com.example.room.manager.guests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("guests")
@RequiredArgsConstructor
public class GuestsController {
    private final GuestStorage guestStorage;

    @GetMapping
    GuestsView guests() {
        return new GuestsView(guestStorage.guests());
    }

    @PostMapping
    void add(@RequestBody @Validated GuestsInput guests) {
        guestStorage.add(guests.guests);
    }

    @DeleteMapping
    void deleteAll() {
        guestStorage.reset();
    }

    @Data
    private static class GuestsInput {
        @NotNull
        @Size(min = 1, max = 100)
        private List<@Positive Double> guests;
    }

    @Data
    @AllArgsConstructor
    private static class GuestsView {
        private List<Double> guests;
    }
}
