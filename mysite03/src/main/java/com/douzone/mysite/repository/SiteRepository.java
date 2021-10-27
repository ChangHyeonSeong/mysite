package com.douzone.mysite.repository;



import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.douzone.mysite.vo.SiteVo;

@Repository
public class SiteRepository {
	@Autowired
	private SqlSession sqlsession;
	

	public SiteVo findAll() {
		return sqlsession.selectOne("site.findAll", sqlsession);
	}


	public boolean updateAll(SiteVo siteVo) {
		return sqlsession.update("site.updateAll", siteVo) == 1;
	}

}
