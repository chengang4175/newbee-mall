/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import ltd.newbee.mall.entity.GoodsQa;
import ltd.newbee.mall.entity.GoodsCoupon;
import ltd.newbee.mall.entity.GoodsDesc;
import ltd.newbee.mall.entity.GoodsImage;
import ltd.newbee.mall.entity.GoodsReview;
import ltd.newbee.mall.entity.GoodsSale;
import ltd.newbee.mall.entity.HelpNum;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.SearchHistory;
import ltd.newbee.mall.entity.StockNumDTO;
import ltd.newbee.mall.entity.TbCategory;
import ltd.newbee.mall.entity.TbSale;
import ltd.newbee.mall.util.PageQueryUtil;

public interface NewBeeMallGoodsMapper {
    int deleteByPrimaryKey(Long goodsId);

    int insert(NewBeeMallGoods record);

    int insertSelective(NewBeeMallGoods record);

    NewBeeMallGoods selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(NewBeeMallGoods record);

    int updateByPrimaryKeyWithBLOBs(NewBeeMallGoods record);

    int updateByPrimaryKey(NewBeeMallGoods record);

    List<NewBeeMallGoods> findNewBeeMallGoodsList(PageQueryUtil pageUtil);

    int getTotalNewBeeMallGoods(PageQueryUtil pageUtil);

    List<NewBeeMallGoods> selectByPrimaryKeys(List<Long> goodsIds);

    List<NewBeeMallGoods> findNewBeeMallGoodsListBySearch(PageQueryUtil pageUtil);
    //检索
    List<NewBeeMallGoods> findHitGoodsList(PageQueryUtil pageUtil);

    int getTotalNewBeeMallGoodsBySearch(PageQueryUtil pageUtil);

    int batchInsert(@Param("newBeeMallGoodsList") List<NewBeeMallGoods> newBeeMallGoodsList);

    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int batchUpdateSellStatus(@Param("orderIds")Long[] orderIds,@Param("sellStatus") int sellStatus);
    
    
    
//获得图片列表
    List<GoodsImage> getGoodsImageList(Long goodsId);
//获取评论列表
    List<GoodsReview> getGoodsReviewList(Long goodsId);
//获得Qa列表
    List<GoodsQa> getGoodsQaList(Long goodsId);
//获得说明
    GoodsDesc getGoodsDesc(Long goodsId);
//分页功能
    List<GoodsQa> getGoodsQaPageList(PageQueryUtil pageUtil);
    int getGoodsQacount(PageQueryUtil pageUtil);
//四月二十四日页面排序
    List<GoodsQa> getHelpedNumList(PageQueryUtil pageUtil);
//四月二十四日页面内容输入
    int insertGoodsQa(GoodsQa qaRecord);
    Long getMaxQaId(Long goodsId);
//展开更多评论s
    List<GoodsReview> getGoodsReviews(Long goodsId);
//参考人数
    boolean insertHelpNum(HelpNum goodsReviewaHelpNum);
    boolean updateReviewNum(HelpNum goodsReviewaHelpNum);
    long getGoodsReviewHelpNum(int reviewId);
//
	/*
	 * List<NewBeeMallGoods> getTotalNewBeeMallGoodsName(NewBeeMallGoods goodsName);
	 */

//
    int insertSearchHistory(SearchHistory keywordId);
    //getMaxKeywordID
    Long getMaxKeyWordId(Long userId);
    //sale 2021/05/11
    List<TbSale> getTbSale(Long id); 
    List<TbCategory> getTbCategory(Long id);
    List<GoodsSale> getGoodsSale(Long id);   
    List<GoodsCoupon> getGoodsCoupon(Long couponId);
    //sale insert 2021/05/11
    int insertTbSale(TbSale id);
    int insertTbCategory(TbCategory id);
    int insertGoodsSale(GoodsSale id);
    int insertGoodsCoupon(GoodsCoupon couponId);
//2021/05/14
    List<TbSale> getTbSaleDownload(Integer[] ids);  
//2021/05/17
    List<GoodsSale> goodsSalePagAndSort(PageQueryUtil pageUtil);
    int getGoodsSale(PageQueryUtil pageUtil);
}
 