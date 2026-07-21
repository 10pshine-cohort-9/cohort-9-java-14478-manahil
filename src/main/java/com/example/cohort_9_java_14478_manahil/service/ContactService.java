package com.example.cohort_9_java_14478_manahil.service;

import com.example.cohort_9_java_14478_manahil.dto.ContactDTO;
import com.example.cohort_9_java_14478_manahil.entity.Contact;
import com.example.cohort_9_java_14478_manahil.exception.ContactNotFoundException;
import com.example.cohort_9_java_14478_manahil.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    // Create contact
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }


    // Get all contacts (DTO)
    public List<ContactDTO> getAllContacts() {

        return contactRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    // Get contact by id (DTO)
    public ContactDTO getContactById(Long id) {

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() ->
                        new ContactNotFoundException("Contact not found with id: " + id)
                );

        return convertToDTO(contact);
    }


    // Update contact
    public Contact updateContact(Long id, Contact contactDetails) {

        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() ->
                        new ContactNotFoundException("Contact not found with id: " + id)
                );

        existingContact.setFirstName(contactDetails.getFirstName());
        existingContact.setLastName(contactDetails.getLastName());
        existingContact.setEmail(contactDetails.getEmail());
        existingContact.setPhoneNumber(contactDetails.getPhoneNumber());
        existingContact.setCompany(contactDetails.getCompany());
        existingContact.setJobTitle(contactDetails.getJobTitle());

        return contactRepository.save(existingContact);
    }


    // Delete contact
    public void deleteContact(Long id) {

        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() ->
                        new ContactNotFoundException("Contact not found with id: " + id)
                );

        contactRepository.delete(existingContact);
    }


    // Entity to DTO conversion
    private ContactDTO convertToDTO(Contact contact) {

        ContactDTO dto = new ContactDTO();

        dto.setId(contact.getId());
        dto.setFirstName(contact.getFirstName());
        dto.setLastName(contact.getLastName());
        dto.setEmail(contact.getEmail());
        dto.setPhoneNumber(contact.getPhoneNumber());
        dto.setCompany(contact.getCompany());
        dto.setJobTitle(contact.getJobTitle());

        return dto;
    }
}