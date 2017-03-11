package cn.itcast.core.service.staticpage;

import java.util.Map;

public interface StaticPageService {
	//静态化方法
		//传进来数据，和商品id，数据加模板经过freemarker 中间过滤输出静态 页面，root参数存放的是数据
		//该方法直接调用即可根据传入的数据将其进行 页面的静态化
		public void index(Map<String,Object>root,Long  id); 
}
