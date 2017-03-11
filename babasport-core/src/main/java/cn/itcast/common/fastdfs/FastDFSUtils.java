package cn.itcast.common.fastdfs;

import org.apache.commons.io.FilenameUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传图片到分布式文件系统
 * @author Administrator
 * @company
 */
public class FastDFSUtils {
	//上传图片
	public static String uploadPic(MultipartFile pic) throws Exception{
		ClassPathResource resource =new ClassPathResource("fdfs_client.conf");
		//读取配置文件
		ClientGlobal.init(resource.getClassLoader().getResource("fdfs_client.conf").getPath());
		//trackee客户端
		TrackerClient trackerClient= new TrackerClient();
		//与tracker连接
		TrackerServer trackerServer = trackerClient.getConnection();
		StorageServer storageServer = null;
		//创建stoage的客户端
		StorageClient1 storageClient1 = new StorageClient1(trackerServer, storageServer );
		
		String ext = FilenameUtils.getExtension(pic.getOriginalFilename());
		
		NameValuePair[] meta_list = new NameValuePair[3];
		meta_list[0] = new NameValuePair("filename", pic.getOriginalFilename());
		meta_list[1] = new NameValuePair("filelength", String.valueOf(pic.getSize()));
		meta_list[2] = new NameValuePair("ext", ext);
		//上传图片
		String path = storageClient1.upload_file1(pic.getBytes(), ext, meta_list);
		return path;
	}
}
