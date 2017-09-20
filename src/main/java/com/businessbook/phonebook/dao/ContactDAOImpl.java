
package com.businessbook.phonebook.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.businessbook.phonebook.model.Contact;

@Repository
@Qualifier("contactDAO")
public class ContactDAOImpl implements ContactDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addContact(Contact contact) {
        jdbcTemplate.update("INSERT INTO contact (id, first_name, last_name, phone) VALUES (?, ?, ?, ?)",
                contact.getId(), contact.getFirstName(), contact.getLastName(), contact.getPhone());
        System.out.println("Contact added:: " + contact);
    }

    @Override
    public void editContact(Contact contact, Long contactId) {
        jdbcTemplate.update("UPDATE contact SET first_name = ? , last_name = ? , phone = ? WHERE id = ? ",
                contact.getFirstName(), contact.getLastName(), contact.getPhone(), contactId);
        System.out.println("Contact updated::" + contact);
    }

    @Override
    public void deleteContact(Long contactId) {
        jdbcTemplate.update("DELETE from contact WHERE id = ? ", contactId);
        System.out.println("Contact with ID = %l successfully deleted");
    }

    @Override
    public Contact findContact(Long contactId) {
        System.out.println("-------------------------------------------------");
        System.out.println(contactId);
        Contact contact = (Contact) jdbcTemplate.queryForObject("SELECT * FROM contact where id = ? ",
                new Object[] { contactId }, new BeanPropertyRowMapper<Contact>(Contact.class));
        System.out.println(contact);
        return contact;
    }

    @Override
    public List<Contact> findAll() {
        List<Contact> contacts = jdbcTemplate.query("SELECT * FROM contact", new BeanPropertyRowMapper<Contact>(Contact.class));
        return contacts;
    }
}
