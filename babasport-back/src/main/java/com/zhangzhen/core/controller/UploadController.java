package com.zhangzhen.core.controller;

import com.zhangzhen.common.fastdfs.FastDFSUtils;
import com.zhangzhen.core.web.Constants;
import net.fckeditor.response.UploadResponse;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

/**
 * 上传图片
 * 
 * @author lx
 *
 */
@Controller
public class UploadController {

	// 上传图片
	@RequestMapping(value = "/upload/uploadPic.do")
	public void uploadPic(@RequestParam MultipartFile pic, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		 * //图片名生成策略 //采用时间格式精确到毫秒，并追加随机三位（10以内的数） DateFormat df = new
		 * SimpleDateFormat("yyyyMMddHHmmSSsss"); String picName = df.format(new
		 * Date()); //在随机生成3为10以内的数 Random r = new Random(); for (int i = 0; i <
		 * 3; i++) { picName += r.nextInt(10); } //获取扩展名 String originalFilename
		 * = pic.getOriginalFilename(); String ext =
		 * FilenameUtils.getExtension(originalFilename); //图片的上传路径 String path =
		 * "/upload/"+picName+"."+ext; //上传图片到指定位置 pic.transferTo(new
		 * File(request.getSession().getServletContext().getRealPath(""+path)));
		 * //json对象 JSONObject jo = new JSONObject(); jo.put("path", path);
		 * 
		 * //设置响应头信息JSon
		 * response.setContentType("application/json;charset=utf-8");
		 * //在响应中写入JSON格式的字符串 response.getWriter().write(jo.toString());
		 */
		String path = FastDFSUtils.uploadPic(pic);
		String url = Constants.IMG_URL+path;
		// json对象
		JSONObject jo = new JSONObject();
		jo.put("path", path);
		jo.put("url", url);
		// 设置响应头信息JSon
		response.setContentType("application/json;charset=utf-8");
		// 在响应中写入JSON格式的字符串
		response.getWriter().write(jo.toString());
		System.out.println(path);
	}

	public static void main(String[] args) {
		System.out.println(new Random().nextInt(10));
	}
	
	//上传Fck
	@RequestMapping("/upload/uploadFck.do")
	public void uploadFck(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//强转request spring提供的multipartRequest
		MultipartRequest mr = (MultipartRequest) request;
		//获取fileMap
		Map<String, MultipartFile> fileMap = mr.getFileMap();
		//遍历map
		Set<Entry<String,MultipartFile>> entrySet = fileMap.entrySet();
		for (Entry<String, MultipartFile> entry : entrySet) {
			//获取
			MultipartFile pic = entry.getValue();
			//FastDFSUtils上传图片
			String path = FastDFSUtils.uploadPic(pic);
			//显示图片URL
			String url = Constants.IMG_URL+path;
			//返回Fck的上传路径
			UploadResponse ok = UploadResponse.getOK(url);
			//write()方法和print()方法的区别
			//write()方法仅支持字符串和字符数组的输出
			//print()方法支持各种数据类型，包括对象的输出，其实
			//其原理还是通过将对象或者字符数据转换成字节数据进行输出的
			//print()输出的字节流
			response.getWriter().print(ok);
//			response.getWriter().write("");
		}
	}
}