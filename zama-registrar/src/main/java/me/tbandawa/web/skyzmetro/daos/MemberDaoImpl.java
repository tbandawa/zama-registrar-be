package me.tbandawa.web.skyzmetro.daos;

import me.tbandawa.web.skyzmetro.entities.Member;
import me.tbandawa.web.skyzmetro.entities.PagedMembers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberDaoImpl implements MemberDao{

    private final SessionFactory sessionFactory;

    public MemberDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public PagedMembers getMembers(int page) {

        PagedMembers pagedMembers = new PagedMembers();
        pagedMembers.setPerPage(10);
        pagedMembers.setCurrentPage(page);
        page = page - 1;

        Session session = this.sessionFactory.getCurrentSession();

        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);

        countQuery.select(criteriaBuilder.count(countQuery.from(Member.class)));
        Long count = session.createQuery(countQuery).getSingleResult();
        pagedMembers.setCount(count.intValue());

        CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
        Root<Member> root = criteriaQuery.from(Member.class);
        CriteriaQuery<Member> selectQuery = criteriaQuery.select(root);

        TypedQuery<Member> typedQuery = session.createQuery(selectQuery);

        if (page < count.intValue()) {
            typedQuery.setFirstResult(page * 10);
            typedQuery.setMaxResults(10);

            pagedMembers.setResults(typedQuery.getResultList());
            pagedMembers.setNextPage(page + 2);
        }

        return pagedMembers;
    }

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    public Member saveMember(Member member) {
        Session session = this.sessionFactory.getCurrentSession();
        session.save(member);
        return member;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Member> getMember(long id) {
        Session session = this.sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Member.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Member> searchMembers(String query) {
        EntityManager entityManager = this.sessionFactory.createEntityManager();
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);
        try {
            fullTextEntityManager.createIndexer().startAndWait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Member.class)
                .get();
        org.apache.lucene.search.Query searchQuery = queryBuilder
                .keyword()
                .wildcard()
                .onFields("nationalIdNumber", "membershipNumber", "names", "surname", "position", "district")
                .matching(query + "*")
                .createQuery();
        return (List<Member>) fullTextEntityManager.createFullTextQuery(searchQuery, Member.class).getResultList();
    }

    @Override
    @Transactional
    public int editMember(Member member) {
        Session session = this.sessionFactory.getCurrentSession();
        session.update(member);
        return Math.toIntExact(member.getId());
    }

    @Override
    @Transactional
    public int activateMember(long id, boolean active) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<?> query = session.createQuery("UPDATE Member SET isActive = :isActive WHERE id = :id");
        query.setParameter("isActive", active);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    @Override
    @Transactional
    public int printMember(long id, boolean card) {
        Session session = this.sessionFactory.getCurrentSession();
        Query<?> query = session.createQuery("UPDATE Member SET isCard = :isCard WHERE id = :id");
        query.setParameter("isCard", card);
        query.setParameter("id", id);
        return query.executeUpdate();
    }
}
