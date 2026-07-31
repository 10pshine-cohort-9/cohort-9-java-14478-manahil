package com.example.cohort_9_java_14478_manahil.controller;
import com.example.cohort_9_java_14478_manahil.entity.Contact;
import com.example.cohort_9_java_14478_manahil.dto.ContactDTO;
import com.example.cohort_9_java_14478_manahil.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;

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
    public ResponseEntity<ContactDTO> createContact(@Valid @RequestBody ContactDTO contactDTO) {

        ContactDTO savedContact = contactService.createContact(contactDTO);

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
    public ResponseEntity<ContactDTO> updateContact(
            @PathVariable Long id,
            @Valid @RequestBody ContactDTO contactDTO) {

        ContactDTO updatedContact = contactService.updateContact(id, contactDTO);

        return ResponseEntity.ok(updatedContact);
    }

    // Delete Contact
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id) {

        contactService.deleteContact(id);

        return ResponseEntity.ok("Contact deleted successfully");
    }
    @GetMapping("/search/firstname")
    public ResponseEntity<List<Contact>> searchByFirstName(
            @RequestParam String firstName) {

        return ResponseEntity.ok(contactService.searchByFirstName(firstName));
    }

    @GetMapping("/search/lastname")
    public ResponseEntity<List<Contact>> searchByLastName(
            @RequestParam String lastName) {

        return ResponseEntity.ok(contactService.searchByLastName(lastName));
    }

    @GetMapping("/search/email")
    public ResponseEntity<List<Contact>> searchByEmail(
            @RequestParam String email) {

        return ResponseEntity.ok(contactService.searchByEmail(email));
    }

    @GetMapping("/search/company")
    public ResponseEntity<List<Contact>> searchByCompany(
            @RequestParam String company) {

        return ResponseEntity.ok(contactService.searchByCompany(company));
    }

    @GetMapping("/search/jobtitle")
    public ResponseEntity<List<Contact>> searchByJobTitle(
            @RequestParam String jobTitle) {

        return ResponseEntity.ok(contactService.searchByJobTitle(jobTitle));
    }
    @GetMapping("/filter/company")
    public ResponseEntity<List<Contact>> filterByCompany(
            @RequestParam String company) {

        return ResponseEntity.ok(contactService.filterByCompany(company));
    }
    @GetMapping("/filter/jobtitle")
    public ResponseEntity<List<Contact>> filterByJobTitle(
            @RequestParam String jobTitle) {

        return ResponseEntity.ok(contactService.filterByJobTitle(jobTitle));
    }


}