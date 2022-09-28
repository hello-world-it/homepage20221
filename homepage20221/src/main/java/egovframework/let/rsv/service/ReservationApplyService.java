package egovframework.let.rsv.service;

import java.util.List;

import egovframework.rte.psl.dataaccess.util.EgovMap;

public interface ReservationApplyService {
	
	//예약가능여부 확인
	public ReservationApplyVO rsvCheck(ReservationApplyVO vo) throws Exception;
	
	//예약자 상세정보
	public ReservationApplyVO selectReservationApply(ReservationApplyVO vo) throws Exception;

	//예약자 등록하기
	public ReservationApplyVO insertReservationApply(ReservationApplyVO vo) throws Exception;

	//예약자 목록 가져오기
	public List<EgovMap> selectReservationApplyList(ReservationApplyVO vo) throws Exception;

	//예약자 목록 수
	public int selectReservationApplyListCnt(ReservationApplyVO vo) throws Exception; 
	
	
}
