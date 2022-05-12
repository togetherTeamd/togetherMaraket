package com.project.together.service;

import com.project.together.entity.Inquiry;
import com.project.together.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    @Transactional
    public Long save(Inquiry inquiry) {
        inquiryRepository.save(inquiry);
        return inquiry.getId();
    }

    public List<Inquiry> findAll() {
        return inquiryRepository.findAll();
    }

    public Inquiry findOne(Long id) {
        return inquiryRepository.findOne(id);
    }

    public List<Inquiry> findByUserIdx(Long userIdx) {
        return inquiryRepository.findByUserIdx(userIdx);
    }

    @Transactional
    public void answerInquiry(Long inquiryId, String inquiryAnswer) {
        Inquiry inquiry = inquiryRepository.findOne(inquiryId);
        inquiry.setInquiryAnswer(inquiryAnswer);
        inquiry.setAnswerAt(LocalDateTime.now());
    }


}
