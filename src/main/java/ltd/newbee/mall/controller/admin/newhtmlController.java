package ltd.newbee.mall.controller.admin;
import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.NewBeeMallCategoryLevelEnum;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.NewBeeMallUserVO;
import ltd.newbee.mall.entity.GoodsCategory;
import ltd.newbee.mall.entity.HelpNum;
import ltd.newbee.mall.entity.GoodsSale;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.service.NewBeeMallCategoryService;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.*;
@Controller
@RequestMapping("/admin")
public class newhtmlController {
	@Resource
	private NewBeeMallGoodsService newBeeMallGoodsService;
	@Resource
	private NewBeeMallCategoryService newBeeMallCategoryService;
	
	
	@GetMapping({ "/goods/newHtml", "/newHtml.html" })
	 public String firstLevel(HttpServletRequest request) {
	  request.setAttribute("path", "edit");
	  // 查询所有的一级分类
	  List<GoodsCategory> firstLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(
	   Collections.singletonList(0L), NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
	  List<GoodsSale> goodsSaleList = newBeeMallGoodsService.GoodsSale();
	  request.setAttribute("goodsSaleList", goodsSaleList);
	  request.setAttribute("firstLevelCategories", firstLevelCategories);
	  request.setAttribute("path", "goods-edit");
	  return "admin/newHtml";
	 }
	/*
	 * @GetMapping({ "/goods/newHtml", "/newHtml.html" }) public String
	 * getFirstLevel(HttpServletRequest request,@RequestBody GoodsCategory
	 * goodsCategory) { GoodsCategory categoryList = new GoodsCategory(); String
	 * parentId = newBeeMallGoodsService.selectByLevelAndParentIdsAnd(null, 0);
	 * categoryList.setParentId(parentId); request.setAttribute(parentId,
	 * categoryList); return null;
	 * 
	 * }
	 */
	
}
