package egovframework.let.temp2.service.impl;

import java.util.List;
import egovframework.let.temp2.service.Temp2Service;
import egovframework.let.temp2.service.Temp2VO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

@Service("temp2Service") ////고유값. 어노테이션 값은 하나만, 중복이면 안돼.
public class Temp2ServiceImpl extends EgovAbstractServiceImpl implements Temp2Service { //implements Temp2Service Temp2와 연결

	@Resource(name = "temp2Mapper")
	private Temp2Mapper temp2Mapper;
	
	/* DAO(ibytis)로 불러옴 
	@Resource(name = "tempDAO")
	private TempDAO tempDAO; */
	
	//context-idgen의 추가한 IdGen과 연결 / 220427
	@Resource(name = "egovTempIdGnrService")
	private EgovIdGnrService idgenService;
	
	@Override
	public Temp2VO selectTemp(Temp2VO vo) throws Exception {
		return temp2Mapper.selectTemp(vo);
	}
	
	//임시데이터 목록 가져오기
	@Override
	public List<EgovMap> selectTempList(Temp2VO vo) throws Exception {
		return temp2Mapper.selectTempList(vo); 
	}
	
	//임시데이터 등록하기 / String으로 받아오는 id값 체크
//	public String insertTemp(Temp2VO vo) throws Exception {
//		temp2Mapper.insertTemp(vo); //void형식으로 받아오는데 String으로 받기 위해 따로 선언 후 아래에 return null
//		return null;
//	}
	
	//임시데이터 등록하기 / Id Gen으로 받기 / 220427 
	@Override
	public String insertTemp(Temp2VO vo) throws Exception { 
		String id = idgenService.getNextStringId(); // 변수명 idgenService의 id를 get
		vo.setTempId(id);
		temp2Mapper.insertTemp(vo); //받은 id를 insert
		
		return id;
	} //Impl에선 에러시 rollback이 된다(트랜잭션 도중 에러시(트리거 발생시) 롤백을 하기 위해 Impl에서 작성) / controller에선 에러시 auto commit이므로 에러로 중단이 되면 오류를 못찾아. 
	
	//임시데이터 수정하기
	@Override
	public void updateTemp(Temp2VO vo) throws Exception {
		temp2Mapper.updateTemp(vo);
	}
	
	//임시데이터 삭제하기
	@Override
	public void deleteTemp(Temp2VO vo) throws Exception {
		temp2Mapper.deleteTemp(vo);
	}
	
	//임시데이터 목록 수 / 220420
	@Override
	public int selectTempListCnt(Temp2VO vo) throws Exception {
		return temp2Mapper.selectTempListCnt(vo);
	}
	
	/* DAO로 불러오는거 주석처리 
	@Override
	public TempVO selectTemp(TempVO vo) throws Exception {
		return tempDAO.selectTemp(vo);
	}
	*/
}
