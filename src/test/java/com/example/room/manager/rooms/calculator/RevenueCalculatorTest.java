package com.example.room.manager.rooms.calculator;

import com.example.room.manager.rooms.GuestCriteria;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static com.example.room.manager.rooms.calculator.RoomType.ECONOMY;
import static com.example.room.manager.rooms.calculator.RoomType.PREMIUM;
import static java.math.BigDecimal.valueOf;

class RevenueCalculatorTest {

    private final RevenueCalculator revenueCalculator = new RevenueCalculator(new GuestCriteria());

    @ParameterizedTest
    @MethodSource
    void shouldCalculateResult(Data td) {
        // expect
        Assertions.assertThat(revenueCalculator.calculate(td.premium, td.economy,
                        Arrays.stream(td.offers).boxed().toList()))
                .isEqualTo(new AllocationResult(
                        new RevenuePerRoomType(PREMIUM, valueOf(td.expectedPremiumRevenue), td.expectedPremiumUsage),
                        new RevenuePerRoomType(ECONOMY, valueOf(td.expectedEconomyRevenue), td.expectedEconomyUsage)));
    }

    private static Stream<Data> shouldCalculateResult() {
        return Stream.of(
                new Data(0, 0, new double[]{}, 0, 0, 0, 0),
                new Data(0, 0, new double[]{1, 99.9}, 0, 0, 0, 0),
                new Data(0, 3, new double[]{1, 2, 100}, 0, 0, 3, 2),
                new Data(3, 0, new double[]{1, 2, 100}, 103, 3, 0, 0),
                new Data(1, 1, new double[]{2, 99.9}, 99.9, 1, 2, 1),
                new Data(2, 1, new double[]{2, 99.9}, 101.9, 2, 0, 0),
                new Data(2, 1, new double[]{101, 99.9, 1}, 200.9, 2, 1, 1),
                new Data(1, 3, new double[]{100, 200, 300}, 300, 1, 0, 0)
        );
    }

    record Data(int premium,
                int economy,
                double[] offers,
                double expectedPremiumRevenue,
                int expectedPremiumUsage,
                double expectedEconomyRevenue, int expectedEconomyUsage) {
    }
}