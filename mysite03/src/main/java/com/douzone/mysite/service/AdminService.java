package com.douzone.mysite.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.mysite.repository.SiteRepository;
import com.douzone.mysite.vo.SiteVo;

@Service
public class AdminService {
	private static String SAVE_PATH = "/mysite-upload-images";
	private static String URL_BASE = "/gallery/images";

	@Autowired
	private SiteRepository siteRepository;

	public SiteVo getAdmin() {
		return siteRepository.findAll();
	}

	public SiteVo alterData(MultipartFile multipartFile, SiteVo siteVo) {
		String url = SaveImg(multipartFile, "Saved by admin");
		if(url != null) {
			siteVo.setProfileURL(url);
		}
		System.out.println("--------------------------------------------------             " + siteVo);
		siteRepository.updateAll(siteVo);
		return siteVo;
	}

	private String SaveImg(MultipartFile multipartFile, String comments) {
		String url = null;

		/** 디렉토리없으면 생성 **/
		File uploadDirectory = new File(SAVE_PATH);
		if (!uploadDirectory.exists()) {
			uploadDirectory.mkdir();
		}

		if (multipartFile.isEmpty()) {
			return url;
		}

		/** 서버에 다른 이름으로 저장하기 위해 **/
		String originFilename = multipartFile.getOriginalFilename();
		String extName = originFilename.substring(originFilename.lastIndexOf('.') + 1); // 확장자 가져오기
		String saveFilename = generateSaveFilename(extName);
		long fileSize = multipartFile.getSize();

		System.out.println("#############" + originFilename);
		System.out.println("#############" + fileSize);
		System.out.println("#############" + saveFilename);

		/** 서버에 저장 **/
		try {
			byte[] data = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFilename);
			os.write(data);
			os.close();

			url = URL_BASE + "/" + saveFilename;
		} catch (IOException e) {
			throw new RuntimeException("img save error:" + e);
		}

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

}
