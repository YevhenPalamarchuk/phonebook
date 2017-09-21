
package com.businessbook.phonebook.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.businessbook.phonebook.model.Contact;

@Repository
@Qualifier("contactDAO")
public class ContactDAOImpl implements ContactDAO {

    private final String SQL_QUERY_ADD = "INSERT INTO contact (first_name, last_name, phone) VALUES (:firstName, :lastName, :phone)";
    private final String SQL_QUERY_UPDATE = "UPDATE contact SET first_name = :firstName, last_name = :lastName, phone = :phone WHERE id = :id";
    private final String SQL_QUERY_DELETE = "DELETE from contact WHERE id = :id";
    private final String SQL_QUERY_FIND_ONE = "SELECT * FROM contact where id = :id";
    private final String SQL_QUERY_FIND_ALL = "SELECT * FROM contact";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public void addContact(Contact contact) {
        HashMap<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("firstName", contact.getFirstName());
        namedParameters.put("lastName", contact.getLastName());
        namedParameters.put("phone", contact.getPhone());
        namedParameterJdbcTemplate.update(SQL_QUERY_ADD, namedParameters);
        System.out.println("Contact added:: " + contact);
    }

    @Override
    public void editContact(Contact contact, Long contactId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("firstName", contact.getFirstName());
        namedParameters.addValue("lastName", contact.getLastName());
        namedParameters.addValue("phone", contact.getPhone());
        namedParameters.addValue("id", contactId);
        namedParameterJdbcTemplate.update(SQL_QUERY_UPDATE, namedParameters);
        System.out.println("Contact updated::" + contact);
    }

    @Override
    public void deleteContact(Long contactId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", Long.valueOf(contactId));
        namedParameterJdbcTemplate.update(SQL_QUERY_DELETE, namedParameters);
        System.out.format("Contact with ID = %d successfully deleted\n", contactId);
    }

    @Override
    public Contact findContact(Long contactId) {
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", Long.valueOf(contactId));
        Contact contact = (Contact) namedParameterJdbcTemplate.queryForObject(SQL_QUERY_FIND_ONE, namedParameters,
                this::mapContact);
        return contact;
    }

    @Override
    public List<Contact> findAll() {
        List<Contact> contacts = (List<Contact>) namedParameterJdbcTemplate.query(SQL_QUERY_FIND_ALL, this::mapContact);
        return contacts;
    }

    private Contact mapContact(ResultSet rs, int rowNum) throws SQLException {
        return new Contact(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"),
                rs.getString("phone"));
    }
}
