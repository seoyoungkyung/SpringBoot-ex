package com.shop.repository;

import com.shop.dto.CartDetailDto;
import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    //카트 아이디와 상품 아이디를 이용해서 상품이 장바구니에 있는지 조회
    CartItem findByCartIdAndItemId(Long cartId, Long itemId);


    /*
    select ci.item_id,i.item_nm,i.price, ci.count, im.img_url
    from cart_item ci ,item_img im
    join item i
    where ci.cart_id = 2 and ci.item_id = im.item_id and im.repimg_yn = 'Y'
    order by ci.reg_time desc;
    */
    @Query("select new com.shop.dto.CartDetailDto(ci.id,i.itemNm,i.price,ci.count,im.imgUrl) " +
            "from CartItem ci, ItemImg im " +
            "join ci.item i " +
            "where ci.cart.id =:cartId " +
            "and im.item.id = ci.item.id " +
            "and im.repimgYn = 'Y' " +
            "order by ci.regTime desc"
    )
    List<CartDetailDto> findCartDetailDtoList(Long cartId);
}
