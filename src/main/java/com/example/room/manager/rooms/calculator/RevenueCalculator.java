package com.example.room.manager.rooms.calculator;

import com.example.room.manager.rooms.GuestCriteria;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RevenueCalculator {
    private static final Guests EMPTY_GUESTS = new Guests(Collections.emptyList());

    private final GuestCriteria guestCriteria;

    public AllocationResult calculate(int premium, int economy, List<Double> guestOffers) {
        var guests = mapToGuests(guestOffers);
        var guestsByType = classifyAndSortGuests(guests);

        var premiumGuests = guestsByType.getOrDefault(GuestType.PREMIUM, EMPTY_GUESTS);
        var economyGuests = guestsByType.getOrDefault(GuestType.ECONOMY, EMPTY_GUESTS);
        var premiumRooms = new Rooms(premium);
        var economyRooms = new Rooms(economy);

        allocate(premiumGuests, premiumRooms);
        allocate(economyGuests, premiumRooms);
        allocate(economyGuests, economyRooms);

        return new AllocationResult(RevenuePerRoomType.of(RoomType.PREMIUM, premiumRooms),
                RevenuePerRoomType.of(RoomType.ECONOMY, economyRooms));
    }


    private void allocate(Guests guests, Rooms rooms) {
        while (rooms.hasMore() && guests.hasMore()) {
            rooms.addIncome(guests.next().money);
            rooms.next();
        }
    }

    private List<Guest> mapToGuests(List<Double> guests) {
        return guests.stream().map(BigDecimal::valueOf).map(Guest::new).toList();
    }

    private Map<GuestType, Guests> classifyAndSortGuests(List<Guest> guests) {
        return guests.stream().collect(Collectors.groupingBy(this::guestType,
                Collectors.collectingAndThen(Collectors.toList(), group -> new Guests(sortGuests(group)))));
    }

    private List<Guest> sortGuests(List<Guest> group) {
        return group.stream().sorted(Comparator.comparing(Guest::money).reversed()).toList();
    }

    private GuestType guestType(Guest guest) {
        return guest.money.compareTo(guestCriteria.getPremiumThreshold()) < 0 ? GuestType.ECONOMY :
                GuestType.PREMIUM;
    }

    @Getter
    public static class Rooms {

        private final int initial;
        private int used;

        private BigDecimal revenue = BigDecimal.valueOf(0.0);

        public Rooms(int initial) {
            this.initial = initial;
        }

        public boolean hasMore() {
            return used < initial;
        }

        public void next() {
            used++;
        }

        void addIncome(BigDecimal money) {
            revenue = revenue.add(money);
        }
    }

    record Guest(BigDecimal money) {
    }

    private static class Guests {
        private final List<Guest> guests;
        private int position;

        public Guests(List<Guest> guests) {
            this.guests = guests;
            this.position = 0;
        }

        public boolean hasMore() {
            return position < guests.size();
        }

        public Guest next() {
            return guests.get(position++);
        }
    }

    enum GuestType {
        PREMIUM, ECONOMY
    }
}
