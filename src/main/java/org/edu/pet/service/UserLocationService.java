package org.edu.pet.service;

import lombok.RequiredArgsConstructor;
import org.edu.pet.dto.req.AddLocationDto;
import org.edu.pet.dto.req.DeleteLocationDto;
import org.edu.pet.mapper.LocationDtoMapper;
import org.edu.pet.model.Location;
import org.edu.pet.model.User;
import org.edu.pet.repository.LocationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserLocationService {

    private final LocationRepository locationRepository;
    private final LocationDtoMapper locationDtoMapper;

    List<Location> findAllInternal(User user) {

        return locationRepository.findAllByUser(user);
    }

    void removeInternal(Location location) {

        locationRepository.delete(location);
    }

    public void add(User user, AddLocationDto addLocationDto) {

        Location location = locationDtoMapper.toEntity(addLocationDto, user);
        locationRepository.save(location);
    }

    public void removeByCoordinates(User user, DeleteLocationDto dto) {

        locationRepository.findByLongitudeAndLatitudeAndUser(dto.longitude(), dto.latitude(), user)
                .ifPresent(this::removeInternal);
    }
}