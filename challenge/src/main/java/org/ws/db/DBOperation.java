package org.ws.db;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ws.web.model.*;

@Configuration
@Repository
public class DBOperation extends NamedParameterJdbcTemplate {
	
 
	public DBOperation(DataSource dataSource) {
		super(dataSource);
	}

//	public DBOperation() {
////		// //MvcConfiguration mvc = new MvcConfiguration();
//		 BasicDataSource dataSource = new BasicDataSource();
//		 dataSource.setDriverClassName("org.h2.Driver");
//		 dataSource.setUrl("jdbc:h2:mem:challenge");
//		 dataSource.setUsername("user");
//		 dataSource.setPassword("password");
//	}

	@Transactional(readOnly = true)
	public List<Object> query(String query) {
//		//jdbcTemplate = new JdbcTemplate(this.get);
		query = "SELECT * FROM person";
		List<Object> list = super.query(query,(rs, rowNum) -> new User(rs.getInt("id"), rs.getString("name")));
		return list;
	}
	

	// public void update(final )
}
