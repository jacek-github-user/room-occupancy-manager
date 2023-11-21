package com.example.room.manager.rooms;

import com.example.room.manager.rooms.calculator.AllocationResult;
import com.example.room.manager.rooms.calculator.RevenuePerRoomType;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("rooms")
@Validated
@RequiredArgsConstructor
public class RoomController {
    private final RevenueService revenueService;

    @GetMapping("calculate-revenue/premium/{premium}/economy/{economy}")
    AllocationResultView calculateRevenue(@PathVariable @PositiveOrZero int premium,
                                          @PathVariable @PositiveOrZero int economy) {
        return new AllocationResultView(revenueService.calculateRevenue(premium, economy));
    }

    @Data
    static class AllocationResultView {
        private RevenuePerRoomTypeView premium;
        private RevenuePerRoomTypeView economy;

        public AllocationResultView(AllocationResult allocationResult) {
            premium = mapToView(allocationResult.premium());
            economy = mapToView(allocationResult.economy());
        }

        private RevenuePerRoomTypeView mapToView(RevenuePerRoomType room) {
            return new RevenuePerRoomTypeView(room.roomType().name(), room.usage(), room.revenue());
        }

        @Data
        @AllArgsConstructor
        static class RevenuePerRoomTypeView {
            private String roomType;
            private int usage;
            private BigDecimal revenue;
        }
    }
}
