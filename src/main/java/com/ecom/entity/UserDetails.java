package com.ecom.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name="USER_DETAILS")
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String mobileNum;
    @Column(unique = true)
    private String email;
    private String city;
    private String state;
    private boolean isEnable;
    private String pincode;
    private String password;
    private String profileImage;

    public void setIsEnable(String status) {
    }
}
