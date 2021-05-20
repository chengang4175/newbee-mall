/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本系统已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package ltd.newbee.mall.controller.common;

import ltd.newbee.mall.common.Constants;
import ltd.newbee.mall.common.NewBeeMallException;
import ltd.newbee.mall.common.ServiceResultEnum;
import ltd.newbee.mall.controller.vo.GoodsQaVO;
import ltd.newbee.mall.controller.vo.GoodsSaleVO;
import ltd.newbee.mall.controller.vo.SearchPageCategoryVO;
import ltd.newbee.mall.entity.GoodsQa;
import ltd.newbee.mall.entity.GoodsSale;
import ltd.newbee.mall.entity.PagingQa;
import ltd.newbee.mall.entity.TbSale;
import ltd.newbee.mall.service.NewBeeMallGoodsService;
import ltd.newbee.mall.util.BeanUtil;
import ltd.newbee.mall.util.NewBeeMallUtils;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;
import ltd.newbee.mall.util.Result;
import ltd.newbee.mall.util.ResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/newbee-ltd
 */
@Controller
@RequestMapping("/admin")
public class UploadController {
	@Resource
	private NewBeeMallGoodsService newBeeMallGoodsService;
	@Autowired
	private StandardServletMultipartResolver standardServletMultipartResolver;

	@PostMapping({ "/upload/file" })
	@ResponseBody
	public Result upload(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file)
			throws URISyntaxException {
		String fileName = file.getOriginalFilename();
		String suffixName = fileName.substring(fileName.lastIndexOf("."));
		// 生成文件名称通用方法
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		Random r = new Random();
		StringBuilder tempName = new StringBuilder();
		tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
		String newFileName = tempName.toString();
		File fileDirectory = new File(Constants.FILE_UPLOAD_DIC);
		// 创建文件
		File destFile = new File(Constants.FILE_UPLOAD_DIC + newFileName);
		try {
			if (!fileDirectory.exists()) {
				if (!fileDirectory.mkdir()) {
					throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
				}
			}
			file.transferTo(destFile);
			Result resultSuccess = ResultGenerator.genSuccessResult();
			resultSuccess.setData(NewBeeMallUtils.getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/upload/"
					+ newFileName);
			return resultSuccess;
		} catch (IOException e) {
			e.printStackTrace();
			return ResultGenerator.genFailResult("文件上传失败");
		}
	}

	@PostMapping({ "/upload/files" })
	@ResponseBody
	public Result uploadV2(HttpServletRequest httpServletRequest) throws URISyntaxException {
		List<MultipartFile> multipartFiles = new ArrayList<>(8);
		if (standardServletMultipartResolver.isMultipart(httpServletRequest)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) httpServletRequest;
			Iterator<String> iter = multiRequest.getFileNames();
			int total = 0;
			while (iter.hasNext()) {
				if (total > 5) {
					return ResultGenerator.genFailResult("最多上传5张图片");
				}
				total += 1;
				MultipartFile file = multiRequest.getFile(iter.next());
				multipartFiles.add(file);
			}
		}
		if (CollectionUtils.isEmpty(multipartFiles)) {
			return ResultGenerator.genFailResult("参数异常");
		}
		if (multipartFiles != null && multipartFiles.size() > 5) {
			return ResultGenerator.genFailResult("最多上传5张图片");
		}
		List<String> fileNames = new ArrayList(multipartFiles.size());
		for (int i = 0; i < multipartFiles.size(); i++) {
			String fileName = multipartFiles.get(i).getOriginalFilename();
			String suffixName = fileName.substring(fileName.lastIndexOf("."));
			// 生成文件名称通用方法
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			Random r = new Random();
			StringBuilder tempName = new StringBuilder();
			tempName.append(sdf.format(new Date())).append(r.nextInt(100)).append(suffixName);
			String newFileName = tempName.toString();
			File fileDirectory = new File(Constants.FILE_UPLOAD_DIC);
			// 创建文件
			File destFile = new File(Constants.FILE_UPLOAD_DIC + newFileName);
			try {
				if (!fileDirectory.exists()) {
					if (!fileDirectory.mkdir()) {
						throw new IOException("文件夹创建失败,路径为：" + fileDirectory);
					}
				}
				multipartFiles.get(i).transferTo(destFile);
				fileNames.add(NewBeeMallUtils.getHost(new URI(httpServletRequest.getRequestURL() + "")) + "/upload/"
						+ newFileName);
			} catch (IOException e) {
				e.printStackTrace();
				return ResultGenerator.genFailResult("文件上传失败");
			}
		}
		Result resultSuccess = ResultGenerator.genSuccessResult();
		resultSuccess.setData(fileNames);
		return resultSuccess;
	}

