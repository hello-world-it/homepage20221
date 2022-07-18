package egovframework.let.temp.service.impl;

import java.util.List;
import egovframework.let.temp.service.TempService;
import egovframework.let.temp.service.TempVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("tempService") ////고유값
public class TempServiceImpl extends EgovAbstractServiceImpl implements TempService {

	@Resource(name = "tempMapper")
	private TempMapper tempMapper;
	
	/* DAO로 불러옴 */
	@Resource(name = "tempDAO")
	private TempDAO tempDAO;
	
	@Override
	public TempVO selectTemp(TempVO vo) throws Exception {
		return tempMapper.selectTemp(vo);
	}
	
	//임시데이터 목록 가져오기
	@Override
	public List<EgovMap> selectTempList(TempVO vo) throws Exception {
		return tempMapper.selectTempList(vo); 
	}
	
	//임시데이터 등록하기 / String으로 받아오는 id값 체크
	@Override
	public String insertTemp(TempVO vo) throws Exception {
		tempMapper.insertTemp(vo); //void형식으로 받아오는데 String으로 받기 위해 따로 선언 후 아래에 return null
		return null;
	}
	
	//임시데이터 수정하기
	@Override
	public void updateTemp(TempVO vo) throws Exception {
		tempMapper.updateTemp(vo);
	}
	
	//임시데이터 삭제하기
	@Override
	public void deleteTemp(TempVO vo) throws Exception {
		tempMapper.deleteTemp(vo);
	}
	
	//임시데이터 목록 수 / 220420
	@Override
	public int selectTempListCnt(TempVO vo) throws Exception {
		return tempMapper.selectTempListCnt(vo);
	}
	
	/* DAO로 불러오는거 주석처리 
	@Override
	public TempVO selectTemp(TempVO vo) throws Exception {
		return tempDAO.selectTemp(vo);
	}
	*/
}
