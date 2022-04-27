package com.project.together.service;

import com.project.together.entity.Address;
import com.project.together.repository.MapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MapService {

    private final MapRepository mapRepository;

    public long join(Address address) {
        mapRepository.save(address);
        return 1;
    }
}
