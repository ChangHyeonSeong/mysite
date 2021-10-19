package com.douzone.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.douzone.mysite.repository.GuestbookRepository;
import com.douzone.mysite.vo.GuestbookVo;

@Service
public class GuestbookService {
	@Autowired
    private GuestbookRepository guestbookRepository;

	public List<GuestbookVo> GetList() {
		List<GuestbookVo> list = guestbookRepository.findAll();
		for(GuestbookVo vo : list) {
			System.out.println(vo);
		}
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
	
	
}
