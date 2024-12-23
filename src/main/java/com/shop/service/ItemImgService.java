package com.shop.service;

import com.shop.entity.ItemImg;
import com.shop.repository.ItemImgRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {

    @Value("${itemImgLocation}")
    private String itemImgLocation;

    private final ItemImgRepository itemImgRepository;

    private final FileService fileService;

    public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {

        String oriImgName = itemImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        //파일 업로드
        if(!StringUtils.isEmpty(oriImgName)){

            //imgName 실제 로컬에 저장된 상품 이미지 파일의 이름      // oriImgName 업로드했던 상품 이미지 파일의 원래이름
            imgName = fileService.uploadFile(itemImgLocation, oriImgName,itemImgFile.getBytes());

            // imgUrl 업로드 결과 로컬에 저장된 상품 이미지 파일을 불러오는 경로
            imgUrl = "/images/item/"+imgName;

            
            // 아래 주석은 이미지 저장시 입력한 갯수만큼 이미지 저장함 -> 수정 시 이미지 추가하여 저장불가
//            itemImg.updateItemImg(oriImgName,imgName,imgUrl);
//            itemImgRepository.save(itemImg);
        }

        itemImg.updateItemImg(oriImgName,imgName,imgUrl);
        itemImgRepository.save(itemImg);

    }   //end saveItemImg

    public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {

        //itemImgFile null 이 아니면 실행
        // 기존 이미지는 삭제 -> 수정한 이미지 저장
        if(!itemImgFile.isEmpty()){
            ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
                    .orElseThrow(()-> new EntityNotFoundException("ItemImg not found"));

            //기존 이미지 파일 삭제
            if(!StringUtils.isEmpty(savedItemImg.getImgName())){
                fileService.deleteFile(itemImgLocation+"/"+savedItemImg.getImgName());
            }

            String oriImgName = itemImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(itemImgLocation, oriImgName,itemImgFile.getBytes());
            String imgUrl = "/images/item/" + imgName;

            savedItemImg.updateItemImg(oriImgName,imgName,imgUrl);
        }
    }   // end updateItemImg


}
