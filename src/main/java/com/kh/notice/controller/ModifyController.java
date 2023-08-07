package com.kh.notice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

/**
 * Servlet implementation class ModifyController
 */
@WebServlet("/notice/modify.do")
public class ModifyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 페이지 이동용
		int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));
		NoticeService service = new NoticeService();
		// SELECT * FROM NOTICE_TBL WHERE NOTICE_NO = ?
		Notice notice = service.selectOneByNo(noticeNo);
		if(notice != null) {
			request.setAttribute("notice", notice);
			request.getRequestDispatcher("/WEB-INF/views/notice/modify.jsp").forward(request, response);
		}else {
			// 생략
//			RequestDispatcher view = request.getRequestDispatcher("/WEB-INF/views/notice/modify.jsp");
//			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 데이터 수정용
		// UPDATE NOTICE_TBL SET NOTICE_SUBJECT = ?, NOTICE_CONTENT = ? WHERE NOTICE_NO = ?
		request.setCharacterEncoding("UTF-8");
		int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));
		String noticeSubject = request.getParameter("noticeSubject");
		String noticeContent = request.getParameter("noticeContent");
		Notice notice = new Notice(noticeNo, noticeSubject, noticeContent);
		NoticeService service = new NoticeService();
		int result = service.updateNotice(notice);
		if(result > 0) {
			// 성공하면 상세페이지로 이동
			response.sendRedirect("/notice/detail.do?noticeNo="+noticeNo);
		}else {
			// 실패하면 에러메시지 출력
			request.setAttribute("msg", "공지사항 수정이 완료되지 않았습니다.");
			request.setAttribute("url", "/notice/modify.do?noticeNo"+noticeNo);
			request.getRequestDispatcher("/WEB-INF/views/common/errorPage.jsp").forward(request, response);
		}
	}

}