//csv文件
	@PostMapping({"/uploadtest/file"})
    @ResponseBody
    public Result uploadTest(HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file) throws URISyntaxException, ParseException {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
            InputStream is = file.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String s = null;
            while((s = br.readLine())!=null) {
             String[] data = s.split(",");// 切割
                // 放入方法
                GoodsSale saleId = new GoodsSale();
                saleId.setId(Long.parseLong(data[0]));
                saleId.setName(data[1]);
                saleId.setStartDate(formatter.parse(data[2]));
                saleId.setEndDate(formatter.parse(data[3]));
                int flag = newBeeMallGoodsService.insertGoodsSale(saleId);
            }
            br.close();
              }
         catch (IOException e) {
            e.printStackTrace();
        }
        return ResultGenerator.genSuccessResult();
    }    

	/*
	 * @PostMapping({ "/uploadtext/file" })
	 * 
	 * @ResponseBody public Result uploadtext(HttpServletRequest
	 * httpServletRequest, @RequestParam("file") MultipartFile file) throws
	 * URISyntaxException, ParseException { try { SimpleDateFormat sdFormat = new
	 * SimpleDateFormat("yyyy/MM/dd"); Integer count = null; InputStream is =
	 * file.getInputStream(); BufferedReader br = new BufferedReader(new
	 * InputStreamReader(is)); String line; while ((line = br.readLine()) != null) {
	 * String[] arr = line.split(","); // 放入方法 TbSale tbEntity = new TbSale();
	 * tbEntity.setId(Long.parseLong(arr[0]));
	 * tbEntity.setGoodsId(Long.parseLong(arr[1]));
	 * tbEntity.setStartDate(sdFormat.parse(arr[2]));
	 * tbEntity.setEndDate(sdFormat.parse(arr[3])); // 插入服务 if (tbEntity != null) {
	 * count = newBeeMallGoodsService.insertTbSale(tbEntity);
	 * 
	 * } if (!(count > 0)) { return ResultGenerator.genFailResult(""); } return
	 * ResultGenerator.genSuccessResult(count);
	 * 
	 * } br.close();
	 * 
	 * } catch (IOException e) { e.printStackTrace();
	 * 
	 * } return ResultGenerator.genSuccessResult(); }
	 */
	// 五月十四日下载
	/*
	 * @RequestMapping(value = "/Download/file", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public Result Download(@RequestBody Integer[] ids){ File f =
	 * new File("D:\\zhaopian\\upload\\text.csv"); SimpleDateFormat sdFormat = new
	 * SimpleDateFormat("yyyy/MM/dd");
	 * 
	 * try { BufferedWriter bw = new BufferedWriter(new FileWriter(f)); List<TbSale>
	 * tbSale = newBeeMallGoodsService.getTbSaleDownload(ids);
	 * tbSale.stream().forEach( c -> { try { bw.write(c.toString()); bw.newLine();
	 * 
	 * }catch (IOException e) { e.printStackTrace(); }
	 * 
	 * }); bw.close(); }catch (IOException e) { e.printStackTrace(); }
	 * 
	 * Result resultSuccess = ResultGenerator.genSuccessResult();
	 * resultSuccess.setData("/upload/text.csv"); return resultSuccess; }
	 */
	@RequestMapping(value = "/goodsSale/download", method = RequestMethod.POST)
	@ResponseBody
	public Result download(@RequestBody Integer[] ids) throws URISyntaxException, ParseException {
		File f = new File("D:\\zhaopian\\upload\\text.csv");
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f));
			List<GoodsSale> list = newBeeMallGoodsService.getGoodsSaleDownload(ids);
			list.stream().forEach(c -> {
				try {
					bw.write(c.toString());
					bw.newLine();// 空一行
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Result resultSuccess = ResultGenerator.genSuccessResult();
		resultSuccess.setData("/upload/text2.csv");
		return resultSuccess;
	}

	// 2021/05/17
	@GetMapping({ "/goods/sale", "/sale.html" })
	public String searchPage(@RequestParam Map<String, Object> params, HttpServletRequest request) {
		if (StringUtils.isEmpty(params.get("page"))) {
			params.put("page", 1);
		}
		params.put("limit", 3);
		// 封装参数供前端回显
		if (params.containsKey("orderBy") && !StringUtils.isEmpty(params.get("orderBy") + "")) {
			request.setAttribute("orderBy", params.get("orderBy") + "");
		}
		String keyword = "";
		// 对keyword做过滤 去掉空格
		if (params.containsKey("keyword") && !StringUtils.isEmpty((params.get("keyword") + "").trim())) {
			keyword = params.get("keyword") + "";
		}
		request.setAttribute("keyword", keyword);
		params.put("keyword", keyword);
		// 封装商品数据
		PageQueryUtil pageUtil = new PageQueryUtil(params);
		request.setAttribute("pageResult", newBeeMallGoodsService.goodsSalePagAndSort(pageUtil));

		/*
		 * Map<String, Object> paramsgs = new HashMap<>(); paramsgs.put("page", "1");
		 * paramsgs.put("limit", "3"); PageQueryUtil gs = new PageQueryUtil(paramsgs);
		 * PageResult rs = newBeeMallGoodsService.goodsSalePagAndSort(gs);
		 * List<GoodsSale> listSale = (List<GoodsSale>) rs.getList(); if (listSale ==
		 * null) {
		 * NewBeeMallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult()); }
		 * List<GoodsSaleVO> goodsSaleVOList = new ArrayList<GoodsSaleVO>(); for (int i
		 * = 0; i < listSale.size(); i++) { GoodsSale sale = new GoodsSale(); sale =
		 * listSale.get(i); if (sale != null) { GoodsSaleVO saleVO = new GoodsSaleVO();
		 * BeanUtil.copyProperties(sale, saleVO); goodsSaleVOList.add(saleVO); } }
		 */
		/* request.setAttribute("goodsSaleDetail", goodsSaleVOList); */
		return "admin/sale";
	}

}
