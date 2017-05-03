package org.ws.web.db;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.ws.web.model.Person;
import org.ws.web.model.Tweet;

@Component
@Repository
public class DAO implements IDAO {

	private NamedParameterJdbcTemplate jdbc;

	public DAO(final NamedParameterJdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	@Transactional(readOnly = true)
	private List<Person> getPersonList(String sql) {
		List<Person> list = jdbc.query(sql, (rs, rowNum) -> new Person(rs.getInt("id"), rs.getString("name")));
		if (list == null)
			list = new ArrayList<Person>();
		return list;
	}

	@Transactional(readOnly = true)
	public List<Tweet> getTweets(List<Integer> userIds, String keyword) {

		String searchFilter = "";
		if (keyword != null)
			searchFilter =  " AND tweet.content regexp '" + keyword + "'";

		String users = userIds.toString();
		users = users.substring(1, users.length() - 1);
		String sql = "SELECT tweet.*,person.name " + "FROM tweet " + "INNER JOIN person "
				+ "ON person.id=tweet.person_id " + "WHERE person.id IN (" + users + ")  " + searchFilter;

		return jdbc.query(
				sql,
				(rs, rowNum) -> new Tweet(rs.getString("content"), rs.getInt("id"), rs.getInt("person_id"), rs
						.getString("name")));
	}

	@Transactional(readOnly = true)
	public Person getUser(String username) {

		return getPersonList("SELECT * FROM person WHERE name ='" + username + "'").get(0);
	}

	@Transactional(readOnly = true)
	public List<Person> getUsers(int[] id) {

		return getPersonList("SELECT * FROM person WHERE id ='" + id + "'");
	}

	@Transactional(readOnly = true)
	public List<Person> queryFollowAndFollowerRecords(int id) {
		
		String sql = "SELECT  DISTINCT  person.* " + " FROM followers " + " INNER JOIN person  "
				+ " ON followers.person_id = person.id " + " WHERE followers.follower_person_id  = '" + id + "'"
				+ " UNION " + " SELECT  DISTINCT  person.* " + " FROM followers " + " INNER JOIN person  "
				+ " ON followers.follower_person_id = person.id " + " WHERE followers.person_id  = '" + id + "'";

		List<Person> list = jdbc.query(sql, (rs, rowNum) -> new Person(rs.getInt("id"), rs.getString("name")));

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Integer> getFollowing(int id) {
		
		String sql = "SELECT  person.id " + " FROM followers " + " INNER JOIN person  "
				+ " ON followers.person_id = person.id " + " WHERE followers.follower_person_id  = '" + id
				+ "' order by person.id ";
		
		List<Integer> followers = jdbc.query(sql, (rs, rowNum) -> new Integer(rs.getInt("id")));
		
		if (followers == null)
			followers = new ArrayList<Integer>();
		
		return followers;
	}

	@Transactional
	public boolean delete(int userId, int id) {

		String sql = "DELETE FROM followers where person_id = " + userId + "AND follower_person_id = " + id;
		SqlParameterSource namedParameters = new MapSqlParameterSource();
		int value = jdbc.update(sql, namedParameters);

		return id == value;
	}

	@Transactional
	public boolean insert(int userId, int currentuser) {

		String sql = "INSERT  into followers (person_id, follower_person_id)  values (" + userId + "," + currentuser
				+ ")";
		SqlParameterSource namedParameters = new MapSqlParameterSource();
		int value = jdbc.update(sql, namedParameters);
		
		return currentuser == value;
	}
}
