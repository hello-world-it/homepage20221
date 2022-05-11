package egovframework.let.crud.service.impl;

import java.util.List;

import egovframework.let.crud.service.CrudService;
import egovframework.let.crud.service.CrudVO;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

@Service("crudService") ////고유값. 어노테이션에 명칭을 사용 할 때 소문자로 시작
public class CrudServiceImpl extends EgovAbstractServiceImpl implements CrudService {

	@Resource(name = "crudMapper")
	private CrudMapper crudMapper;
	
	/* DAO로 불러옴
	@Resource(name = "crudDAO")
	private CrudDAO crudDAO; */
	
	//context-idgen의 추가한 IdGen과 연결  
	@Resource(name = "egovCrudIdGnrService")
	private EgovIdGnrService idgenService;
	
	//데이터 목록 가져오기
	@Override
	public List<EgovMap> selectCrudList(CrudVO vo) throws Exception { 
		return crudMapper.selectCrudList(vo); 
	}
	
	//데이터 목록 수 / 220420
	@Override
	public int selectCrudListCnt(CrudVO vo) throws Exception {
		return crudMapper.selectCrudListCnt(vo);
	}
		
	//임시데이터 등록하기 / String으로 받아오는 id값 체크
 /* public String insertCrud(CrudVO vo) throws Exception {
		crudMapper.insertCrud(vo); //void형식으로 받아오는데 String으로 받기 위해 따로 선언 후 아래에 return null
		return null;
	} */
	
	//데이터 등록하기 / Id Gen으로 받기  
	@Override
	public String insertCrud(CrudVO vo) throws Exception { 
		String id = idgenService.getNextStringId(); //변수명 idgenService의 id를 get
		vo.setCrudId(id);
		crudMapper.insertCrud(vo); //받은 id를 mapper에서 불러와서 insert
		
		return id;
	} //Impl에선 에러시 rollback이 된다(트랜잭션 도중 에러시(트리거 발생시) 롤백을 하기 위해 Impl에서 작성) / controller에선 에러시 auto commit이므로 에러로 중단이 되면 오류를 못찾아. 
	
	//데이터 가져오기
	@Override
	public CrudVO selectCrud(CrudVO vo) throws Exception {
	//	CrudVO result = crudMapper.selectCrud(vo);
	//	return result; //아래로 함축해서 사용
		return crudMapper.selectCrud(vo);
	}
		
	//데이터 수정하기 / ex쇼핑몰에서 주문 삭제 시 숫자반환 (retrun)가능 
	@Override
	public void updateCrud(CrudVO vo) throws Exception {
		crudMapper.updateCrud(vo);
	}
	
	//데이터 삭제하기 / 우리는 반환값이 없어서 void로 선언해서 사용
	@Override
	public void deleteCrud(CrudVO vo) throws Exception {
		crudMapper.deleteCrud(vo);
	}
	
	/* DAO로 불러오는거 주석처리 
	@Override
	public TempVO selectTemp(TempVO vo) throws Exception {
		return tempDAO.selectTemp(vo);
	}
	*/
}
