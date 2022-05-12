package com.project.together.service;

import com.project.together.entity.Report;
import com.project.together.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReportService {

    private final ReportRepository reportRepository;

    @Transactional
    public Long save(Report report) {
        reportRepository.save(report);
        return report.getId();
    }

    public List<Report> findAll() {
        return reportRepository.findAll();
    }
}
