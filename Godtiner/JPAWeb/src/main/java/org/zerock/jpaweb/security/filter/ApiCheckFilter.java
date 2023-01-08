package org.zerock.jpaweb.security.filter;

import lombok.extern.log4j.Log4j2;
import net.minidev.json.JSONObject;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.zerock.jpaweb.security.util.JWTUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
public class ApiCheckFilter extends OncePerRequestFilter {

    private AntPathMatcher antPathMatcher;
    private String pattern;
    private JWTUtil jwtUtil;

    public ApiCheckFilter(String pattern, JWTUtil jwtUtil){
        this.antPathMatcher = new AntPathMatcher();
        this.pattern = pattern;
        this.jwtUtil = jwtUtil;

    }
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws
            ServletException, IOException{

        log.info("Request url:" +request.getRequestURI());
        log.info(antPathMatcher.match(pattern,request.getRequestURI()));

        if(antPathMatcher.match(pattern, request.getRequestURI())){
            log.info("Api check filter.......................");
            log.info("Api check filter.......................");
            log.info("Api check filter.......................");

            boolean checkHeader = checkAuthHeader(request);

            if(checkHeader){
                filterChain.doFilter(request,response);

                return;
            } else{
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                //json 리턴 및 한글깨짐 수정
                response.setContentType("application/json;charset=utf-8");
                JSONObject json = new JSONObject();
                String message = "Fail check api token";

                json.put("code","403");
                json.put("message,",message);

                PrintWriter out = response.getWriter();
                out.println(json);
                return;
            }


        }

        filterChain.doFilter(request,response);
    }

    private boolean checkAuthHeader(HttpServletRequest request){
        boolean checkResult = false;


        String authHeader = request.getHeader("Authorization");

        if(StringUtils.hasText(authHeader) &&authHeader.startsWith("Bearer ")){
            log.info("Authorization exist:" + authHeader);
            try{
                String email = jwtUtil.validateAndExtract(authHeader.substring(7));
                log.info("validate result:"+ email);
                checkResult = email.length() >0;
            } catch (Exception e){
                e.printStackTrace();
            }
           /* if(authHeader.equals("12345678")){
                checkResult = true;

            }*/
        }
        return checkResult;
    }




}
