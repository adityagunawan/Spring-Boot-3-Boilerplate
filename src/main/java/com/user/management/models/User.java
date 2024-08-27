package com.user.management.models;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "created_date")
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "modified_date")
    @UpdateTimestamp
    private Date modifiedDate;


//    @Enumerated(EnumType.STRING)
//    private Role role;
//
//    @OneToMany(mappedBy = "user")
//    private List<Token> tokens;

//    @Column(name = "status")
//    private Integer status;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        roles.forEach(r -> {
//            authorities.addAll(r.getUserApiUrls()
//                    .stream()
//                    .map(p -> new SimpleGrantedAuthority(p.getApiUrl()))
//                    .collect(Collectors.toList()));
//        });
//
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }

}
