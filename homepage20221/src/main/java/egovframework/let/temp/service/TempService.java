package egovframework.let.temp.service;

import java.util.List;
import java.util.Map;

public interface TempService {
	
	//임시데이터(서비스) 가져오기
	public TempVO selectTemp(TempVO vo) throws Exception;
}