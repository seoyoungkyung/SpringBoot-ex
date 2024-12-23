package com.shop.entity;

import com.shop.constant.Role;
import com.shop.dto.MemberFormDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name="member")
@Getter
@Setter
@ToString
public class Member extends BaseEntity {

    @Id
    @Column(name="member_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique=true)
    private String email;

    private String password;

    private String address;

    @Enumerated(EnumType.STRING)        //STRING은 해당 이름으로 값을 가져옴 , origin은 인덱스 번호값으로 값을 가져옴
    private Role role;

    //MemberFormDto -> Member로 변환
    public static Member createMember(MemberFormDto memberFormDto,
                                      PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(memberFormDto.getName());
        member.setEmail(memberFormDto.getEmail());
        member.setAddress(memberFormDto.getAddress());

        //passwordEncoder를 파라미터로 넘겨서 비밀번호를 암호화한다.
        member.setPassword(passwordEncoder.encode(memberFormDto.getPassword()));

        //기본권한을 어떤거로 줄지 선택
        member.setRole(Role.ADMIN);
        return member;
    }
}
