package ltd.newbee.mall.controller.mall;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ltd.newbee.mall.entity.GoodsDesc;
import ltd.newbee.mall.entity.GoodsImage;
import ltd.newbee.mall.entity.GoodsQa;
import ltd.newbee.mall.entity.GoodsReview;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
@SpringBootTest


class GoodsControllerTest {
	@Resource
NewBeeMallGoodsService newBeeMallGoodsService;
	
	
	
@Test 
 public void testGoodsImage() {
	Long goodsId = 10700L;
    List<GoodsImage> list = newBeeMallGoodsService.getGoodsImageEntityByGoodsId(goodsId);
    GoodsImage image = list.get(0);
    String path = image.getPath();
    assertEquals("/goods-img/00e53d76-db08-4ae2-864f-ca1cd7c8c32b.jpg",path);
}
@Test
 public void testGoodsDesc() { 
 long goodsId =  10700L; 
    GoodsDesc goodsDesc =newBeeMallGoodsService.getGoodsDescEntityByGoodsId(goodsId); 
    String color =goodsDesc.getColor(); 
    assertEquals("珊瑚色",color);
 }
@Test
 public void testGoodsQa() {
	Long goodsId = 10700L ;
    List<GoodsQa> listQa =  newBeeMallGoodsService.getGoodsQaEntityByGoodsId(goodsId);
    GoodsQa qa = listQa.get(0);
    String question = qa.getQuestion();
    assertEquals("ありがとうございました。",question);
 }
@Test
public void testGoodsReview() {
	Long goodsId = 10700L ;
	List<GoodsReview> listReview =  newBeeMallGoodsService.getGoodsReviewEntityByGoodsId(goodsId);
	GoodsReview review = listReview.get(0);
	String picture = review.getPicture();
	assertEquals("/goods-img/de654f42-d58d-4336-8edd-da01c3523449.jpg",picture);
	
	
	
	
	
	
	
	
	
}

  
 
}
