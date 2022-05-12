package com.project.together.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Report {

    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long id;

    @Column(name = "reported_user")
    private String reportedUser;
    @Column(name = "reporting_user")
    private String reportingUser;
    @Column(name = "report_text")
    private String reportText;
    private LocalDateTime createdAt;
}
