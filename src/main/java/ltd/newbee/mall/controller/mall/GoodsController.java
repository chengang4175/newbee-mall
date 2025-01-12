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
import ltd.newbee.mall.controller.vo.GoodsSaleVO;
import ltd.newbee.mall.controller.vo.NewBeeMallGoodsDetailVO;
import ltd.newbee.mall.controller.vo.NewBeeMallUserVO;
import ltd.newbee.mall.controller.vo.SearchHistoryVO;
import ltd.newbee.mall.controller.vo.SearchPageCategoryVO;
import ltd.newbee.mall.controller.vo.TbSaleVO;
import ltd.newbee.mall.entity.GoodsDesc;
import ltd.newbee.mall.entity.GoodsImage;
import ltd.newbee.mall.entity.GoodsQa;
import ltd.newbee.mall.entity.GoodsReview;
import ltd.newbee.mall.entity.GoodsSale;
import ltd.newbee.mall.entity.HelpNum;

import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.PagingQa;
import ltd.newbee.mall.entity.SearchHistory;
import ltd.newbee.mall.entity.TbSale;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class GoodsController<GoddsImageVo> {
	@Resource
    private NewBeeMallGoodsService newBeeMallGoodsService;
    @Resource
    private NewBeeMallCategoryService newBeeMallCategoryService;
	
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
             if (image !=null) {
//	            String path = image.getPath();
	            GoodsImageVO imageVO = new GoodsImageVO();
//	            imageVO.setPath(path);
	            BeanUtil.copyProperties(image, imageVO);
	            imageVOList.add(imageVO);	
	            
             }            		 
           }         	
//===============================================================================================================
        Map<String,Object> params = new HashMap<>();
        params.put("page","1");
        params.put("limit",Constants.GOODS_QA_PAGE_LIMIT);
        params.put("orderBy","helped_num");
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        PageResult rs = newBeeMallGoodsService.getHelpedNumListEntityByGoodsId(pageUtil);
        List<GoodsQa> listQa = (List<GoodsQa>) rs.getList();
