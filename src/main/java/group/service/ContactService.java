package group.service;

import group.dto.contact.ContactDto;
import group.dto.contact.ContactRequestDto;

public interface ContactService {
    ContactDto createContact(ContactRequestDto contactRequestDto);
}
