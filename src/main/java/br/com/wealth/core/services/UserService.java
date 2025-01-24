package br.com.wealth.core.services;



import br.com.wealth.core.dto.UserRegistrationDto;
import br.com.wealth.core.entities.Role;
import br.com.wealth.core.entities.User;
import br.com.wealth.core.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User registerUser(UserRegistrationDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new IllegalArgumentException("E-mail já registrado"); //todo: verificar porque esta mensagem não está caindo no exception handler
        }

        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            throw new IllegalArgumentException("As senhas não coincidem");
        }

        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = new Role();
        role.setRoleId(userDto.getRole().getRoleId());;  // Use o ID correto da role no banco

        // Adiciona a Role ao Set<Role>
        Set<Role> roles = new HashSet<>();
        roles.add(role);

        // Atribui as roles ao usuário
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        if(role.getRoleId() == 6L) {
//            actionmakerService.createActionMaker(savedUser);
        }

        if(role.getRoleId() == 4L) {
//            promoterService.createPromoter(savedUser);
        }

        return savedUser;
    }

    public Optional<User> findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return userRepository.findByEmail(email);
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            return findByEmail(email).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        }
        return null;
    }

}
