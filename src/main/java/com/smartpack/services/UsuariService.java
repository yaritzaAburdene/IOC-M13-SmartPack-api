package com.smartpack.services;

import com.smartpack.models.Rol;
import com.smartpack.models.Usuari;
import com.smartpack.repositories.UsuariRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UsuariService {
    @Autowired
    private UsuariRepository usuariRepository;


    public UsuariService(UsuariRepository usuariRepository) {
        this.usuariRepository = usuariRepository;
    }

     public List<Usuari> allUsers() {
        List<Usuari> users = new ArrayList<>();

        usuariRepository.findAll().forEach(users::add);

        return users;
    }
}