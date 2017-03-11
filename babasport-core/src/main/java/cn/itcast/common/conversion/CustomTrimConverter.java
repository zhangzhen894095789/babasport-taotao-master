package cn.itcast.common.conversion;

import org.springframework.core.convert.converter.Converter;

/**
 * 处理传参 字符串
 * @author lx
 * String类型转String类型
 *
 */
public class CustomTrimConverter implements Converter<String, String> {

	@Override
	public String convert(String source) {
		try {
			if (source != null) {
				source = source.trim();
				if (!"".equals(source)) {
					//这样的话source本身就是一个空串，再去掉空串还是空串
					return source;
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//测试空串在trim()后还是空串
	public static void main(String[] args) {
		System.out.println("".trim());
	}

}
