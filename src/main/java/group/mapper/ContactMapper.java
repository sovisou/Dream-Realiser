package group.mapper;

import group.dto.contact.ContactDto;
import group.dto.contact.ContactRequestDto;
import group.model.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {
    public Contact toEntity(ContactRequestDto contactRequestDto) {
        Contact contact = new Contact();
        contact.setCover(contactRequestDto.cover());
        contact.setEmail(contactRequestDto.email());
        contact.setDescription(contactRequestDto.description());
        return contact;
    }

    public ContactDto toDto(Contact contact) {
        return new ContactDto(
                contact.getId(),
                contact.getCover(),
                contact.getEmail(),
                contact.getDescription()
        );
    }
}
