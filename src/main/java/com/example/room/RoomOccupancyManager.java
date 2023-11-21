package com.example.room;

import com.example.room.manager.rooms.GuestCriteria;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(GuestCriteria.class)
public class RoomOccupancyManager {

    public static void main(String[] args) {
        SpringApplication.run(RoomOccupancyManager.class, args);
    }
}
