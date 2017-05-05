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

/**
 * @Description Data access object implementation. This has DB layer Since this is a very simple and time crunch design
 *              here users DB operation instead of abstracting query generation for DB queries but Database layer is
 *              separated from Java using data Models which are just plain old Java objects. A users can see these model
 *              as representation of data.
 * */
@Component
@Repository
public class DAOImplementation implements DAO {

	private static final String ID = "id";
	private static final String NAME = "name";
	private static final String CONTENT = "content";
	private static final String PERSON_ID = "person_id";

	private NamedParameterJdbcTemplate fJDBC;

	public DAOImplementation(final NamedParameterJdbcTemplate jdbc) {
		this.fJDBC = jdbc;
	}

	@Transactional(readOnly = true)
	private List<Person> getPersonList(String sql) {
		List<Person> list = fJDBC.query(sql, (rs, rowNum) -> new Person(rs.getInt(ID), rs.getString(NAME)));
		if (list == null)
			list = new ArrayList<Person>();
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tweet> getTweets(List<Integer> userIds, String keyword) {

		String searchFilter = "";
		if (keyword != null)
			searchFilter = " AND tweet.content regexp '" + keyword + "'";

		String users = userIds.toString();
		users = users.substring(1, users.length() - 1);
		String sql = "SELECT tweet.*,person.name " + "FROM tweet " + "INNER JOIN person "
				+ "ON person.id=tweet.person_id " + "WHERE person.id IN (" + users + ")  " + searchFilter;

		return fJDBC.query(sql, (rs, rowNum) -> new Tweet(rs.getString(CONTENT), rs.getInt(ID), rs.getInt(PERSON_ID),
				rs.getString(NAME)));
	}

	@Override
	@Transactional(readOnly = true)
	public Person getUser(String username) {

		return getPersonList("SELECT * FROM person WHERE name ='" + username + "'").get(0);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Person> getUsers(int[] id) {

		return getPersonList("SELECT * FROM person WHERE id ='" + id + "'");
	}

	@Override
	@Transactional(readOnly = true)
	public List<Person> queryFollowAndFollowerRecords(int id) {

		String sql = "SELECT  DISTINCT  person.* " + " FROM followers " + " INNER JOIN person  "
				+ " ON followers.person_id = person.id " + " WHERE followers.follower_person_id  = '" + id + "'"
				+ " UNION " + " SELECT  DISTINCT  person.* " + " FROM followers " + " INNER JOIN person  "
				+ " ON followers.follower_person_id = person.id " + " WHERE followers.person_id  = '" + id + "'";

		List<Person> list = fJDBC.query(sql, (rs, rowNum) -> new Person(rs.getInt(ID), rs.getString(NAME)));

		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Integer> getFollowing(int id) {

		String sql = "SELECT  person.id " + " FROM followers " + " INNER JOIN person  "
				+ " ON followers.person_id = person.id " + " WHERE followers.follower_person_id  = '" + id
				+ "' order by person.id ";

		List<Integer> followers = fJDBC.query(sql, (rs, rowNum) -> new Integer(rs.getInt(ID)));

		if (followers == null)
			followers = new ArrayList<Integer>();

		return followers;
	}

	@Override
	@Transactional
	public boolean delete(int userId, int id) {

		String sql = "DELETE FROM followers where person_id = " + userId + "AND follower_person_id = " + id;
		SqlParameterSource namedParameters = new MapSqlParameterSource();
		int value = fJDBC.update(sql, namedParameters);

		return id == value;
	}

	@Override
	@Transactional
	public boolean insert(int userId, int currentuser) {

		String sql = "INSERT  into followers (person_id, follower_person_id)  values (" + userId + "," + currentuser
				+ ")";
		SqlParameterSource namedParameters = new MapSqlParameterSource();
		int value = fJDBC.update(sql, namedParameters);

		return currentuser != value;
	}

	public List<Person> queryPerson(int dst) {
		String sql = "SELECT * FROM person WHERE id='" + dst + "'";
		List<Person> list = fJDBC.query(sql, (rs, rowNum) -> new Person(rs.getInt(ID), rs.getString(NAME)));
		return list;
	}
}
