package egovframework.com.cmm.service;

/**
 *  Class Name : Globals.java
 *  Description : 시스템 구동 시 프로퍼티를 통해 사용될 전역변수를 정의한다.
 *  Modification Information
 * 
 *     수정일         수정자                   수정내용
 *   -------    --------    ---------------------------
 *   2009.01.19    박지욱          최초 생성
 *
 *  @author 공통 서비스 개발팀 박지욱
 *  @since 2009. 01. 19
 *  @version 1.0
 *  @see 
 * 
 */

public class Globals {
    //파일 업로드 원 파일명
	public static final String ORIGIN_FILE_NM = "originalFileName";
	//파일 확장자
	public static final String FILE_EXT = "fileExtension";
	//파일크기
	public static final String FILE_SIZE = "fileSize";
	//업로드된 파일명
	public static final String UPLOAD_FILE_NM = "uploadFileName";
	//파일경로
	public static final String FILE_PATH = "filePath";
	
	//220907 네아로 키정보 추가
	// 네이버 클라이언트ID //글로벌스에서 불러오는 변수명은 대문자로 (ex NAVER_CLIENTID)
	public static final String NAVER_CLIENTID = EgovProperties.getProperty("NAVER.clientId");
	// 네이버 시크릿키
	public static final String NAVER_CLIENTSECRET = EgovProperties.getProperty("NAVER.clientSecret");
	// 네이버 리다이렉트URL
	public static final String NAVER_REDIRECTURI = EgovProperties.getProperty("NAVER.redirectUri");
}
