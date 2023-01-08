package org.zerock.jpaweb.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zerock.jpaweb.security.util.JWTUtil;

public class JWTTests {

    private JWTUtil jwtUtil;

    @BeforeEach
    public void testBefore(){
        System.out.println("test Before----------------");
        jwtUtil = new JWTUtil();
    }

    @Test
    public void testEncode() throws Exception {
        String email = "u10@rmail.com";
        String str = jwtUtil.generateToken(email);
        System.out.println(str);
    }

    @Test
    public void testValidate() throws Exception{
        String email = "u10@rmail.com";
        String str = jwtUtil.generateToken(email);
        Thread.sleep(5000);
        String resultEmail = jwtUtil.validateAndExtract(str);
        System.out.println(resultEmail);
    }
}
