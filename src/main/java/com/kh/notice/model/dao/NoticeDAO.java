package com.kh.notice.model.dao;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;

import com.kh.notice.model.vo.Notice;

public class NoticeDAO {

	public List<Notice> selectNoticeList(SqlSession session, int currentPage) {
		// JDBC랑 다르게 1줄이면 코드 끝
		// select를 할거면 session에서 selectList, selectOne 메소드 중에서 필요에 맞게 호출!
		// mapper.xml의 name값(NoticeMapper)과 쿼리문의 id값(selectNoticeList)을 호출!
		// 넘겨주는 값(매개변수)은 없으므로 name.id값만 selectList() 메소드의 전달값으로 넘겨줌
		// select이기 때문에 mapper.xml에서는 rsetToNotice에 해당하는 ResultMap을 작성해줘야함!!
		
		/*
		 * RowBounds는 왜 쓰나요? 쿼리문을 변경하지 않고도 페이징을 처리할 수 있게 해주는 클래스
		 * RowBounds의 동작은 offset값과 limit값을 이용해서 동작함.
		 * limit값은 한 페이지당 보여주고 싶은 게시물의 갯수
		 * offset값은 시작값, 변하는 값
		 * 1페이지에서는 0*10부터 시작해서 10개를 가져오고	1 ~ 10
		 * 2페이지에서는 1*10부터 시작해서 ...			11 ~ 20
		 * 3페이지에서는 2*10부터 시작해서 ...			21 ~ 30
		 */
		int limit = 10;
		int offset = (currentPage-1)*limit;
		RowBounds rowBounds = new RowBounds(offset, limit);
		List<Notice> nList = session.selectList("NoticeMapper.selectNoticeList", null, rowBounds);
		return nList;
	}

	public Notice selectOneByNo(SqlSession session, int noticeNo) {
		Notice notice = session.selectOne("NoticeMapper.selectOneByNo", noticeNo);
		return notice;
	}

	public int insertMember(SqlSession session, Notice notice) {
		// JDBC랑 다르게 1줄이면 코드 끝
		// insert를 할거면 session에서 insert() 메소드 호출 후
		// mapper.xml의 name값과 쿼리문의 id값을 호출!
		// 한 줄 쓰고 끝나는게 아니라 mapper.xml만들고 태그르르 이용해서 쿼리문 작성해주고
		// mapper.xml을 사용한다고 mybatis-config.xml에 신고/등록 해야함!
		int result = session.insert("NoticeMapper.insertNotice", notice);
		return result;
	}

	public int updateNotice(SqlSession session, Notice notice) {
		// 한 줄쓰고 끝나는게 아니라 notice-mapper.xml에 태그를 이용해서 쿼리문 작성해줘야 함.
		int result = session.update("NoticeMapper.updateNotice", notice);
		return result;
	}

	public int deleteNoticeByNo(SqlSession session, int noticeNo) {
		// 한 줄쓰고 끝나는게 아니라 notice-mapper.xml에 태그를 이용해서 쿼리문 작성해줘야 함.
		int result = session.delete("NoticeMapper.deleteNotice", noticeNo);
		return result;
	}
	
	public String generatePageNavi(SqlSession session, int currentPage) {
		// 전체 게시물의 갯수
		int totalCount = getTotalCount(session);
		int recordCountPerPage = 10;
		int naviCountPerPage = 5;
		int totalNaviCount;
		if(totalCount % recordCountPerPage > 0) {
			totalNaviCount = totalCount / recordCountPerPage + 1;
		}else {
			totalNaviCount = totalCount / recordCountPerPage;
		}
		// currentPage			startNavi
		// 1,2,3,4,5				1
		// 6,7,8,9,10				6
		// 11,12,13,14,15			11
		
		// currentPage			endNavi
		// 1,2,3,4,5				5
		// 6,7,8,9,10				10
		// 11,12,13,14,15			15
		
		
		int startNavi = ((currentPage - 1)/naviCountPerPage) * naviCountPerPage + 1;
		int endNavi = startNavi + naviCountPerPage - 1;
		if(endNavi > totalNaviCount) {
			endNavi = totalNaviCount;
		}
		StringBuffer result = new StringBuffer();
		boolean needPrev = true;
		boolean needNext = true;
		if(startNavi != 1) {
			result.append("<a href='/notice/list.do?currentPage="+(startNavi-1)+"'>[이전]</a>");
		}
		
		for(int i = startNavi; i <= endNavi; i++) {
			result.append("<a href='/notice/list.do?currentPage="+i+"'>"+i+"</a>&nbsp;&nbsp;");
		}
		if(endNavi != totalNaviCount) {
			result.append("<a href='/notice/list.do?currentPage="+(endNavi+1)+"'>[다음]</a>");
		}
		return result.toString();
	}

	private int getTotalCount(SqlSession session) {
		int totalCount = session.selectOne("NoticeMapper.getTotalCount");
		return totalCount;
	}

}
