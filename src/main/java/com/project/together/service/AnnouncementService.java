package com.project.together.service;

import com.project.together.entity.Announcement;
import com.project.together.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    @Transactional
    public Long save(Announcement announcement) {
        announcementRepository.save(announcement);
        return announcement.getId();
    }

    public List<Announcement> findAll() {
        return announcementRepository.findAll();
    }

    public Announcement findOne(Long id) {
        return announcementRepository.findOne(id);
    }

    @Transactional
    public void removeWish(Long aId) {
//        wishRepository.findByUserItem(userIdx, wishId).get(0).getWishItem().getItem().removeWishCount();
//        wishRepository.wishCancel(userIdx, wishId);
        announcementRepository.announcementCancel(aId);

    }
}
