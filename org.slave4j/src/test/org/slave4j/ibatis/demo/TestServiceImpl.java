package test.org.slave4j.ibatis.demo;


public class TestServiceImpl implements TestService {

	private ILoginDao loginDao;
	
	public ILoginDao getLoginDao() {
		return loginDao;
	}

	public void setLoginDao(ILoginDao loginDao) {
		this.loginDao = loginDao;
	}
	
	public void getName() {
//		List<UserVO> userVOList = (List<UserVO>) loginDao.queryUser();
//		System.out.println("============== invoke getName() ===============" + userVOList.get(0).getUserName());
		
		UserVO userVO= (UserVO)loginDao.queryUser();
		System.out.println(userVO.getUserName());
	}
	
	public void registUser() {
		System.out.println("============== invoke getName() ===============");
		loginDao.registUser();
	}

}
