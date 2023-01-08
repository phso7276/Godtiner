package org.zerock.jpaweb.security;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testEncode(){
        String password="11111";
        String enPw = passwordEncoder.encode(password);
        System.out.println("enPW: "+enPw);
        boolean matchResult = passwordEncoder.matches(password,enPw);

        System.out.println("matchResult:"+matchResult);
    }
}
