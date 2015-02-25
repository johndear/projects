package test.org.slave4j.ibatis.instance;


public class SysMenuVo {

		   	private String id;
		   	private String name;
		   	private String url;
		   	private String parentid;
		   	private String deleted;
		   	private String createtime;
		   	private String updatetime;
		   	private String rank;
		   	private String actions;
	
		   	private String getId(){
		   		return this.id;
		   	}
		   	
		   	private void setId(String id){
		   		this.id = id;
		   	}
		   	private String getName(){
		   		return this.name;
		   	}
		   	
		   	private void setName(String name){
		   		this.name = name;
		   	}
		   	private String getUrl(){
		   		return this.url;
		   	}
		   	
		   	private void setUrl(String url){
		   		this.url = url;
		   	}
		   	private String getParentid(){
		   		return this.parentid;
		   	}
		   	
		   	private void setParentid(String parentid){
		   		this.parentid = parentid;
		   	}
		   	private String getDeleted(){
		   		return this.deleted;
		   	}
		   	
		   	private void setDeleted(String deleted){
		   		this.deleted = deleted;
		   	}
		   	private String getCreatetime(){
		   		return this.createtime;
		   	}
		   	
		   	private void setCreatetime(String createtime){
		   		this.createtime = createtime;
		   	}
		   	private String getUpdatetime(){
		   		return this.updatetime;
		   	}
		   	
		   	private void setUpdatetime(String updatetime){
		   		this.updatetime = updatetime;
		   	}
		   	private String getRank(){
		   		return this.rank;
		   	}
		   	
		   	private void setRank(String rank){
		   		this.rank = rank;
		   	}
		   	private String getActions(){
		   		return this.actions;
		   	}
		   	
		   	private void setActions(String actions){
		   		this.actions = actions;
		   	}
	
	
}