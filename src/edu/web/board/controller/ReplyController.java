package edu.web.board.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import edu.web.board.domain.ReplyVO;
import edu.web.board.persistence.ReplyDAO;
import edu.web.board.persistence.ReplyDAOImple;

@WebServlet("/replies/*")
public class ReplyController extends HttpServlet {
	private static final long serialVersionUID = 1L;
      private ReplyDAO dao;
    public ReplyController() {
    	dao = ReplyDAOImple.getInstance();
    	
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		controlURI(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		controlURI(request, response);
	}

	private void controlURI(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String requestURI = request.getRequestURI();
		System.out.println(requestURI);
		
		if(requestURI.contains("add")) {
			System.out.println("add 호출 확인");
			replyAdd(request, response);
		} else if(requestURI.contains("all")) {
			System.out.println("all 호출 확인");
			replyList(request, response);
		} else if(requestURI.contains("update")) {
			System.out.println("update 호출 확인");
			replyUpdate(request, response);
		} else if(requestURI.contains("delete")) {
			System.out.println("delete 호출 확인");
			replyDelete(request, response);
	}
		
		
	}//end controlURI()




	private void replyAdd(HttpServletRequest request, HttpServletResponse response) throws IOException {
			int replyBno = Integer.parseInt(request.getParameter("replyBno"));
			String replyContent = request.getParameter("replyContent");
			String replyId = request.getParameter("replyId");
			System.out.println(replyBno);
			System.out.println(replyContent);
			System.out.println(replyId);
			ReplyVO vo = new ReplyVO(0, replyBno, replyContent, replyId, "");
			int result = dao.insert(vo);
			System.out.println(result);
			if(result == 1) {
				response.getWriter().append("success");
			}
			
	}
	private void replyList(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int replyBno = Integer.parseInt(request.getParameter("replyBno"));
		List<ReplyVO> list = dao.select(replyBno);
		
		JSONArray jsonArray = new JSONArray();
		for(int i = 0; i < list.size(); i++) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("replyNo", list.get(i).getReplyNo());
			jsonObject.put("replyBNo", list.get(i).getReplyBno());
			jsonObject.put("replyContent", list.get(i).getReplyContent());
			jsonObject.put("replyId", list.get(i).getReplyId());
			jsonObject.put("replyDate", list.get(i).getReplyDate());
			jsonArray.add(jsonObject);
		}
		System.out.println(jsonArray.toString());
		response.getWriter().append(jsonArray.toString());
		
	}
	private void replyUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
	int replyNo = Integer.parseInt(request.getParameter("replyNo"));
	int replyBno = Integer.parseInt(request.getParameter("replyBno"));
	String replyContent = request.getParameter("replyContent");
	ReplyVO vo = new ReplyVO(replyNo, replyBno, replyContent, "", "");
	
	int result = dao.update(vo);
	if(result == 1) {
		response.getWriter().append("success");
		
	}
	}
	private void replyDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
	int replyNo = Integer.parseInt(request.getParameter("replyNo"));
	int replyBno = Integer.parseInt(request.getParameter("replyBno"));
	int result = dao.delete(replyNo, replyBno);
	if(result == 1) {
		response.getWriter().append("success");
	}
	}
}




























