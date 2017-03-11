package cn.itcast.core.service.staticpage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 静态化服务类
 * @author Administrator
 * 
 */
public class StaticPageServiceImpl implements StaticPageService,ServletContextAware {
	//创建framaker实例,获取configuration的全过程
	private Configuration conf;
	private FreeMarkerConfigurer freeMarkerConfigurer;
	//提供springmvc提供的freemarkerConfigurer获取configuration
	//spring的依赖注入获取freemarker中的configuration
	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		conf= freeMarkerConfigurer.getConfiguration();
	}
	
	//静态化方法
	//传进来数据，和商品id，数据加模板经过freemarker 中间过滤输出静态 页面，root参数存放的是数据
	//该方法直接调用即可根据传入的数据将其进行 页面的静态化
	public void index(Map<String,Object>root,Long  id) {
		//获取到全路径
		String path = getPath("/html/product/"+id+".html");
		//获取父文件路径
		File f = new File(path);
		File parentFile = f.getParentFile();
		//判断父文件是否存在
		if (!parentFile.exists()) {
			//父文件的目录不存在就创建一个否则会报文件找不到的异常
			parentFile.mkdirs();
		}
		Writer out = null;
		try {
			//流写的过程指定编码集,防止页面中的中文乱码
			out = new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
			//指定模板返回模板对象
			Template template = conf.getTemplate("productDetail.html");
			template.process(root, out);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String getPath(String path) {
		return servletContext.getRealPath(path);
	}
	
	private ServletContext servletContext;
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
}
