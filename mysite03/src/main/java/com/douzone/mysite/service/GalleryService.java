package com.douzone.mysite.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.repository.GalleryRepository;
import com.douzone.mysite.vo.GalleryVo;

@Service
public class GalleryService {
	@Autowired
	private GalleryRepository galleryRepository;
	private static String SAVE_PATH = "/mysite-upload-images";
	private static String URL_BASE = "/images";
	
    public String upload(MultipartFile multipartFile,String comments) {
    	String url = null;
    	
    	/**디렉토리없으면 생성**/
    	File uploadDirectory = new File( SAVE_PATH);
		if(!uploadDirectory.exists()) {
			uploadDirectory.mkdir();
		}
    	
    	if(multipartFile.isEmpty()) {
    		return url;
    	}	
    	
    	/**서버에 다른 이름으로 저장하기 위해**/
    	String originFilename =  multipartFile.getOriginalFilename();
    	String extName = originFilename.substring( originFilename.lastIndexOf('.')+1); //확장자 가져오기
    	String saveFilename = generateSaveFilename(extName);
    	long fileSize = multipartFile.getSize();
    	
    	System.out.println("#############" + originFilename);
		System.out.println("#############" + fileSize);
		System.out.println("#############" + saveFilename);
		
		/**서버에 저장**/
		try {
			byte[] data = multipartFile.getBytes();
			OutputStream os = new FileOutputStream( SAVE_PATH + "/" + saveFilename);
			os.write(data);
			os.close();
			
			url = URL_BASE + "/" + saveFilename;
		} catch (IOException e) {
			throw new RuntimeException("file upload error:" + e);
		}
		
		/**db에 저장 **/
        GalleryVo vo = new GalleryVo();
        vo.setUrl("/gallery"+URL_BASE + "/" + saveFilename);
        vo.setComments(comments);
		galleryRepository.insert(vo);
			
    	return url;
    }

	private String generateSaveFilename(String extName) {
		String filename = "";
		
		Calendar calendar = Calendar.getInstance();
		
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
		
		
		return filename;
	}

	public List<GalleryVo> findAllUrl() {
		return galleryRepository.findAll();
	}
}
