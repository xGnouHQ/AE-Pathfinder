package com.pathfinder.service;

import com.pathfinder.dto.LoginRequestDTO;
import com.pathfinder.exception.EmailNotFoundException;
import com.pathfinder.exception.WrongPasswordException;
import com.pathfinder.model.Nachwuchskraft;
import com.pathfinder.repository.NachwuchskraftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final NachwuchskraftRepository repo;

    public Nachwuchskraft login(LoginRequestDTO req) {

        // Passwort check (fake)
        if (!req.password().equals("pass123")) {
            throw new WrongPasswordException();
        }

        // Nachwuchskraft anhand E-Mail finden
        return repo.findByEmail(req.email())
                .orElseThrow(() -> new EmailNotFoundException(req.email()));
    }
}
