package egovframework.let.crud2.service.impl;

import java.util.List;

import egovframework.let.crud2.service.Crud2Service;
import egovframework.let.crud2.service.Crud2VO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("crud2Service") ////고유값. 어노테이션에 명칭을 사용 할 때 소문자로 시작
public class Crud2ServiceImpl extends EgovAbstractServiceImpl implements Crud2Service {

	@Resource(name = "crud2Mapper")
	private Crud2Mapper crud2Mapper;
	
	//context-idgen의 추가한 IdGen과 연결  
	@Resource(name = "egovCrud2IdGnrService")
	private EgovIdGnrService idgenService;
	
	//데이터 목록 가져오기
	@Override
	public List<EgovMap> selectCrudList(Crud2VO vo) throws Exception { 
		return crud2Mapper.selectCrudList(vo); 
	}
	
	//데이터 목록 수 / 220420
	@Override
	public int selectCrudListCnt(Crud2VO vo) throws Exception {
		return crud2Mapper.selectCrudListCnt(vo);
	}
		
	//임시데이터 등록하기 / String으로 받아오는 id값 체크
 /* public String insertCrud(CrudVO vo) throws Exception {
		crudMapper.insertCrud(vo); //void형식으로 받아오는데 String으로 받기 위해 따로 선언 후 아래에 return null
		return null;
	} */
	
	//데이터 등록하기 / Id Gen으로 받기  
	@Override
	public String insertCrud(Crud2VO vo) throws Exception { 
		String id = idgenService.getNextStringId(); //변수명 idgenService의 id를 get
		vo.setCrud2Id(id);
		crud2Mapper.insertCrud(vo); //받은 id를 mapper에서 불러와서 insert
		
		return id;
	} //Impl에선 에러시 rollback이 된다(트랜잭션 도중 에러시(트리거 발생시) 롤백을 하기 위해 Impl에서 작성) / controller에선 에러시 auto commit이므로 에러로 중단이 되면 오류를 못찾아. 
	
	//데이터 가져오기
	@Override
	public Crud2VO selectCrud(Crud2VO vo) throws Exception {
	//	CrudVO result = crudMapper.selectCrud(vo);
	//	return result; //아래로 함축해서 사용
		return crud2Mapper.selectCrud(vo);
	}
		
	//데이터 수정하기 / ex쇼핑몰에서 주문 삭제 시 숫자반환 (retrun)가능 
	@Override
	public void updateCrud(Crud2VO vo) throws Exception {
		crud2Mapper.updateCrud(vo);
	}
	
	//데이터 삭제하기 / 우리는 반환값이 없어서 void로 선언해서 사용
	@Override
	public void deleteCrud(Crud2VO vo) throws Exception {
		crud2Mapper.deleteCrud(vo);
	}
	
	/* DAO로 불러오는거 주석처리 
	@Override
	public TempVO selectTemp(TempVO vo) throws Exception {
		return tempDAO.selectTemp(vo);
	}
	*/
}
