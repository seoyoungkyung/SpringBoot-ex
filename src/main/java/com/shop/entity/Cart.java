package com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cart")
@Getter
@Setter
@ToString
public class Cart extends BaseEntity{

        @Id
        @Column(name="cart_id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

    //  @OneToOne(fetch = FetchType.LAZY) 조인x(Member테이블 사용 되기 직전 select/지연 로딩)
    //  필요한 시점에만 연관 데이터를 조회하므로 초기 로딩 시 성능을 최적화 할 수 있음

    //  @OneToOne(fetch = FetchType.EAGER) 조인o 으로 조회(기본값/즉시 로딩)
    //  엔티티를 조회할 때 해당 엔티티와 매핑된 엔티티도 한 번에 조회하는 것

        @OneToOne(fetch = FetchType.LAZY)                            //회원 엔티티와 일대일관계로 매핑
        @JoinColumn(name="member_id")       //Member table에 있는 member_id를 외래키로 설정
        private Member member;

        // 카트 생성
        public static Cart createCart(Member member) {
                Cart cart = new Cart();
                cart.setMember(member);
                return cart;
        }

    }
