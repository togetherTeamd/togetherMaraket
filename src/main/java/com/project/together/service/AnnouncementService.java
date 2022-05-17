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

//    public List<Inquiry> findByUserIdx(Long userIdx) {
//        return announcementRepository.findByUserIdx(userIdx);
//    }

//    @Transactional
//    public void answerInquiry(Long inquiryId, String inquiryAnswer) {
//        Inquiry inquiry = inquiryRepository.findOne(inquiryId);
//        inquiry.setInquiryAnswer(inquiryAnswer);
//        inquiry.setAnswerAt(LocalDateTime.now());
//    }
}
