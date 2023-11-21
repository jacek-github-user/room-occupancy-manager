package com.example.room.manager.rooms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.math.BigDecimal;

@ConfigurationProperties("guest-criteria")
@Data
public class GuestCriteria {
    private BigDecimal premiumThreshold = BigDecimal.valueOf(100);
}
