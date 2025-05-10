package com.example.oro3.service;

import com.example.oro3.model.Hotel;
import com.example.oro3.model.Room;
import com.example.oro3.repo.HotelRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HotelService {

    private final HotelRepo hotelRepo;

    public Hotel createHotel(List<Room> rooms){
        Hotel hotel = new Hotel();
        rooms.forEach(room -> room.setHotel(hotel));
        hotel.setRooms(rooms);
        return hotelRepo.save(hotel);
    }
}
