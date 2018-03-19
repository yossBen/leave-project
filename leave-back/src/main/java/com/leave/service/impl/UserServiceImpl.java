package com.leave.service.impl;

import com.leave.dao.UserDAO;
import com.leave.entity.User;
import com.leave.service.JwtService;
import com.leave.service.MailService;
import com.leave.service.UserService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final static Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private MailService mailService;
    @Autowired
    private JwtService jwtService;

    @Value("${front.server}")
    private String frontServer;

    @Override
    public boolean isExist(String email) {
        User user = findByEmail(email);
        return user != null;
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public void saveOrUpdate(User user) {
        userDAO.saveOrUpdate(user);
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User laod(Long userId) {
        return userDAO.load(userId);
    }

    @Override
    public void create(String firstname, String lastname, String password, String email) {
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setEmail(email);
        user.setPassword(password);
        Long id = userDAO.save(user);
        sendValidationAccount(email, id);
    }

    @Override
    public boolean validateAccount(String jwtToken) {
        boolean validate = false;
        try {
            Long userId = jwtService.parseJWT(jwtToken);
            if (userId != null) {
                User user = userDAO.load(userId);
                user.setValidate(true);
                userDAO.update(user);
            }
            validate = true;
        } catch (JwtException e) {
            LOGGER.log(Level.SEVERE, "TOKEN Invalide : " + jwtToken);
        }
        return validate;
    }

    private void sendValidationAccount(String email, Long userId) {
        String token = jwtService.generateJWT(email, userId, 0);
        mailService.sendEmail("Validation de Compte", "Lien pour valider le compte : " + frontServer + "/validate?token=" + token, new String[]{email}, null, null);
    }
}