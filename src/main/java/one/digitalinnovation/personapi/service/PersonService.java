package one.digitalinnovation.personapi.service;

import one.digitalinnovation.personapi.dto.request.PersonDTO;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService
{
    private PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository)
    {
        this.personRepository = personRepository;
    }

    public MessageResponseDTO createPerson(PersonDTO personDTO)
    {
        Person personToSave = Person.builder()
                .firstName(personDTO.getFirstName())
                .lastName(personDTO.getLastName())
                .cpf(personDTO.getCpf())
                .phones(personDTO.getPhones())
                .build();

        Person saved = personRepository.save(personToSave);
        return MessageResponseDTO.
                builder().
                message("Created Person with ID: " + saved.getId()).
                build();
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

    private Person verifyIfExists(long id) throws PersonNotFoundException {
        return personRepository.findById(id).
                orElseThrow(() -> new PersonNotFoundException(id));
    }
}
