package org.example.domain;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;

@Stateless
// @Alternative
public class ContactDaoDB implements IContactDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Contact> getAll() {
        return em.createNamedQuery("Contact.findAll", Contact.class).getResultList();
    }

    @Override public Collection<Contact> get(String q) {
        return null;
    }

    @Override public boolean add(Contact c) {
        return false;
    }

    @Override public boolean remove(String id) {
        return false;
    }

    @Override public boolean update(String id, Contact c) {
        return false;
    }

}