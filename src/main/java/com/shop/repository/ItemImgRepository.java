package com.shop.repository;

import com.shop.entity.ItemImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemImgRepository extends JpaRepository<ItemImg, Long> {

    List<ItemImg> findByItemIdOrderByIdDesc(Long itemId);
    List<ItemImg> findByItemIdOrderByIdAsc(Long itemId);

    //itemId 에 해당하는 대표이미지 반환
    ItemImg findByItemIdAndRepimgYn(Long itemId, String repimgYn);
}
