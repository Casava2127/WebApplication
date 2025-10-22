//package com.ra.model.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@Entity
//@Table(name = "user_role")
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//public class User_Role {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "id_user")
//    private User user;
//
//    @ManyToOne
//    @JoinColumn(name = "id_role")
//    private Role role;
//}
