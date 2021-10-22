package one.digitalinnovation.personapi.service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService
{
    private PersonRepository personRepository;

    public MessageResponseDTO createPerson(PersonDTO personDTO)
    {
        Person personToSave = Person.builder()
                .firstName(personDTO.getFirstName())
                .lastName(personDTO.getLastName())
                .cpf(personDTO.getCpf())
                .phones(personDTO.getPhones())
                .build();

        Person saved = personRepository.save(personToSave);
        return createMessageDTO(saved, "Created Person with ID: ");
    }

    public List<Person> listAll()
    {
        return personRepository.findAll();
    }

    public Person findById(long id) throws PersonNotFoundException
    {
        return verifyIfExists(id);
    }

    public void deleteById(long id) throws PersonNotFoundException
    {
        verifyIfExists(id);

        personRepository.deleteById(id);
    }

    public MessageResponseDTO updateById(long id, PersonDTO personDTO) throws PersonNotFoundException
    {
        verifyIfExists(id);

        Person personToSave = Person.builder()
                .firstName(personDTO.getFirstName())
                .lastName(personDTO.getLastName())
                .cpf(personDTO.getCpf())
                .phones(personDTO.getPhones())
                .build();

        Person personSaved = personRepository.save(personToSave);

        personRepository.deleteById(id);
        return createMessageDTO(personSaved, "Update Person with ID: ");
    }

    private Person verifyIfExists(long id) throws PersonNotFoundException {
        return personRepository.findById(id).
                orElseThrow(() -> new PersonNotFoundException(id));
    }

    private MessageResponseDTO createMessageDTO(Person saved, String s) {
        return MessageResponseDTO.
                builder().
                message(s + saved.getId()).
                build();
    }
}
