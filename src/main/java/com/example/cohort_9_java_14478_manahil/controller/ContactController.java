package com.example.cohort_9_java_14478_manahil.controller;

import com.example.cohort_9_java_14478_manahil.dto.ContactDTO;
import com.example.cohort_9_java_14478_manahil.entity.Contact;
import com.example.cohort_9_java_14478_manahil.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }


    // Create Contact
    @PostMapping
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {

        Contact savedContact = contactService.createContact(contact);

        return new ResponseEntity<>(savedContact, HttpStatus.CREATED);
    }


    // Get All Contacts
    @GetMapping
    public ResponseEntity<List<ContactDTO>> getAllContacts() {

        return ResponseEntity.ok(contactService.getAllContacts());
    }


    // Get Contact By ID
    @GetMapping("/{id}")
    public ResponseEntity<ContactDTO> getContactById(@PathVariable Long id) {

        return ResponseEntity.ok(contactService.getContactById(id));
    }


    // Update Contact
    @PutMapping("/{id}")
    public ResponseEntity<Contact> updateContact(
            @PathVariable Long id,
            @RequestBody Contact contact) {

        Contact updatedContact = contactService.updateContact(id, contact);

        return ResponseEntity.ok(updatedContact);
    }


    // Delete Contact
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id) {

        contactService.deleteContact(id);

        return ResponseEntity.ok("Contact deleted successfully");
    }
}