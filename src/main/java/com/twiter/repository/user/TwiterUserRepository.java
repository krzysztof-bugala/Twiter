package com.twiter.repository.user;

import com.twiter.entity.user.TwiterUser;
import com.twiter.entity.user.User;
import com.twiter.repository.user.exception.UserNotFoundException;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by kb on 2017-09-12.
 */
@Repository
public class TwiterUserRepository implements UserRepository {

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    public User getUser(long id)  {
        User user = entityManager.find(TwiterUser.class, id);
        if (user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }

    @Override
    public User getUserByLogin(String login)  {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TwiterUser> cq = cb.createQuery(TwiterUser.class);
        Root<TwiterUser> rootEntry = cq.from(TwiterUser.class);
        CriteriaQuery<TwiterUser> all = cq.select(rootEntry);
        ParameterExpression<String> p = cb.parameter(String.class);
        cq.where(cb.equal(rootEntry.get("login"), p));
        TypedQuery<TwiterUser> query = entityManager.createQuery(all);
        query.setParameter(p, login);
        List<TwiterUser> result = query.getResultList();

        if (result.isEmpty())
        {
            throw new UserNotFoundException();
        }

        return result.get(0);
    }


    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public void persist(User user) {
        entityManager.persist(user);
    }

    @Override
    public long getDefaultUserSequenceNextValue() {
        Query q = entityManager.createNativeQuery("select default_user_sequence.nextval from dual");
        BigInteger sequenceValue = (BigInteger) q.getSingleResult();
        return sequenceValue.longValue();
    }

}
