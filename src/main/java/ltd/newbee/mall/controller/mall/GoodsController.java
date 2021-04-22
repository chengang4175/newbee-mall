/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.controller.mall;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.NewBeeMallException;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.GoodsDescVO;
import ltd.newbee.mall.controller.vo.GoodsImageVO;
import ltd.newbee.mall.controller.vo.GoodsQaVO;
import ltd.newbee.mall.controller.vo.GoodsReviewVO;
import ltd.newbee.mall.controller.vo.NewBeeMallGoodsDetailVO;
import ltd.newbee.mall.controller.vo.SearchPageCategoryVO;
import ltd.newbee.mall.entity.GoodsDesc;
import ltd.newbee.mall.entity.GoodsImage;
import ltd.newbee.mall.entity.GoodsQa;
import ltd.newbee.mall.entity.GoodsReview;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.PageQueryUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsController<GoddsImageVo> {

    private static final Collection GoodsImageVO = null;
	private static final List GoodsQaEntity = null;
	@Resource
    private NewBeeMallGoodsService newBeeMallGoodsService;
    @Resource
    private NewBeeMallCategoryService newBeeMallCategoryService;
	private Integer goodsId;
	private Object imageVO;
	private Object descVO;

    @GetMapping({"/search", "/search.html"})
    public String searchPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
        if (StringUtils.isEmpty(params.get("page"))) {
            params.put("page", 1);
        }
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        //封装分类数据
        if (params.containsKey("goodsCategoryId") && !StringUtils.isEmpty(params.get("goodsCategoryId") + "")) {
            Long categoryId = Long.valueOf(params.get("goodsCategoryId") + "");
            SearchPageCategoryVO searchPageCategoryVO = newBeeMallCategoryService.getCategoriesForSearch(categoryId);
            if (searchPageCategoryVO != null) {
                request.setAttribute("goodsCategoryId", categoryId);
                request.setAttribute("searchPageCategoryVO", searchPageCategoryVO);
            }
        }
        //封装参数供前端回显
        if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
            request.setAttribute("orderBy", params.get("orderBy") + "");
        }
        String keyword = "";
        //对keyword做过滤 去掉空格
        if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
            keyword = params.get("keyword") + "";
        }
        request.setAttribute("keyword", keyword);
        params.put("keyword", keyword);
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        request.setAttribute("pageResult", newBeeMallGoodsService.searchNewBeeMallGoods(pageUtil));
        return "mall/search";
    }

    @GetMapping("/goods/detail/{goodsId}")
    public String detailPage(@PathVariable("goodsId") Long goodsId, HttpServletRequest request) {
        if (goodsId < 1) {
            return "error/error_5xx";
        }
        NewBeeMallGoods goods = newBeeMallGoodsService.getNewBeeMallGoodsById(goodsId);
        if (goods == null) {
            NewBeeMallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            NewBeeMallException.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
        }
        NewBeeMallGoodsDetailVO goodsDetailVO = new NewBeeMallGoodsDetailVO();
        BeanUtil.copyProperties(goods, goodsDetailVO);
        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));
        request.setAttribute("goodsDetail", goodsDetailVO);
        
        
        
        
      //4/19日 新加机能     
      // <goodimage>++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        
        
        
		List<GoodsImage> list = newBeeMallGoodsService.getGoodsImageEntityByGoodsId(goodsId);
        if(list == null) {
        	NewBeeMallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
 
        List<GoodsImageVO> imageVOList = new ArrayList<GoodsImageVO>();
            for(int i = 0; i < list.size();i++) {
            GoodsImage image = new GoodsImage();
            image = list.get(i);
            if (image !=null)  {
//	            String path = image.getPath();
	            GoodsImageVO imageVO = new GoodsImageVO();
//	            imageVO.setPath(path);
	            BeanUtil.copyProperties(image, imageVO);
	            imageVOList.add(imageVO);	
	            
         }            		 
         }
            	
            
            request.setAttribute("goodsImegeDetail", imageVOList);  
            
         
       
    //===============================================================================================================


        List<GoodsQa> listQa =  newBeeMallGoodsService.getGoodsQaEntityByGoodsId(goodsId);
        if(listQa == null) {
        	NewBeeMallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        List<GoodsQaVO> qaVOList = new ArrayList<GoodsQaVO>();
           for(int i = 0; i < listQa.size();i++) {
        	   GoodsQa qa = new GoodsQa();
        	   qa = listQa.get(i);
        	   if (qa !=null) {
//        		   String question = qa.getQuestion();
//        		   String id = qa.getId();
//        		   Date submitDate = qa.getSubmitDate();
//        		   String answer = qa.getAnswer();
//        		   String answerDate = qa.getAnswer();
//        		   String helpedNum = qa.getHelpedNum();
        		   GoodsQaVO qaVO = new GoodsQaVO();
//        		   qaVO.setQuestion(question);
        		   BeanUtil.copyProperties(qa, qaVO);
        		   qaVOList.add(qaVO);
        	   }  
               }
          request.setAttribute("goodsQaDetail", qaVOList);
//===================================================================================================================================
          List<GoodsReview> listReview = newBeeMallGoodsService.getGoodsReviewEntityByGoodsId(goodsId);
          if(listReview == null) {
        	  NewBeeMallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
          }
          List<GoodsReviewVO> reviewVOList = new ArrayList<GoodsReviewVO>();
             for(int i = 0; i < listReview.size();i++) {
            	 GoodsReview review = new GoodsReview();
            	 review = listReview.get(i);
            	 if (review !=null) {
//            		 String picture = review.getPicture();
//            		 String id = review.getContent();
//            		 Integer star = review.getStar();
//            		 String custermerId = review.getCustermerId();
//            		 Date commentDate = review.getCommentDate();
//            		 String title = review.getContent();
//            		 String content = review.getContent();
            		 GoodsReviewVO reviewVO = new GoodsReviewVO();
//            		 reviewVO.setPicture(picture);
            		 BeanUtil.copyProperties(review, reviewVO);
            		 reviewVOList.add(reviewVO);
            		 
            	 }
                 }
             request.setAttribute("goodsReviewDetail", reviewVOList);
//===================================================================================================================================
          GoodsDesc goodsDesc = newBeeMallGoodsService.getGoodsDescEntityByGoodsId(goodsId);
          if (goodsDesc == null) {
           	  NewBeeMallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
           }
             GoodsDescVO desc = new GoodsDescVO();
             BeanUtil.copyProperties(goodsDesc, desc);
             request.setAttribute("goodsDescDetail", desc);
    
              return "mall/detail";
             
                
    }
}
           
             
              
         




       	  
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
          
 
        
    
    
                                   
    
   




