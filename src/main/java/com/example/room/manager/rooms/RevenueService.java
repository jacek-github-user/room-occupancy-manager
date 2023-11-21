package com.example.room.manager.rooms;

import com.example.room.manager.guests.GuestStorage;
import com.example.room.manager.rooms.calculator.AllocationResult;
import com.example.room.manager.rooms.calculator.RevenueCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RevenueService {
    private final GuestStorage guestStorage;
    private final RevenueCalculator revenueCalculator;

    public AllocationResult calculateRevenue(int premium, int economy) {
        return revenueCalculator.calculate(premium, economy, guestStorage.guests());
    }
}
