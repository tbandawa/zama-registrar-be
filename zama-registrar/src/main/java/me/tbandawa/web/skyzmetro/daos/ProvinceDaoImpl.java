package me.tbandawa.web.skyzmetro.daos;

import me.tbandawa.web.skyzmetro.entities.Province;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ProvinceDaoImpl implements ProvinceDao {

    private final SessionFactory sessionFactory;

    public ProvinceDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Province> getProvinces() {
        Session session = this.sessionFactory.getCurrentSession();
        Query<?> query = session.createQuery("FROM Province");
        return (List<Province>)query.getResultList();
    }
}
