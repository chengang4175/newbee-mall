/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.GoodsReviewVO;
import ltd.newbee.mall.controller.vo.NewBeeMallGoodsDetailVO;
import ltd.newbee.mall.controller.vo.NewBeeMallSearchGoodsVO;
import ltd.newbee.mall.controller.vo.SearchHistoryVO;
import ltd.newbee.mall.dao.NewBeeMallGoodsMapper;
import ltd.newbee.mall.entity.GoodsQa;
import ltd.newbee.mall.entity.GoodsDesc;
import ltd.newbee.mall.entity.GoodsImage;
import ltd.newbee.mall.entity.GoodsReview;
import ltd.newbee.mall.entity.HelpNum;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.SearchHistory;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import ltd.newbee.mall.entity.GoodsCoupon;
import ltd.newbee.mall.entity.GoodsSale;
import ltd.newbee.mall.entity.TbCategory;
import ltd.newbee.mall.entity.TbSale;

@Service
public class NewBeeMallGoodsServiceImpl implements NewBeeMallGoodsService {

    @Autowired
    private NewBeeMallGoodsMapper goodsMapper;
	private PageQueryUtil pegeUtil;

    @Override
    public PageResult getNewBeeMallGoodsPage(PageQueryUtil pageUtil) {
        List<NewBeeMallGoods> goodsList = goodsMapper.findNewBeeMallGoodsList(pageUtil);
        int total = goodsMapper.getTotalNewBeeMallGoods(pageUtil);
        PageResult pageResult = new PageResult(goodsList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveNewBeeMallGoods(NewBeeMallGoods goods) {
        if (goodsMapper.insertSelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public void batchSaveNewBeeMallGoods(List<NewBeeMallGoods> newBeeMallGoodsList) {
        if (!CollectionUtils.isEmpty(newBeeMallGoodsList)) {
            goodsMapper.batchInsert(newBeeMallGoodsList);
        }
    }

    @Override
    public String updateNewBeeMallGoods(NewBeeMallGoods goods) {
        NewBeeMallGoods temp = goodsMapper.selectByPrimaryKey(goods.getGoodsId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        goods.setUpdateTime(new Date());
        if (goodsMapper.updateByPrimaryKeySelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public NewBeeMallGoods getNewBeeMallGoodsById(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }
    
    @Override
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return goodsMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
    }

    @Override
    public PageResult searchNewBeeMallGoods(PageQueryUtil pageUtil) {
        List<NewBeeMallGoods> goodsList = goodsMapper.findNewBeeMallGoodsListBySearch(pageUtil);
        int total = goodsMapper.getTotalNewBeeMallGoodsBySearch(pageUtil);
        List<NewBeeMallSearchGoodsVO> newBeeMallSearchGoodsVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(goodsList)) {
            newBeeMallSearchGoodsVOS = BeanUtil.copyList(goodsList, NewBeeMallSearchGoodsVO.class);
            for (NewBeeMallSearchGoodsVO newBeeMallSearchGoodsVO : newBeeMallSearchGoodsVOS) {
                String goodsName = newBeeMallSearchGoodsVO.getGoodsName();
                String goodsIntro = newBeeMallSearchGoodsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 28) {
                    goodsName = goodsName.substring(0, 28) + "...";
                    newBeeMallSearchGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 30) {
                    goodsIntro = goodsIntro.substring(0, 30) + "...";
                    newBeeMallSearchGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        PageResult pageResult = new PageResult(newBeeMallSearchGoodsVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }
    
    
    
    
	@Override
    public List<GoodsImage> getGoodsImageEntityByGoodsId(Long goodsId) {
           List<GoodsImage> list = goodsMapper.getGoodsImageList(goodsId);
           return list;
    }

	@Override
	public GoodsDesc getGoodsDescEntityByGoodsId(Long goodsId) {
		   GoodsDesc goodsDesc = goodsMapper.getGoodsDesc(goodsId);
		   return goodsDesc;
	}

	@Override
	public List<GoodsQa> getGoodsQaEntityByGoodsId(Long goodsId) {
		   List<GoodsQa> list = goodsMapper.getGoodsQaList(goodsId);
	       return list;
	}

	@Override
	public List<GoodsReview> getGoodsReviewEntityByGoodsId(Long goodsId) {
		List<GoodsReview> list = goodsMapper.getGoodsReviewList(goodsId);
		return list;
	}
//分页功能
	@Override
	public PageResult getGoodsQaEntityByGoodsId(PageQueryUtil pageUtil) {
		List <GoodsQa> qaPageList = goodsMapper.getGoodsQaPageList(pageUtil);
		int total = goodsMapper.getGoodsQacount(pageUtil);
		PageResult pageResult = new PageResult(qaPageList,total,pageUtil.getLimit(),pageUtil.getPage());
		return pageResult;
	}
//页面排序
	@Override
	public PageResult getHelpedNumListEntityByGoodsId(PageQueryUtil pageUtil) {
		List <GoodsQa> qaSubmitDateList = goodsMapper.getHelpedNumList(pageUtil);
		int total = goodsMapper.getGoodsQacount(pageUtil);
		PageResult pegeResult = new PageResult(qaSubmitDateList,total,pageUtil.getLimit(),pageUtil.getPage());
		return pegeResult;
	}	
	@Override	
	public int insertGoodsQa(GoodsQa qaRecord) {
		int count = goodsMapper.insertGoodsQa(qaRecord);
		return count;
	}
	@Override
	public Long getMaxQaId(Long goodsId) {
		Long maxGoodsId = goodsMapper.getMaxQaId(goodsId);
		if(maxGoodsId !=null) {
			return maxGoodsId + 1;
		}else {
		return 1L;
	    }
	}

	@Override
	public List<GoodsReviewVO> getGoodsReviews(Long goodsId) {
		List<GoodsReview> entityList = goodsMapper.getGoodsReviews(goodsId);
		List<GoodsReviewVO> reviewVoList = BeanUtil.copyList(entityList,GoodsReviewVO.class);
		return reviewVoList;
	}
//参考人数
	
	@Override
	public long getGoodsReviewHelpNum(int reviewId) {
		
		return goodsMapper.getGoodsReviewHelpNum(reviewId);
	}

	@Override
	public boolean addHelpNum(HelpNum goodsReviewHelpNum) {
		
		return goodsMapper.insertHelpNum(goodsReviewHelpNum);
	}

	@Override
	public boolean updateReviewNum(HelpNum goodsReviewHelpNum) {
		
		return goodsMapper.updateReviewNum(goodsReviewHelpNum);
	}
//暧昧显示
	
	/*
	 * @Override public List<NewBeeMallGoods>
	 * getTotalNewBeeMallGoodsName(NewBeeMallGoods goodsName) {
	 * List<NewBeeMallSearchGoodsVO> newBeeMallSearchGoodsVOS = new ArrayList<>();
	 * List<NewBeeMallGoods> total =
	 * goodsMapper.getTotalNewBeeMallGoodsName(goodsName); if
	 * (!CollectionUtils.isEmpty(newBeeMallSearchGoodsVOS)) {
	 * newBeeMallSearchGoodsVOS = BeanUtil.copyList(newBeeMallSearchGoodsVOS,
	 * NewBeeMallSearchGoodsVO.class); for (NewBeeMallSearchGoodsVO
	 * newBeeMallSearchGoodsVO : newBeeMallSearchGoodsVOS) { String goodsName1 =
	 * newBeeMallSearchGoodsVO.getGoodsName(); String goodsIntro =
	 * newBeeMallSearchGoodsVO.getGoodsIntro(); // 字符串过长导致文字超出的问题 if
	 * (goodsName1.length() > 28) { goodsName1 = goodsName1.substring(0, 28) +
	 * "..."; newBeeMallSearchGoodsVO.setGoodsName(goodsName1); } if
	 * (goodsIntro.length() > 30) { goodsIntro = goodsIntro.substring(0, 30) +
	 * "..."; newBeeMallSearchGoodsVO.setGoodsIntro(goodsIntro); } } }
	 * 
	 * return goodsMapper.getTotalNewBeeMallGoodsName(goodsName);
	 * 
	 * }
	 */
	
	@Override
	public PageResult findHitGoodsList(PageQueryUtil pageUtil) {
		  List<NewBeeMallGoods> goodsList = goodsMapper.findHitGoodsList(pageUtil);
	       // int total = goodsMapper.getTotalNewBeeMallGoods(pageUtil);
	        PageResult pageResult = new PageResult(goodsList, 10, pageUtil.getLimit(), pageUtil.getPage());
	        return pageResult;
	}
	//
	
	@Override
	public int insertSearchHistory(SearchHistory keywordId) {
	    int count = goodsMapper.insertSearchHistory(keywordId);
	    return count;
	}
	//getMaxKeywordID
	@Override
	public Long getMaxKeyWordId(Long userId) {
	    Long a = goodsMapper.getMaxKeyWordId(userId);
	     if(a !=null) {
	     return a + 1;
	     }else {
	       return 1L;
	     }
	}
	//5/11
  
	
	@Override
	public List<TbSale> TbSale(Long id) {
	    List<TbSale> list = goodsMapper.getTbSale(id); 
	    return list;
	}

	@Override
	public List<TbCategory> TbCategory(Long id) {
	    List<TbCategory> list = goodsMapper.getTbCategory(id);
	    return list;
	}

	@Override
	public List<GoodsSale> GoodsSale(Long id) {
	    List<GoodsSale> list = goodsMapper.getGoodsSale(id); 
	    return list;
	}

	@Override
	public List<GoodsCoupon> GoodsCoupon(Long couponId) {
	     List<GoodsCoupon> list = goodsMapper.getGoodsCoupon(couponId);
	    return list;
	}
   //insert 2021/05/11
	@Override
	public int insertTbSale(TbSale id) {
            int count = goodsMapper.insertTbSale(id);
	    return count;
	}

	@Override
	public int insertTbCategory(TbCategory id) {
	    int count = goodsMapper.insertTbCategory(id);
	    return count;
	}


	@Override
	public int insertGoodsSale(GoodsSale id) {
	    int count = goodsMapper.insertGoodsSale(id);
	    return count;
	}

	@Override
	public int insertGoodsCoupon(GoodsCoupon couponId) {
	    int count = goodsMapper.insertGoodsCoupon(couponId);
	    return count;
	}
	//5/14
	@Override
	public PageResult getTbSaleDownload(PageQueryUtil pageUtil) {
		   List<TbCategory> goodsList = goodsMapper.getTbSaleDownload(pageUtil);
	        PageResult pageResult = new PageResult(goodsList, 10, pageUtil.getLimit(), pageUtil.getPage());
	        return pageResult;
	}
}

	

	

