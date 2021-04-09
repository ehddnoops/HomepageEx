package edu.web.board.persistence;

import java.util.List;

import edu.web.board.domain.ReplyVO;

public interface ReplyDAO {
	public abstract int insert(ReplyVO vo);
	public abstract List<ReplyVO> select(int replyBno);
	public abstract int update(ReplyVO vo);
	public abstract int delete(int replyno, int replyBno);
}
