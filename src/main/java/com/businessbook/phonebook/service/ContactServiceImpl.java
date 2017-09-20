
package com.businessbook.phonebook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.businessbook.phonebook.dao.ContactDAO;
import com.businessbook.phonebook.model.Contact;

@Service("contactService")
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactDAO contactDAO;

    @Override
    public void addContact(Contact contact) {
        contactDAO.addContact(contact);
    }

    @Override
    public void editContact(Contact contact, Long contactId) {
        contactDAO.editContact(contact, contactId);
    }

    @Override
    public void deleteContact(Long contactId) {
        contactDAO.deleteContact(contactId);
    }

    @Override
    public Contact findContact(Long contactId) {
        return contactDAO.findContact(contactId);
    }

    @Override
    public List<Contact> findAll() {
        return contactDAO.findAll();
    }

}
