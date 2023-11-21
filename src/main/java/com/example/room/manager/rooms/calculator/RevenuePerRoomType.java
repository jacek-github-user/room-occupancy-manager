package com.example.room.manager.rooms.calculator;

import java.math.BigDecimal;

public record RevenuePerRoomType(RoomType roomType, BigDecimal revenue, int usage) {
    public static RevenuePerRoomType of(RoomType roomType, RevenueCalculator.Rooms rooms) {
        return new RevenuePerRoomType(roomType, rooms.getRevenue(), rooms.getUsed());
    }
}
