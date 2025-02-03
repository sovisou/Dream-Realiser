package group.service.impl;

import group.dto.contact.ContactDto;
import group.dto.contact.ContactRequestDto;
import group.mapper.ContactMapper;
import group.model.Contact;
import group.repository.ContactRepository;
import group.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    @Override
    public ContactDto createContact(ContactRequestDto contactRequestDto) {
        Contact contact = contactMapper.toEntity(contactRequestDto);
        contact = contactRepository.save(contact);
        return contactMapper.toDto(contact);
    }
}
