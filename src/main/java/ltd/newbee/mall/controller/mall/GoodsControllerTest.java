package ltd.newbee.mall.controller.mall;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ltd.newbee.mall.entity.GoodsDesc;
import ltd.newbee.mall.entity.GoodsImage;
import ltd.newbee.mall.entity.GoodsQa;
import ltd.newbee.mall.entity.GoodsReview;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
@SpringBootTest


class GoodsControllerTest {
	@Resource
NewBeeMallGoodsService newBeeMallGoodsService;
	private NewBeeMallGoodsService newBeeMallGoodsQaPage;
	private PageResult rs;
	
	
	
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
    assertEquals("这个划算吗",question);
 }
@Test
public void testGoodsReview() {
	Long goodsId = 10700L ;
	List<GoodsReview> listReview =  newBeeMallGoodsService.getGoodsReviewEntityByGoodsId(goodsId);
	GoodsReview review = listReview.get(0);
	String picture = review.getPicture();
	assertEquals("/goods-img/de654f42-d58d-4336-8edd-da01c3523449.jpg",picture);
}	

//四月二十三日，页面测试===========================================================================	
@Test
	public void testPageResultService() {
	Map<String,Object> params = new HashMap<String,Object>();
	params.put("page","1");
	params.put("limit","3");
	PageQueryUtil pageUtil = new PageQueryUtil(params);
	PageResult rs = newBeeMallGoodsService.getNewBeeMallGoodsPage(pageUtil);
	List<GoodsQa> goodsList = (List<GoodsQa>)rs.getList();
	int size = 0;
	if(goodsList != null || !goodsList.isEmpty()) {
		size = goodsList.size();
}
	assertEquals(3,size);
	GoodsQa expect1 = new GoodsQa();
	expect1.setGoodsId(10700L);
	GoodsQa expect2 = new GoodsQa();
	expect2.setQuestion("这个划算吗");
	GoodsQa expect3 = new GoodsQa();
	expect3.setAnswer("非常划算");
	GoodsQa expect4 = new GoodsQa();
	expect4.setHelpedNum("9");
	GoodsQa expect5 = new GoodsQa();
	expect5.setId("10222");
	
	List<GoodsQa> expectList = new ArrayList<GoodsQa>();
	Boolean isTrue = goodsList.equals(expectList);
	assertEquals(true,isTrue);
	}
}
  
 

