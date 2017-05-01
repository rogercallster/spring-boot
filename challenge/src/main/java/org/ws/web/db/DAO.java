package org.ws.web.db;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ws.web.model.*;

@Component
@Repository
public class DAO implements IDAO {
	
	private NamedParameterJdbcTemplate jdbc;
	public DAO(final NamedParameterJdbcTemplate jdbc) {
		this.jdbc=jdbc;
	}


	@Transactional(readOnly = true)
	public List<Object> query(String query) {
		query = "SELECT * FROM person";
		List<Object> list = jdbc.query(query,(rs, rowNum) -> new Person(rs.getInt("id"), rs.getString("name")));
		return list;
	}
	
//	//jdbcTemplate = new JdbcTemplate(this.get);

//	public DBOperation() {
////		// //MvcConfiguration mvc = new MvcConfiguration();
//		 BasicDataSource dataSource = new BasicDataSource();
//		 dataSource.setDriverClassName("org.h2.Driver");
//		 dataSource.setUrl("jdbc:h2:mem:challenge");
//		 dataSource.setUsername("user");
//		 dataSource.setPassword("password");
//	}
	// public void update(final )
}
