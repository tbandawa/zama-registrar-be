package me.tbandawa.web.skyzmetro.daos;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import me.tbandawa.web.skyzmetro.entities.User;

@Repository
public class UserDaoImpl implements UserDao {
	
	private final SessionFactory sessionFactory;

	public UserDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	@Transactional
	public Long addUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		return (Long)session.save(user);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> getUser(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		return Optional.ofNullable(session.get(User.class, id));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<User> getUsers(Long id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery("FROM User u WHERE u.id != :id");
		query.setParameter("id", id);
		return (List<User>)query.getResultList();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findByEmail(String email) {
		Session session = this.sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery("FROM User WHERE email = :email");
		query.setParameter("email", email);
		User results = (User)query.uniqueResult();
		return results == null? Optional.empty() : Optional.of(results);
	}

	@Override
	@Transactional
	public int deleteUser(long id) {
		Session session = this.sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery("DELETE FROM User WHERE id = :id");
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	@Override
	@Transactional
	public int editUser(User user) {
		Session session = this.sessionFactory.getCurrentSession();
		session.update(user);
		return Math.toIntExact(user.getId());
	}
	
	@Override
	@Transactional
	public int activateUser(long id, int isActive) {
		Session session = this.sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery("UPDATE User SET isActive = :isActive WHERE id = :id");
		query.setParameter("isActive", isActive);
		query.setParameter("id", id);
		return query.executeUpdate();
	}
}
