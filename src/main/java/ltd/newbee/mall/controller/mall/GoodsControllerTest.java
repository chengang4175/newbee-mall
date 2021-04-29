package ltd.newbee.mall.controller.mall;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.entity.Carousel;
import ltd.newbee.mall.entity.GoodsDesc;
import ltd.newbee.mall.entity.GoodsImage;
import ltd.newbee.mall.entity.GoodsQa;
import ltd.newbee.mall.entity.GoodsReview;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
@SpringBootTest


class GoodsControllerTest {
	@Resource
NewBeeMallGoodsService newBeeMallGoodsService;
	private NewBeeMallGoodsService newBeeMallGoodsQaPage;
	private PageResult rs;
	private Object newBeeMallGoodsQaService;
	
	
	
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
    assertEquals("这个保修吗",question);
 }
@Test
public void testGoodsReview() {
	Long goodsId = 10700L ;
	List<GoodsReview> listReview =  newBeeMallGoodsService.getGoodsReviewEntityByGoodsId(goodsId);
	GoodsReview review = listReview.get(0);
	String picture = review.getPicture();
	assertEquals("/goods-img/de654f42-d58d-4336-8edd-da01c3523449.jpg",picture);
}	

////四月二十三日，页面测试	
@Test
	public void testPageResultService() {
	Map<String,Object> params = new HashMap<String,Object>();
	params.put("page","1");
	params.put("limit","3");
	PageQueryUtil pageUtil = new PageQueryUtil(params);
	PageResult rs = newBeeMallGoodsService.getGoodsQaEntityByGoodsId(pageUtil);
	List<GoodsQa> goodsList = (List<GoodsQa>) rs.getList();
	int size = 0;
	if(goodsList != null || !goodsList.isEmpty()) {
		size = goodsList.size();
}
	assertEquals(3,size);
	assertEquals("001",goodsList.get(0).getId());
	assertEquals("002",goodsList.get(1).getId());
	assertEquals("003",goodsList.get(2).getId());
}	
////四月二十四日，页面排序测试
@Test
    public void testHelpedNumDateService() {
	Map<String,Object> params = new HashMap<String,Object>();
	params.put("page","1");
	params.put("limit","3");
	params.put("orderBy","helped_num");
	PageQueryUtil pageUtil = new PageQueryUtil(params);
	PageResult rs = newBeeMallGoodsService.getHelpedNumListEntityByGoodsId(pageUtil);
	List<GoodsQa> goodsList = (List<GoodsQa>) rs.getList();
	int size = 0;
	if(goodsList != null || !goodsList.isEmpty()) {
		size = goodsList.size();
}
	assertEquals(3,size);
	assertEquals("11",goodsList.get(0).getHelpedNum());
	assertEquals("12",goodsList.get(1).getHelpedNum());
	assertEquals("13",goodsList.get(2).getHelpedNum());
	}
//四月二十五日，页面内容输入测试
@Test
    public void testInsertGoodsQa() {
	   GoodsQa qa = new GoodsQa();
	   qa.setQuestion("好吃吗");
//	   qa.setId(009L);
	   qa.setAnswer("好吃厉害");
//	   qa.setAnswerDate("20210423");
	   qa.setHelpedNum("20");
	   qa.setSubmitDate(null);
	   qa.setGoodsId(10070L);
	   int rs = newBeeMallGoodsService.insertGoodsQa(qa);
	   assertEquals(ServiceResultEnum.SUCCESS.getResult(),rs);
	   
	   
}
}




  
 

