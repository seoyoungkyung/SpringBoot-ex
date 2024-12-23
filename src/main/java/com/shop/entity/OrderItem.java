package com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="order_item")
@ToString
public class OrderItem extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;

    private int orderPrice;     // 주문 가격

    private int count;          // 수량
    
    
    public static OrderItem createOrderItem(Item item, int count){
        
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);        // 주문 상품
        orderItem.setCount(count);      // 주문 수량
        orderItem.setOrderPrice(item.getPrice());
        
        // 주문 수량만큼 상품의 재고 수량 감소
        item.removeStock(count);
        return orderItem;
    }
    
    // 주문가격과 주문 수량을 곱해서 해당 상품을 주문한 총 가격 계산
    public int getTotalPrice(){
        return orderPrice*count;
    }

    public void cancel(){
        this.getItem().addStock(count);
    }
}
