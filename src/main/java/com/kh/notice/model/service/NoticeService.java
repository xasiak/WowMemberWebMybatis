package com.kh.notice.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.kh.common.SqlSessionTemplate;
import com.kh.notice.model.dao.NoticeDAO;
import com.kh.notice.model.vo.Notice;
import com.kh.notice.model.vo.PageData;

public class NoticeService {
	
	// 서비스에서는 연결생성, DAO 호출 커밋롤백, 연결 해제 해줌
	// DAO 호출을 위한 nDao 필드 필요
	private NoticeDAO nDao;
	
	public NoticeService() {
		// 필드는 생성자에서 초기화 해줌
		nDao = new NoticeDAO();
	}
	
	public int insertNotice(Notice notice) {
		// 마이바티스에서는 SqlSession을 이용
		// SqlSessionTemplate은 mybatis-config.xml 파일을 참조하여 SqlSession 연결을 생성함
		// 연결공장빌더가 연결공장을 만들면 연결이 생성됨
		SqlSession session = SqlSessionTemplate.getSqlSession();
		int result = nDao.insertMember(session, notice);
		// 커밋&롤백은 DML의 경우에만 해당하는 것임!
		if(result > 0) {
			// 성공시 커밋
			session.commit();
		}else {
			// 실패시 롤백
			session.rollback();
		}
		// 연결해제
		session.close();
		return result;	// 리턴값은 0이나 null로 두지 않음 0 -> result, null -> notice or nList
	}

	public int updateNotice(Notice notice) {
		SqlSession session = SqlSessionTemplate.getSqlSession();
		int result = nDao.updateNotice(session, notice);
		if(result > 0) {
			session.commit();
		}else {
			session.rollback();
		}
		session.close();
		return result;
	}

	public int deleteNoticeByNo(int noticeNo) {
		SqlSession session = SqlSessionTemplate.getSqlSession();
		int result = nDao.deleteNoticeByNo(session, noticeNo);
		if(result > 0) {
			session.commit();
		}else {
			session.rollback();
		}
		session.close();
		return result;
	}

	public PageData selectNoticeList(int currentPage) {
		// 서비스, DAO, mapper.xml 순으로 코딩
		// 서비스는 연결생성, DAO 호출, 연결 해제
		SqlSession session = SqlSessionTemplate.getSqlSession();
		List<Notice> nList = nDao.selectNoticeList(session, currentPage);
		String pageNavi = nDao.generatePageNavi(session, currentPage);
		PageData pd = new PageData(nList, pageNavi);
		session.close();
		return pd;
	}

	public Notice selectOneByNo(int noticeNo) {
		SqlSession session = SqlSessionTemplate.getSqlSession();
		Notice notice = nDao.selectOneByNo(session, noticeNo);
		session.close();
		return notice;
	}

}
