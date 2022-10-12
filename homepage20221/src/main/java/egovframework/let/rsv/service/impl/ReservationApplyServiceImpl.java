package egovframework.let.rsv.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.let.rsv.service.ReservationApplyService;
import egovframework.let.rsv.service.ReservationApplyVO;
import egovframework.let.rsv.service.ReservationService;
import egovframework.let.rsv.service.ReservationVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.fdl.property.EgovPropertyService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

@Service("reservationApplyService")
public class ReservationApplyServiceImpl extends EgovAbstractServiceImpl implements ReservationApplyService {
	
	@Resource(name= "propertiesService")
	protected EgovPropertyService propertyService;
	
	@Resource(name= "reservationApplyMapper")
	private ReservationApplyMapper reservationApplyMapper;
	
	@Resource(name= "egovReqIdGnrService")
	private EgovIdGnrService idgenService;
	
	@Resource(name= "reservationService")
	private ReservationService reservationService;
	
	// 예약가능여부확인
	@Override
	public ReservationApplyVO rsvCheck(ReservationApplyVO vo) throws Exception {
		// 신청인원체크
		ReservationVO reservationVO = new ReservationVO();
		reservationVO.setResveId(vo.getResveId());
		ReservationVO result = reservationService.selectReservation(reservationVO);
		if(result.getMaxAplyCnt() <= result.getApplyFCnt()) { //최대신청자수 보다 지금까지 받은 신청자수가 크거나 같으면
			vo.setErrorCode("ERROR-R1"); //API 사용이 에러코드 사용
			vo.setMessage("마감 되었습니다.");
		}else if(reservationApplyMapper.duplicateApplyCheck(vo) > 0) { //duplicateApplyCheck 내가 이미 예약을 했는지 체크
			vo.setErrorCode("ERROR-R2");
			vo.setMessage("이미 해당 프로그램 예약이 되어져 있습니다.");
		}
		
		return vo;
	}

	//예약자 상세정보
	@Override
	public ReservationApplyVO selectReservationApply(ReservationApplyVO vo) throws Exception {
		return reservationApplyMapper.selectReservationApply(vo);
	}

	//예약자 등록하기
	@Override
	public ReservationApplyVO insertReservationApply(ReservationApplyVO vo) throws Exception {
		
		//신청 인원 체크
		ReservationVO reservationVO = new ReservationVO();
		reservationVO.setResveId(vo.getResveId());
		ReservationVO result = reservationService.selectReservation(reservationVO);
		if(result.getMaxAplyCnt() <= result.getApplyFCnt()) { //신청인원 체크
			vo.setErrorCode("ERROR-R1");
			vo.setMessage("마감 되었습니다.");
		}else {
			//기존 신청여부
			if(reservationApplyMapper.duplicateApplyCheck(vo) > 0) {
				vo.setErrorCode("ERROR-R2");
				vo.setMessage("이미 해당 프로그램 예약이 되어져 있습니다.");
			}else { //새로 신청하는 사람
				String id = idgenService.getNextStringId();
				vo.setReqstId(id);
				reservationApplyMapper.insertReservationApply(vo);
			}
		}
		
		return vo;
	}
	
	//예약자 목록 가져오기
	@Override
	public List<EgovMap> selectReservationApplyList(ReservationApplyVO vo) throws Exception {

		return reservationApplyMapper.selectReservationApplyList(vo);
	}

	//예약자 목록 수
	@Override
	public int selectReservationApplyListCnt(ReservationApplyVO vo) throws Exception {
		
		return reservationApplyMapper.selectReservationApplyListCnt(vo);
	}
	

	//예약자 수정하기
	@Override
	public void updateReservationApply(ReservationApplyVO vo) throws Exception {
		
		reservationApplyMapper.updateReservationApply(vo);
	}

	//예약자 삭제하기
	@Override
	public void deleteReservationApply(ReservationApplyVO vo) throws Exception {
		
		reservationApplyMapper.deleteReservationApply(vo);
	}
	
	//예약자 승인처리
	@Override
	public void updateReservationConfirm(ReservationApplyVO vo) throws Exception {
		
		reservationApplyMapper.updateReservationConfirm(vo);
	}

}
