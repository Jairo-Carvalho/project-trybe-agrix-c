package com.betrybe.agrix.dto;

import com.betrybe.agrix.ebytr.staff.entity.Person;
import com.betrybe.agrix.ebytr.staff.security.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The type Person dto.
 */
public record PersonDto(
    Long id,
    String username,
    @JsonIgnore
    String password,
    Role role
) {

  /**
   * Person to dto person dto.
   *
   * @param person the person
   * @return the person dto
   */
  public static PersonDto personToDto(Person person) {
    return new PersonDto(
        person.getId(),
        person.getUsername(),
        person.getPassword(),
        person.getRole()
    );
  }

  public Person toEntity() {
    return new Person();
  }
}
