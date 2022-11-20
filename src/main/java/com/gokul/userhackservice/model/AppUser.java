package com.gokul.userhackservice.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name="users",
        uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"), @UniqueConstraint(columnNames = "email")
        }
)

@NoArgsConstructor
@Getter
@Setter
public class AppUser {

//        id username email password roles
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank
        @Size(max = 20)
        private String username;

        @NotBlank
        @Size(max = 50)
        private String gmnia;

        @NotBlank
        @Size(max = 300)
        private String address;

        private Long deviceId;

        private ELevel level;

        @OneToMany(mappedBy = "user",fetch=FetchType.LAZY,cascade = CascadeType.ALL)
        @JsonManagedReference
        private Set<Compost_Activity>activities;

        @NotBlank
        @Size(max = 70)
        @Email
        private String email;

        @NotBlank
        @Size(max = 120)
        private String password;


        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id")
        )
        private Set<Role> roles=new HashSet<>();


        @Override
        public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                AppUser appUser = (AppUser) o;
                return Objects.equals(id, appUser.id) && Objects.equals(username, appUser.username) && Objects.equals(gmnia, appUser.gmnia) && Objects.equals(address, appUser.address) && Objects.equals(deviceId, appUser.deviceId) && level == appUser.level  && Objects.equals(email, appUser.email) && Objects.equals(password, appUser.password) && Objects.equals(roles, appUser.roles);
        }

        @Override
        public int hashCode() {
                return Objects.hash(id, username, gmnia, address, deviceId, level, email, password, roles);
        }
}
