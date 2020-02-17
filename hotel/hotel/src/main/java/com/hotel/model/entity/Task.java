package com.hotel.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.hotel.model.enumeration.TaskStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
@Entity
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name="TASK")
public class Task implements Serializable {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ERROR_MESSAGE")
    private String errorMessage;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS", nullable = false)
    private TaskStatus status;

    @Column(name = "USER_FK_ID", insertable = false, updatable = false)
    private Integer countryId;

    @ManyToOne()
    @JoinColumn(name = "USER_FK_ID")
    private UserEntity userEntity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id) &&
                Objects.equals(errorMessage, task.errorMessage) &&
                status == task.status &&
                Objects.equals(countryId, task.countryId) &&
                Objects.equals(userEntity, task.userEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, errorMessage, status, countryId, userEntity);
    }
}
