package egovframework.let.rsv.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import com.ibm.icu.text.SimpleDateFormat;

import egovframework.com.cmm.service.FileVO;
import egovframework.let.rsv.service.ReservationApplyService;
import egovframework.let.rsv.service.ReservationApplyVO;
import egovframework.let.rsv.service.ReservationService;
import egovframework.let.rsv.service.ReservationVO;
import egovframework.let.utl.fcc.service.EgovStringUtil;
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
	
	//221017 임시테이블 idgen
	@Resource(name = "egovReqTempIdGnrService")
	private EgovIdGnrService idgenTempService;

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

	//예약자 엑셀 업로드
	@Override
	public Map<String, Object> excelUpload(FileVO fileVO, ReservationApplyVO vo) throws Exception {

		String fileExt = fileVO.getFileExtsn(); //서버에 엑셀 파일 업로드
		
		FileInputStream stream = new FileInputStream(fileVO.getFileStreCours() + "/" + fileVO.getStreFileNm()); //엑셀 업로드 파일 읽기
		File file = new File(fileVO.getFileStreCours() + "/" + fileVO.getStreFileNm());
		
		Boolean result = true;
		Boolean totResult = true;
		String resultMsg = "";
		List<EgovMap> resultList = new ArrayList<EgovMap>();
		List<String> duplList = new ArrayList<String>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		//기존 예약자
		List<EgovMap> existUserList = reservationApplyMapper.selectReservationApplyList(vo);
		
		//엑셀 Id
		String tempId = idgenTempService.getNextStringId();
		vo.setReqsttempId(tempId); //여러 사용자의 파일을 구분
		
		try {
			Workbook wb = null;
			if("XLS".equals(fileExt.toUpperCase())) {
				wb = WorkbookFactory.create(stream);
			}else if("XLSX".equals(fileExt.toUpperCase())) {
				wb = WorkbookFactory.create(stream);
			}
			FormulaEvaluator eval = wb.getCreationHelper().createFormulaEvaluator();
			
			//int sheetNum = wb.getNumberOfSheets(); //시트갯수 가져오기
			if(wb != null) {
				Sheet sheet = wb.getSheetAt(0); //첫번째 시트 (여러 시트 사용 시 for문 한 번 더 돌림)
				int rows = sheet.getPhysicalNumberOfRows(); //행 갯수 가져오기
				for(int r=1; r<rows; r++) { //row 루프
					Row row = sheet.getRow(r); //row 가져오기 / 한 줄 가져오고
					if(row != null) {
						for(int c=0; c<4; c++) { //cell 가져오기  / 위 한 줄의 한 칸씩 가져옴 총 4칸만 가져온다. (실무에선 보통 아느는 칸을 memo로 사용)
							Cell cell = row.getCell(c);
							result = true;
							if(cell != null) {
								String value = "";
								//셀 타입에 맞춰서 값 호출
								switch(cell.getCellType()) {
									case Cell.CELL_TYPE_FORMULA:
										if(!EgovStringUtil.isEmpty(cell.toString())) {
											switch(eval.evaluateFormulaCell(cell)) {
												case Cell.CELL_TYPE_NUMERIC:
													if(HSSFDateUtil.isCellDateFormatted(cell)) {
														SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
														value = formatter.format(cell.getDateCellValue());
													}else {
														value = "" + (long)cell.getNumericCellValue(); 
													}
													break;
												case Cell.CELL_TYPE_STRING:
													value = "" + cell.getRichStringCellValue(); //"" + : String으로 변환  -> 해서 value에 저장
													break;
												case Cell.CELL_TYPE_BLANK:
													value = "";
													break;
												case Cell.CELL_TYPE_ERROR:
													value = "" + cell.getErrorCellValue();
													break;
												case Cell.CELL_TYPE_BOOLEAN:
													value = "" + cell.getBooleanCellValue();
													break;
												default:
													break;
											}
										}
									break;
									
									case Cell.CELL_TYPE_NUMERIC:
										if(HSSFDateUtil.isCellDateFormatted(cell)) {
											SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
											value = formatter.format(cell.getDateCellValue());
										}else {
											value = "" + (long)cell.getNumericCellValue();
										}
										break;
									case Cell.CELL_TYPE_STRING:
										value = "" + cell.getRichStringCellValue();
										break;
									case Cell.CELL_TYPE_BLANK:
										value = "";
										break;
									case Cell.CELL_TYPE_ERROR:
										value = "" + cell.getErrorCellValue();
										break;
									case Cell.CELL_TYPE_BOOLEAN:
										value = "" + cell.getBooleanCellValue();
										break;
									default:
										break;
								}
								
								if(!EgovStringUtil.isEmpty(value)) {
									value = value.trim();
								}
								
								switch(c) {
									case 0:
										vo.setUserId(value); //신청자ID
										break;
									case 1:
										vo.setChargerNm(value); //신청자명
										break;
									case 2:
										vo.setTelno(value); //연락처
										break;
									case 3:
										vo.setEmail(value); //이메일
										break;
									default:
										break;
								}
							}
						}
						
						//빈 행은 제외 (한 행을 다 돌고 빈 행을 제외시킴 / ex 엑셀시트에서 엔터치고 저장 시 샐 살아있음)
						if(!EgovStringUtil.isEmpty(vo.getUserId())) {
							List<String> existIdList = new ArrayList<>();
							//기존유저 중복 체크
							if(existUserList != null) {
								for(EgovMap cu : existUserList) {
									existIdList.add(cu.get("frstRegisterId").toString());
								}
								
								if(existIdList.contains(vo.getUserId())) {
									EgovMap usetMap = new EgovMap();
									usetMap.put("userId", vo.getUserId());
									usetMap.put("message", "이미 등록된 ID입니다.");
									resultList.add(usetMap);
									
									result = false;
									totResult = false;
								}
							}
							//엑셀 중복 체크
							if(result && duplList.contains(vo.getUserId())) { //리스트에 넣어서 이미 들어간 값을 체크
								EgovMap userMap = new EgovMap();
								userMap.put("userId", vo.getUserId());
								userMap.put("mesage", "엑셀에 중복으로 입력되었습니다.");
								resultList.add(userMap);
								
								result = false;
								totResult = false;
							}
							
							if(result && !EgovStringUtil.isEmpty(vo.getUserId())) { //모두 통과돼서 저장을 시킴
								String id = idgenService.getNextStringId();
								vo.setReqstId(id);
								reservationApplyMapper.insertReservationApplyTemp(vo);
								
								duplList.add(vo.getUserId()); //다음에 들어올 데이터 중복 체크 위해 저장
							}
						}
					}
				}
				
				List<EgovMap> tempList = reservationApplyMapper.selectReservationApplyTemp(vo); //엑셀을 다 돌고 옴
				if(tempList.size() > 0) { ///insert한 사람의 수를 저장하기 위해 
					//임시테이블에 저장된 데이터 일괄 등록(이관) //maper에서 tempId로 조회하여 insert into select로 select한 모든 값 저장  (속도↑ 통계 시에도 사용)
					reservationApplyMapper.insertReservationApplyTempAll(vo);
				}
				
			}
		} catch (FileNotFoundException e) {
			result = false;
			resultMsg = "문제가 발생하여 완료하지 못하였습니다.";
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			result = false;
			resultMsg = "문제가 발생하여 완료하지 못하였습니다.";
			e.printStackTrace();
		} catch (Exception e) {
			result = false;
			resultMsg = "문제가 발생하여 완료하지 못하였습니다.";
			e.printStackTrace();
		} finally {
			//임시 데이터 삭제 (insert한 데이터가 있기 때문에 임시데이터는 삭제 시킴)
			reservationApplyMapper.deleteReservationApplyTemp(vo);
			file.delete(); //엑셀 업로드 파일 삭제 : DB에 insert 했으니 엑셀 파일도 삭제. 
		}
		
		map.put("success", totResult);
		map.put("msg", resultMsg);
		map.put("resultList", resultList);
		
		return map;
	}

}
