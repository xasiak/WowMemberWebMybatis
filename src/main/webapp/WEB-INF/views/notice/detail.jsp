<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>공지사항 상세조회</title>
	</head>
	<body>
		<h1>공지사항 상세</h1>
		<form action="" method="">
		<fieldset>
			<legend>공지사항 상세</legend>
				<ul>
					<li>
						<label>글번호</label>
						<span>${requestScope.notice.noticeNo }</span>
					</li>
					<li>
						<label>작성일</label>
						<span>${requestScope.notice.noticeDate }</span>
					</li>
					<li>
						<label>글쓴이</label>
						<span>${requestScope.notice.noticeWriter }</span>
					</li>
					<li>
						<label>제목</label>
						<span>${notice.noticeSubject }</span>
					</li>
					<li>
						<label>내용</label>
						<p>${notice.noticeContent }</p>
					</li>
				</ul>
			</fieldset>
		</form>
		<a href="/notice/list.do">목록으로 이동</a>
		<a href="/notice/modify.do?noticeNo=${notice.noticeNo }">수정하기</a>
<!-- 		DELETE * FROM NOTICE_TBL WHERE NOTICE_NO = ? -->
		<a href="javascript:void(0)"onclick="deleteCheck();">삭제하기</a>
<!-- 		<button id="" onclick="">삭제하기</button> -->
		<script type="text/javascript">
			const deleteCheck = () => {
				const noticeNo = '${notice.noticeNo}';
				if(confirm("정말로 삭제?")){
					location.href = "/notice/delete.do?noticeNo="+noticeNo;
				}
			}
		</script>
	</body>
</html>