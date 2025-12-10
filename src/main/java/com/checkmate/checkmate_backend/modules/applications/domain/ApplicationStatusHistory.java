package com.checkmate.checkmate_backend.modules.applications.domain;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "application_status_history")
public class ApplicationStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    @Enumerated(EnumType.STRING)
    @Column(name = "old_status", length = 50)
    private ApplicationStatus oldStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false, length = 50)
    private ApplicationStatus newStatus;

    @Column(name = "comment", columnDefinition = "text")
    private String comment;

    @Column(name = "changed_at", nullable = false)
    private OffsetDateTime changedAt;

    public ApplicationStatusHistory() {}

    @PrePersist
    public void onCreate() {
        if (changedAt == null) {
            changedAt = OffsetDateTime.now();
        }
    }

    // Getters & setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Application getApplication() { return application; }

    public void setApplication(Application application) { this.application = application; }

    public ApplicationStatus getOldStatus() { return oldStatus; }

    public void setOldStatus(ApplicationStatus oldStatus) { this.oldStatus = oldStatus; }

    public ApplicationStatus getNewStatus() { return newStatus; }

    public void setNewStatus(ApplicationStatus newStatus) { this.newStatus = newStatus; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment; }

    public OffsetDateTime getChangedAt() { return changedAt; }

    public void setChangedAt(OffsetDateTime changedAt) { this.changedAt = changedAt; }
}