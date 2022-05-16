package com.project.together.service;

import com.project.together.entity.Recent;
import com.project.together.repository.RecentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecentService {

    private final RecentRepository recentRepository;

    @Transactional
    public Long save(Recent recent) {
        recentRepository.save(recent);
        return recent.getId();
    }

    public List<Recent> findByUserIdx(Long userIdx) {
        return recentRepository.findByUserIdx(userIdx);
    }

    @Transactional
    public Long deleteRecent(Long id) {
        recentRepository.deleteRecent(id);
        return id;
    }

    @Transactional
    public void deleteDuplicate(Long itemId, Long userIdx) {
        recentRepository.deleteDuplicate(itemId, userIdx);
    }
}
