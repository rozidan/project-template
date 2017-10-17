package com.company.app.server.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Calendar;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

/**
 * @author Idan Rozenfeld
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditableEntity {
    @CreatedBy
    private String createdBy;

    @CreatedDate
    private Calendar createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private Calendar lastModifiedDate;
}