package com.hotel.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.io.Serializable;

@MappedSuperclass
@Data
public abstract class OwnedEntity extends DeletableEntity implements Serializable {

    @Column(name = "USER_FK_ID")
    private Integer ownerId;
}
