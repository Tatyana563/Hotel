package com.hotel.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;

@MappedSuperclass
@Data
public abstract class DeletableEntity extends BaseEntity implements Serializable {
    @Column(name = "IS_DELETED")
    private boolean isDeleted;
}
