package com.example.oro3.service;

import com.example.oro3.model.Hotel;
import com.example.oro3.model.Room;
import com.example.oro3.repo.RoomRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepo roomRepo;

    public Room createRoom(int number){
        return roomRepo.save(new Room(null, number, null));
    }
}
