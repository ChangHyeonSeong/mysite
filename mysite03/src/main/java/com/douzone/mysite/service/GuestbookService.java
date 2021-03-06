package com.douzone.mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
    private GuestbookRepository guestbookRepository;

	public List<GuestbookVo> getList() {
		List<GuestbookVo> list = guestbookRepository.findAll();
//		for(GuestbookVo vo : list) {
//			System.out.println(vo);
//		}
		return list;
	}

	public boolean add(GuestbookVo vo) {
	    boolean result;
	    result = guestbookRepository.insert(vo);
	    return result;
	}

	public boolean delete(GuestbookVo vo) {
		boolean result;
		result = guestbookRepository.delete(vo);
		return result;
	}

	public List<GuestbookVo> getList(Long no) {
		if(no < 0 ) {
			return guestbookRepository.findAll().subList(0, 5);
		}
		List<GuestbookVo> list = guestbookRepository.findAll(no);
		return list;
	}

	public boolean delete(Long no, String password) {
		Map<String, Object> map = new HashMap<>();
		map.put("no", no);
		map.put("password", password);
		return guestbookRepository.deleteByNoPassword(map);
	}
	
	
}
