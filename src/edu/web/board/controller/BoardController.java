package edu.web.board.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.web.board.domain.BoardVO;
import edu.web.board.persistence.BoardDAO;
import edu.web.board.persistence.BoardDAOImple;
import edu.web.board.util.PageCriteria;
import edu.web.board.util.PageMaker;

@WebServlet("*.do") // *.do : ~.do로 선언된 HTTP 호출에 대해 반응
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String BOARD_URL = "WEB-INF/board/";
	private static final String MAIN = "index";
	private static final String LIST = "list";
	private static final String REGISTER = "register";
	private static final String DETAIL = "detail";
	private static final String UPDATE = "update";
	private static final String DELETE = "delete";
	private static final String EXTENSION = ".jsp";
	private static final String SERVERSTR = ".do";
	
	
	
    private static BoardDAO dao;
    public BoardController() {
    	dao = BoardDAOImple.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		controlURI(request, response);
//		test(request, response);
	}

//	private void test(HttpServletRequest request, HttpServletResponse response) {
////		테스트 -> 순서대로
////		BoardVO vo = new BoardVO(0, "test", "test", "test", "");
////		int result = dao.insert(vo);
////		System.out.println(result);
//		
////		List<BoardVO> list = dao.select();
////		for(BoardVO vo : list) {
////			System.out.println(vo);
////		}
//		
////		BoardVO vo = dao.select(1);
////		System.out.println(vo);
//		
////		BoardVO vo = new BoardVO(2, "title", "content", "", "");
////		int result = dao.update(vo);
////		System.out.println(result);
//		
////		int vo = dao.delete(2);
////		System.out.println(vo);
////		테스트 구문
//	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		controlURI(request, response);
	}
	
	private void controlURI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();
		String requestMethod = request.getMethod();
		System.out.println("호출경로 : " + requestURI);
		System.out.println("호출방식 : " + requestMethod);
		
		if(requestURI.contains(LIST + SERVERSTR)) {
			System.out.println("list 호출 확인");
			list(request, response);
		} else if(requestURI.contains(REGISTER + SERVERSTR)) {
			System.out.println("register 호출 확인");
			if(requestMethod.equals("GET")) { // GET방식(페이지 불러오기)
				registerGET(request, response);				
			} else if(requestMethod.equals("POST")){//POST방식(DB에 저장)
				registerPOST(request, response);
			}
		} else if(requestURI.contains(DETAIL + SERVERSTR)) {
			System.out.println("detail 호출 확인");
			detail(request, response);
		} else if(requestURI.contains(UPDATE + SERVERSTR)) {
			System.out.println("update 호출 확인");
			if(requestMethod.equals("GET")) {
				updateGET(request, response);
			} else if(requestMethod.equals("POST")) {
				updatePOST(request, response);
			}
		} else if(requestURI.contains(DELETE + SERVERSTR)) {
			System.out.println("delete 호출 확인");
			delete(request, response);
		}
	}

	

	//게시판 전체 내용(List)을 DB에서 가져오고, 그 데이터를 list.jsp 페이지에 보내기
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		List<BoardVO> list = dao.select();
		String page = request.getParameter("page");
		
		PageCriteria c = new PageCriteria();
		if(page != null) {
			c.setPage(Integer.parseInt(page));			
		}
		
		List<BoardVO> list = dao.select(c);
		String path = BOARD_URL + LIST + EXTENSION;
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);	
		request.setAttribute("list", list);
		
		//페이지 링크 번호에 대한 정보를 구성하여
		//list.jsp 파일에 전송
		PageMaker m = new PageMaker();
		m.setCriteria(c); // 시작 페이지 및 한 페이지당 게시글 정보 저장
		int totalCount = dao.getTotalNums();// 전체 게시글 수
		m.setTotalCount(totalCount);//전체 게시글 수 저장
		m.setPageData(); //저장된 데이터를 바탕으로 page 링크 데이터 생성
		System.out.println("전체 게시글 수 : " + m.getTotalCount());
		System.out.println("현재 선택된 페이지 : " + c.getPage());
		System.out.println("한 페이지당 게시글 수 : " + c.getNumsPerPage());
		System.out.println("페이지링크 번호 개수 : " + m.getNumsOfPageLinks());
		System.out.println("시작 게페이지 링크 번호 : " + m.getStartPageNo());
		System.out.println("끝 페이지 링크 번호 : " + m.getEndPageNo());
		System.out.println("이전 버튼 존재 유무 : " + m.isHasPrev());
		System.out.println("다음 버튼 존재 유무 : " + m.isHasNext());
		
		
		request.setAttribute("pageMaker", m);
		dispatcher.forward(request, response);
	}
	//register.jsp를 호출
	private void registerGET(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = BOARD_URL + REGISTER + EXTENSION;
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);
		dispatcher.forward(request, response);
		
	}
	//게시판 새 글 데이터 DB 등록 후, index.jsp 페이지 이동
	private void registerPOST(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userid = request.getParameter("userid");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		BoardVO vo = new BoardVO(0, title, content, userid, "");
		int result = dao.insert(vo);
		if(result == 1) {
			PrintWriter out = response.getWriter();
			out.print("<head>" + "<meta charset='UTF-8'>" + "</head>");
			out.print("<script>alert('게시글 등록 성공');</script>");
			out.print("<script>location.href='" + MAIN + EXTENSION + "'</script>");
		}
	}
	//bno 번호에 맞는 게시글 데이터를 db에서 가져와서 detail.jsp에 전송
	//detail.jsp 파일에 update.do로 가는 버튼 생성
	private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bno = Integer.parseInt(request.getParameter("bno"));
		System.out.println(bno);
		BoardVO vo = dao.select(bno);
		String path = BOARD_URL + DETAIL + EXTENSION;
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);	
		request.setAttribute("vo", vo);
		dispatcher.forward(request, response);
	}
	
	//bno 번호에 맞는 게시글 데이터를 DB에서 가져와서 update.jsp에 전송
	// * update.jsp는 데이터 전송 form으로 구성 <form action="update.do" method="POST">
	private void updateGET(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int bno = Integer.parseInt(request.getParameter("bno"));
		BoardVO vo = dao.select(bno);
		String path = BOARD_URL + UPDATE + EXTENSION;
		RequestDispatcher dispatcher = request.getRequestDispatcher(path);	
		request.setAttribute("vo", vo);
		dispatcher.forward(request, response);
	}
	
	//bno 번호에 맞는 게시글 데이터를 DB에서 수정하고 index.jsp로 이동
	private void updatePOST(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int bno = Integer.parseInt(request.getParameter("bno"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		BoardVO vo = new BoardVO(bno, title , content, "", "");
		int result = dao.update(vo);
		if(result == 1) {
			PrintWriter out = response.getWriter();
			out.print("<head>" + "<meta charset='UTF-8'>" + "</head>");
			out.print("<script>location.href='" + MAIN + EXTENSION + "'</script>");
		}


	}
	
	
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int bno = Integer.parseInt(request.getParameter("bno"));
		int result = dao.delete(bno);
		if(result == 1) {
			PrintWriter out = response.getWriter();
			out.print("<head>" + "<meta charset='UTF-8'>" + "</head>");
			out.print("<script>location.href='" + MAIN + EXTENSION + "'</script>");
		}
		
	}




	
}
