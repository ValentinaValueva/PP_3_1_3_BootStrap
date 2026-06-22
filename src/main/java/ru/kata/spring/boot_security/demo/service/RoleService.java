package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;

public interface RoleService {
    List<Role> allRoles();

    List<Role> findAllById(Iterable<Long> ids);

}
