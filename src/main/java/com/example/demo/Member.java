/*
package com.example.demo;

import jakarta.persistence.*;
import lombok.*;
@NoArgsConstructor(access = AccessLevel.PROTECTED) //기본 생성자
@AllArgsConstructor
@Getter
@Setter
@Entity //엔터티로 지정
public class Member {
    @Id //id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //기본키를 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", nullable = false) //name이라는 not null 컬럼과 매핑
    private String name;

    public void changeName(String name) {
        this.name = name;
    }

}


*/