//      List<GoodsQa> listQa =  newBeeMallGoodsService.getGoodsQaEntityByGoodsId(goodsId);
        if(listQa == null) {
        	NewBeeMallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        List<GoodsQaVO> qaVOList = new ArrayList<GoodsQaVO>();
           for(int i = 0; i < listQa.size();i++) {
        	   GoodsQa qa = new GoodsQa();
        	   qa = listQa.get(i);
        	   if (qa !=null) {        		  
        		   GoodsQaVO qaVO = new GoodsQaVO();      
        		   BeanUtil.copyProperties(qa, qaVO);
        		   qaVOList.add(qaVO);
        	   }  
           }
          
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
            		 GoodsReviewVO reviewVO = new GoodsReviewVO();
            		 BeanUtil.copyProperties(review, reviewVO);
            		 reviewVOList.add(reviewVO);
            		 
            	 }
              }
				/*
				 * List<TbSale> tbSale = newBeeMallGoodsService.TbSale(goodsId); if(tbSale ==
				 * null) {
				 * NewBeeMallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult()); }
				 * List<TbSaleVO> saleVOList = new ArrayList<TbSaleVO>(); for(int i = 0; i <
				 * tbSale.size();i++) { TbSale sale = new TbSale(); sale = tbSale.get(i); if
				 * (sale !=null) { TbSaleVO saleVO = new TbSaleVO();
				 * BeanUtil.copyProperties(sale, saleVO); saleVOList.add(saleVO);
				 * 
				 * } }
				 */
	           
				/* request.setAttribute("tbSaleDetail", saleVOList); */
             request.setAttribute("goodsImegeDetail", imageVOList); 
             request.setAttribute("goodsQaDetail", qaVOList);
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
            @RequestMapping(value = "/goods/qaSort", method = RequestMethod.POST)
            @ResponseBody
            public Result getHelpedNumListEntityByGoodsId(@RequestBody PagingQa page) {    	
            	Map<String,Object> params = new HashMap<>();
            	params.put("page",page.getPage());
            	params.put("limit",Constants.GOODS_QA_PAGE_LIMIT);
            	params.put("orderBy","helped_num");
            	PageQueryUtil pageUtil = new PageQueryUtil(params);
            	PageResult Result = newBeeMallGoodsService.getHelpedNumListEntityByGoodsId(pageUtil);                           
                return ResultGenerator.genSuccessResult(Result);                  
            }
              
            @RequestMapping(value = "/goods/insertGoodsQa", method = RequestMethod.POST)
            @ResponseBody
            public Result insertGoodsQa(@RequestBody GoodsQa qaRecord) { 
               Integer count = null;
               Long qaId = newBeeMallGoodsService.getMaxQaId(qaRecord.getGoodsId());
               qaRecord.setId(qaId);
               Date submiDate = new Date();
               qaRecord.setSubmitDate(submiDate);            	  
            	 if(qaRecord !=null) {
            		 count = newBeeMallGoodsService.insertGoodsQa(qaRecord);
                 }
            	 if(!(count > 0)) {
            	    return ResultGenerator.genFailResult("投稿失败");
            	 }
                    return ResultGenerator.genSuccessResult(count);           
    
              }
            
            @RequestMapping(value = "/goods/showMoreReviews", method = RequestMethod.POST)
            @ResponseBody
            public Result showMoreReviews(@RequestBody Long goodsId) { 
            	List<GoodsReviewVO> reviewVoList = newBeeMallGoodsService.getGoodsReviews(goodsId);
            	List<GoodsReviewVO>	subReviewList = reviewVoList.subList(3, reviewVoList.size()-1); 
                   return ResultGenerator.genSuccessResult(reviewVoList);           
    
             }
            @RequestMapping(value = "/goods/helpNum", method = RequestMethod.POST)
            @ResponseBody
            public Result helpNum(@RequestBody HelpNum goodsReviewHelpNum,HttpSession httpSession) { 
            	  NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
            	  if(user!=null) {
            		  goodsReviewHelpNum.setUserId(user.getUserId());
            	  }
            	  boolean addFlag = newBeeMallGoodsService.addHelpNum(goodsReviewHelpNum);
            	  if(addFlag) {
            		  boolean updateFlag = newBeeMallGoodsService.updateReviewNum(goodsReviewHelpNum);
            		  if(updateFlag) {
            		          long helpNum = newBeeMallGoodsService.getGoodsReviewHelpNum(goodsReviewHelpNum.getReviewId());
            		          return ResultGenerator.genSuccessResult(helpNum);
            	      }else {
            		  return ResultGenerator.genFailResult("失败");
            	     }
            	 }else{
            		  return ResultGenerator.genFailResult("插入失败");
            	  }
            }
            @RequestMapping(value = "/searchHistory/getSearchHistory", method = RequestMethod.POST)
            @ResponseBody
            public Result getSearchHistory(HttpSession httpSession) {
            	List<NewBeeMallGoods> list = new ArrayList<NewBeeMallGoods>();
            	NewBeeMallGoods goods1 = new NewBeeMallGoods();
            	NewBeeMallGoods goods2 = new NewBeeMallGoods();
            	NewBeeMallGoods goods3 = new NewBeeMallGoods();
            	goods1.setGoodsId(10700L);
            	goods1.setGoodsName("家电");
            	list.add(goods1);
            	goods2.setGoodsId(10003L);
            	goods2.setGoodsName("手机");
            	list.add(goods2);
            	goods3.setGoodsId(10004L);
            	goods3.setGoodsName("电器");
            	list.add(goods3);
            	return ResultGenerator.genSuccessResult(list);
             }
				
            // 5/11
			
			  @RequestMapping(value ="/goods/insertKeyword", method = RequestMethod.POST)
			  @ResponseBody
			  public Result insertKeyword(@RequestBody SearchHistory keywordId, HttpSession httpSession) {
					 NewBeeMallUserVO user = (NewBeeMallUserVO) httpSession.getAttribute(Constants.MALL_USER_SESSION_KEY);
					  if(user != null) {
					      keywordId.setUserId(user.getUserId());
					  }
						/*
						 * SimpleDateFormat i = new SimpleDateFormat();
						 * i.applyPattern("yyyy-MM-dd HH:mm:ss a");
						 */
						Date date = new Date();
					    Integer count = null;  
				        Long keyWordId = newBeeMallGoodsService.getMaxKeyWordId(keywordId.getUserId());
				        keywordId.setId(keyWordId);
				        keywordId.setDate(date);
				        if(keywordId != null) {
				            count = newBeeMallGoodsService.insertSearchHistory(keywordId);
				        }
				        if(!(count > 0))  {
				        return ResultGenerator.genFailResult("投稿失敗！");
				        }      
				        return ResultGenerator.genSuccessResult(count);    
				}
			 
			
			  
	             
}
