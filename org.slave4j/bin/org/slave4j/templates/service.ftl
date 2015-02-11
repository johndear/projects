package ${packageName};

import javax.annotation.Resource;
import org.slave4j.orm.hibernate.BaseDao; 
import org.slave4j.orm.hibernate.BaseService; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional;
import ${entityPackageName}.${entityClassName};

@Service
@Transactional
public class ${className} extends BaseService<${entityClassName}> {

	@Override
	@Resource(name="${daoObjectName}")
	public void setBaseDao(BaseDao<${entityClassName}> baseDao){
		this.baseDao = baseDao;
	}

}
