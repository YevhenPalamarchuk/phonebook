
package com.businessbook.phonebook.service;

import java.util.List;

import com.businessbook.phonebook.model.Contact;

public interface ContactService {

    public void addContact(Contact contact);

    public void editContact(Contact contact, Long contactId);

    public void deleteContact(Long contactId);

    public Contact findContact(Long contactId);

    public List<Contact> findAll();

}
