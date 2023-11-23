package com.betrybe.agrix.controller;

import com.betrybe.agrix.dto.PersonDto;
import com.betrybe.agrix.ebytr.staff.entity.Person;
import com.betrybe.agrix.ebytr.staff.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Person controller.
 */
@RestController
@RequestMapping("/persons")
public class PersonController {
  private final PersonService personService;

  @Autowired
  public PersonController(PersonService personService) {
    this.personService = personService;
  }

  /**
   * Create new person response entity.
   *
   * @param person the person
   * @return the response entity
   */
  @PostMapping
  public ResponseEntity<PersonDto> createNewPerson(@RequestBody Person person) {
    Person newPerson = personService.create(person);
    PersonDto newPersonDto = PersonDto.personToDto(newPerson);
    return ResponseEntity.status(HttpStatus.CREATED).body((newPersonDto));
  }
}
