package com.mysite.sbb.user.service;

import com.mysite.sbb.exception.DataNotFoundException;
import com.mysite.sbb.user.SiteUser;
import com.mysite.sbb.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender javaMailSender;

    private static final String ADMIN_ADDRESS = "gksms1495@naver.com";

    @Transactional
    public SiteUser create(String username, String email, String password) {
        SiteUser user = new SiteUser();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return user;
    }

    public SiteUser getUser(String username) {
        Optional<SiteUser> siteUser = userRepository.findByusername(username);

        if (siteUser.isPresent()) {
            return siteUser.get();
        } else {
            throw new DataNotFoundException("siteUser Not found");
        }
    }

    public void sendEmail(String email, String userName, String tempPW) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom(ADMIN_ADDRESS);
        message.setSubject(userName + "님의 임시 비밀번호 안내메일입니다.");
        message.setText(userName + " 님의 임시비밀번호는 [" + tempPW + "] 입니다.");

        javaMailSender.send(message);
    }

    @Transactional
    public void UpdatePw(SiteUser user, String pw) {
        user.updatePW(passwordEncoder.encode(pw));
    }


    @Transactional
    public String SetTempPw(SiteUser user) {
        String tempPw = createRandomPW();
        UpdatePw(user, tempPw);

        return tempPw;
    }

    public String createRandomPW() {
        int leftLimit = 48;
        int rightLimit = 122;
        int targetStringLength = 10;
        Random random = new SecureRandom();


        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint,
                        StringBuilder::append)
                .toString();
    }

    public boolean isPwMatch(SiteUser user, String pw) {
        
        if (passwordEncoder.matches(pw,user.getPassword())) {
            log.info("일치");
            return true;
        }
        return false;
    }
}
