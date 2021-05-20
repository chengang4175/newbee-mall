/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.service;

import java.text.SimpleDateFormat;
import java.util.List;

import ltd.newbee.mall.entity.GoodsQa;
import ltd.newbee.mall.controller.vo.GoodsReviewVO;
import ltd.newbee.mall.controller.vo.SearchHistoryVO;
import ltd.newbee.mall.entity.GoodsDesc;
import ltd.newbee.mall.entity.GoodsImage;
import ltd.newbee.mall.entity.GoodsReview;
import ltd.newbee.mall.entity.HelpNum;
import ltd.newbee.mall.entity.NewBeeMallGoods;
import ltd.newbee.mall.entity.SearchHistory;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import ltd.newbee.mall.entity.GoodsCoupon;
import ltd.newbee.mall.entity.GoodsSale;
import ltd.newbee.mall.entity.TbCategory;
import ltd.newbee.mall.entity.TbSale;


public interface NewBeeMallGoodsService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewBeeMallGoodsPage(PageQueryUtil pageUtil);

    /**
     * 添加商品
     *
     * @param goods
     * @return
     */
    String saveNewBeeMallGoods(NewBeeMallGoods goods);

    /**
     * 批量新增商品数据
     *
     * @param newBeeMallGoodsList
     * @return
     */
    void batchSaveNewBeeMallGoods(List<NewBeeMallGoods> newBeeMallGoodsList);

    /**
     * 修改商品信息
     *
     * @param goods
     * @return
     */
    String updateNewBeeMallGoods(NewBeeMallGoods goods);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
    NewBeeMallGoods getNewBeeMallGoodsById(Long id);

    /**
     * 批量修改销售状态(上架下架)
     *
     * @param ids
     * @return
     */
    Boolean batchUpdateSellStatus(Long[] ids,int sellStatus);

    /**
     * 商品搜索
     *
     * @param pageUtil
     * @return
     */
    PageResult searchNewBeeMallGoods(PageQueryUtil pageUtil);
   
    List<GoodsQa> getGoodsQaEntityByGoodsId(Long goodsId);
    List<GoodsReview> getGoodsReviewEntityByGoodsId(Long goodsId);
    List<GoodsImage> getGoodsImageEntityByGoodsId(Long goodsId);
    GoodsDesc getGoodsDescEntityByGoodsId(Long goodsId);
//关于分页
    PageResult getGoodsQaEntityByGoodsId(PageQueryUtil pageUtil);
//页面排序
    PageResult getHelpedNumListEntityByGoodsId(PageQueryUtil pageUtil);
//页面内容输入实现
    int insertGoodsQa(GoodsQa qaRecord);
    Long getMaxQaId(Long goodsId);
//
    List<GoodsReviewVO> getGoodsReviews(Long goodsId);
//参考人数
    long getGoodsReviewHelpNum(int reviewId);

	boolean addHelpNum(HelpNum goodsReviewHelpNum);

	boolean updateReviewNum(HelpNum goodsReviewHelpNum); 
//
	//List<NewBeeMallGoods> getTotalNewBeeMallGoodsName(NewBeeMallGoods goodsName);

	PageResult findHitGoodsList(PageQueryUtil pageUtil);
//
	int insertSearchHistory(SearchHistory keywordId);
    //getMaxKeywordID
    Long getMaxKeyWordId(Long userId);
    //20210511
    List<TbSale>TbSale(Long id); 
    List<TbCategory>TbCategory(Long id);
    List<GoodsSale>GoodsSale(Long id);   
    List<GoodsCoupon>GoodsCoupon(Long couponId);
    //insert 20210511
    int insertTbSale(TbSale id);
    int insertTbCategory(TbCategory id);
    int insertGoodsSale(GoodsSale id);
    int insertGoodsCoupon(GoodsCoupon couponId);
  //20210514
     
    List<TbSale> getTbSaleDownload(Integer[] ids);
    List<GoodsSale> getGoodsSaleDownload(Integer[] ids);
  //20210517

    PageResult goodsSalePagAndSort(PageQueryUtil pageUtil);

	
	
}
