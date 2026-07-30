package com.example.cohort_9_java_14478_manahil.controller;

import com.example.cohort_9_java_14478_manahil.dto.ContactDTO;
import com.example.cohort_9_java_14478_manahil.service.ContactService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContactController.class)
@org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc(addFilters = false)
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ContactService contactService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateContact() throws Exception {

        ContactDTO dto = new ContactDTO();
        dto.setId(1L);
        dto.setFirstName("Manahil");
        dto.setLastName("Waheed");
        dto.setEmail("manahil@gmail.com");
        dto.setPhoneNumber("03001234567");
        dto.setCompany("OpenAI");
        dto.setJobTitle("Java Developer");

        when(contactService.createContact(any(ContactDTO.class))).thenReturn(dto);

        mockMvc.perform(post("/api/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Manahil"));
    }

    @Test
    void shouldGetAllContacts() throws Exception {

        ContactDTO dto = new ContactDTO();
        dto.setId(1L);
        dto.setFirstName("Manahil");
        dto.setLastName("Waheed");



        mockMvc.perform(get("/api/contacts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Manahil"));
    }

    @Test
    void shouldGetContactById() throws Exception {

        ContactDTO dto = new ContactDTO();
        dto.setId(1L);
        dto.setFirstName("Manahil");
        dto.setLastName("Waheed");

        when(contactService.getContactById(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/contacts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void shouldUpdateContact() throws Exception {

        ContactDTO dto = new ContactDTO();
        dto.setId(1L);
        dto.setFirstName("Updated");
        dto.setLastName("Waheed");
        dto.setEmail("manahil@gmail.com");
        dto.setPhoneNumber("03001234567");

        when(contactService.updateContact(eq(1L), any(ContactDTO.class))).thenReturn(dto);

        mockMvc.perform(put("/api/contacts/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Updated"));
    }

    @Test
    void shouldDeleteContact() throws Exception {

        doNothing().when(contactService).deleteContact(1L);

        mockMvc.perform(delete("/api/contacts/1"))
                .andExpect(status().isOk());
    }
}