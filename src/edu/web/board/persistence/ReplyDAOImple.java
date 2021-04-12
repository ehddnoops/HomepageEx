package edu.web.board.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.web.board.domain.ReplyVO;
import edu.web.dbcp.connmgr.ConnMgr;

public class ReplyDAOImple implements ReplyDAO, ReplyQuery{
	private static ReplyDAOImple instance=null;
	
	private ReplyDAOImple() {
		
	}
	public static ReplyDAOImple getInstance() {
		if(instance == null) {
			instance = new ReplyDAOImple();
		}
		return instance;
		
	}
	
	@Override
	public int insert(ReplyVO vo) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_INSERT);
			pstmt.setInt(1, vo.getReplyBno());
			pstmt.setString(2, vo.getReplyContent());
			pstmt.setString(3, vo.getReplyId());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt);
		}
		return result;
	}

	@Override
	public List<ReplyVO> select(int replyBno) {
		List<ReplyVO> list = new ArrayList<ReplyVO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_SELECT_BY_REPLY_BNO);
			pstmt.setInt(1, replyBno);
			rs = pstmt.executeQuery();
			
			int replyNo;
			String replyContent;
			String replyId;
			String replyDate;
			ReplyVO vo = null;
			while(rs.next()) {
				replyNo = rs.getInt(COL_REPLY_NO);
				replyContent = rs.getString(COL_REPLY_CONTENT);
				replyId = rs.getString(COL_REPLY_ID);
				replyDate = rs.getString(COL_REPLY_DATE);
				vo = new ReplyVO(replyNo, replyBno, replyContent, replyId, replyDate);
				list.add(vo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt, rs);
		}
		
		return list;
	}

	@Override
	public int update(ReplyVO vo) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_UPDATE);
			pstmt.setString(1, vo.getReplyContent());
			pstmt.setInt(2, vo.getReplyNo());
			pstmt.setInt(3, vo.getReplyBno());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt);
		}
		return result;
	}

	@Override
	public int delete(int replyNo, int replyBno) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			conn = ConnMgr.getConnection();
			pstmt = conn.prepareStatement(SQL_DELETE);
			pstmt.setInt(1, replyNo);
			pstmt.setInt(2, replyBno);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnMgr.close(conn, pstmt);
		}
		return result;
	}
	

}





































