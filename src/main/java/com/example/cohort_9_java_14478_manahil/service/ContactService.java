package com.example.cohort_9_java_14478_manahil.service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.cohort_9_java_14478_manahil.dto.ContactDTO;
import com.example.cohort_9_java_14478_manahil.entity.Contact;
import com.example.cohort_9_java_14478_manahil.exception.ContactNotFoundException;
import com.example.cohort_9_java_14478_manahil.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactService {
    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    // Create contact

    // Create contact
    public ContactDTO createContact(ContactDTO contactDTO) {
        logger.info("Creating a new contact: {}", contactDTO.getEmail());
        Contact contact = new Contact();

        contact.setFirstName(contactDTO.getFirstName());
        contact.setLastName(contactDTO.getLastName());
        contact.setEmail(contactDTO.getEmail());
        contact.setPhoneNumber(contactDTO.getPhoneNumber());
        contact.setCompany(contactDTO.getCompany());
        contact.setJobTitle(contactDTO.getJobTitle());

        Contact savedContact = contactRepository.save(contact);
        logger.info("Contact created successfully.");
        return convertToDTO(savedContact);
    }

    // Get all contacts (DTO)
    public List<ContactDTO> getAllContacts() {
        logger.info("Fetching all contacts.");
        return contactRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    // Get contact by id (DTO)
    public ContactDTO getContactById(Long id) {

        logger.info("Fetching contact with ID: {}", id);

        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Contact not found with ID: {}", id);
                    return new ContactNotFoundException("Contact not found with id: " + id);
                });

        return convertToDTO(contact);
    }

    // Update contact
    public ContactDTO updateContact(Long id, ContactDTO contactDTO) {
        logger.info("Updating contact with ID: {}", id);
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() ->
                        new ContactNotFoundException("Contact not found with id: " + id)
                );

        existingContact.setFirstName(contactDTO.getFirstName());
        existingContact.setLastName(contactDTO.getLastName());
        existingContact.setEmail(contactDTO.getEmail());
        existingContact.setPhoneNumber(contactDTO.getPhoneNumber());
        existingContact.setCompany(contactDTO.getCompany());
        existingContact.setJobTitle(contactDTO.getJobTitle());

        Contact updatedContact = contactRepository.save(existingContact);
        logger.info("Contact updated successfully.");
        return convertToDTO(updatedContact);
    }


    // Delete contact
    public void deleteContact(Long id) {
        logger.info("Deleting contact with ID: {}", id);
        Contact existingContact = contactRepository.findById(id)
                .orElseThrow(() ->
                        new ContactNotFoundException("Contact not found with id: " + id)
                );

        contactRepository.delete(existingContact);
        logger.info("Contact deleted successfully.");
    }

    public List<Contact> searchByFirstName(String firstName) {
        logger.info("Searching contacts by first name: {}", firstName);
        return contactRepository.findByFirstNameContainingIgnoreCase(firstName);
    }

    public List<Contact> searchByLastName(String lastName) {
        logger.info("Searching contacts by last name: {}", lastName);
        return contactRepository.findByLastNameContainingIgnoreCase(lastName);
    }

    public List<Contact> searchByEmail(String email) {
        logger.info("Searching contacts by email: {}", email);
        return contactRepository.findByEmailContainingIgnoreCase(email);
    }

    public List<Contact> searchByCompany(String company) {
        logger.info("Searching contacts by company: {}", company);
        return contactRepository.findByCompanyContainingIgnoreCase(company);
    }

    public List<Contact> searchByJobTitle(String jobTitle) {
        logger.info("Searching contacts by job title: {}", jobTitle);
        return contactRepository.findByJobTitleContainingIgnoreCase(jobTitle);
    }

    public List<Contact> filterByCompany(String company) {
        logger.info("Filtering contacts by company: {}", company);
        return contactRepository.findByCompany(company);
    }

    public List<Contact> filterByJobTitle(String jobTitle) {
        logger.info("Filtering contacts by job title: {}", jobTitle);
        return contactRepository.findByJobTitle(jobTitle);
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