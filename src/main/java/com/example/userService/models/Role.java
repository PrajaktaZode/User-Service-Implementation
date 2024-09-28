package com.example.userService.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonDeserialize(as = Role.class)
public class Role extends BaseModel{
    // No enum bcz we might have new roles in future and we should not have to update the code and can be directly added through DB.
    private String role;
}
