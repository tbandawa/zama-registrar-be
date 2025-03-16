package me.tbandawa.web.skyzmetro.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Indexed
@Table(name = "members")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"created"}, allowGetters = true)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Field(analyze = Analyze.YES, store = Store.YES)
    @Column(name = "national_id_number", length = 250, unique = true)
    private String nationalIdNumber;

    @Field(analyze = Analyze.YES, store = Store.YES)
    @Column(name = "membership_number", length = 250, unique = true)
    private String membershipNumber;

    @Field(analyze = Analyze.YES, store = Store.YES)
    @Column(length = 250)
    private String names;

    @Field(analyze = Analyze.YES, store = Store.YES)
    @Column(length = 150)
    private String surname;

    @Field(analyze = Analyze.YES, store = Store.YES)
    @Column(length = 350)
    private String position;

    @Field(analyze = Analyze.YES, store = Store.YES)
    @Column(length = 150)
    private String district;

    @Column(name = "added_by")
    private Long addedBy;

    @Column(name = "is_card", length = 1)
    private boolean isCard;

    @Column(name = "is_active", length = 1)
    private boolean isActive;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created;
}
