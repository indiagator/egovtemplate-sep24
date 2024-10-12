package com.egov.egovtemplate.operations;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/ops/v1")
public class OpsRestController {

    @Autowired
    RequestIdExtractor requestIdExtractor;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("status")
    public ResponseEntity<SagaStatus> saga(HttpServletRequest request, HttpServletResponse servletResponse)
    {
        List<Cookie> cookieList = null;
        //Optional<String> healthStatusCookie = Optional.ofNullable(request.getHeader("health_status_cookie"));
        Cookie[] cookies = request.getCookies();
        if(cookies == null)
        {
            cookieList = new ArrayList<>();
        }
        else
        {
            // REFACTOR TO TAKE NULL VALUES INTO ACCOUNT
            cookieList = List.of(cookies);
        }

        if( cookieList.stream().filter(cookie -> cookie.getName().equals("ss-1")).findAny().isEmpty()) // COOKIE_CHECK
        {
            String requestid = requestIdExtractor.getRequestId(request);

            SagaStatus sagaStatus = new SagaStatus();
            sagaStatus.setSagaId(UUID.randomUUID().toString());
            sagaStatus.setStatus("STEP-1-INITIATED");
            sagaStatus.setSagaResponse("STEP-1-INITIATED");

            Cookie cookie1 = new Cookie("ss-1", sagaStatus.getSagaId());
            cookie1.setMaxAge(3600);
            servletResponse.addCookie(cookie1);

            //THE FOLLOWING STATEMENT STORES THE COOKIE IN CACHE AND ALSO ASSUMES A SUCCESSFUL RESPONSE IS RECEIVED | RESPONSE SHOULD COME FROM ANOTHER SERVICE
            redisTemplate.opsForValue().set(String.valueOf(cookie1.getName()+cookie1.getValue()), "STEP-1-COMPLETE");

            return ResponseEntity.ok(sagaStatus);

        }
        else if ( cookieList.stream().filter(cookie -> cookie.getName().equals("ss-2")).findAny().isEmpty() )
        {
            String sagaId = (String) cookieList.stream().filter(cookie -> cookie.getName().equals("ss-1")).findAny().get().getValue();
            String cacheKey = String.valueOf("ss-1"+sagaId);
            SagaStatus sagaStatus = new SagaStatus();

            String cacheValue = (String) redisTemplate.opsForValue().get(cacheKey);

            Cookie cookie2 = null;

            if(cacheValue == null)
            {
                sagaStatus.setSagaId(sagaId);
                sagaStatus.setStatus("STEP-1-IN-PROGRESS");
                sagaStatus.setSagaResponse("STEP-1-IN-PROGRESS");
            }
            else
            {
                String requestid = requestIdExtractor.getRequestId(request);

                cookie2 = new Cookie("ss-2", sagaId);
                cookie2.setMaxAge(3600);
                servletResponse.addCookie(cookie2);

                sagaStatus.setSagaId(sagaId);
                sagaStatus.setStatus("STEP-2-INITIATED &"+cacheValue);
                sagaStatus.setSagaResponse("STEP-2-INITIATED &"+cacheValue);
            }

            //THE FOLLOWING STATEMENT STORES THE COOKIE IN CACHE AND ALSO ASSUMES A SUCCESSFUL RESPONSE IS RECEIVED | RESPONSE SHOULD COME FROM ANOTHER SERVICE
            redisTemplate.opsForValue().set(String.valueOf(cookie2.getName()+cookie2.getValue()), "STEP-2-COMPLETE");

            return ResponseEntity.ok(sagaStatus);
        }
        else if ( cookieList.stream().filter(cookie -> cookie.getName().equals("ss-3")).findAny().isEmpty() )
        {
            String sagaId = (String) cookieList.stream().filter(cookie -> cookie.getName().equals("ss-2")).findAny().get().getValue();
            String cacheKey = String.valueOf("ss-2"+sagaId);
            SagaStatus sagaStatus = new SagaStatus();

            String cacheValue = (String) redisTemplate.opsForValue().get(cacheKey);

            Cookie cookie3 = null;

            if(cacheValue == null)
            {
                sagaStatus.setSagaId(sagaId);
                sagaStatus.setStatus("STEP-2-IN-PROGRESS");
                sagaStatus.setSagaResponse("STEP-2-IN-PROGRESS");
            }
            else
            {
                String requestid = requestIdExtractor.getRequestId(request);

                cookie3 = new Cookie("ss-3", sagaId);
                cookie3.setMaxAge(3600);
                servletResponse.addCookie(cookie3);

                sagaStatus.setSagaId(sagaId);
                sagaStatus.setStatus("STEP-3-INITIATED &"+cacheValue);
                sagaStatus.setSagaResponse("STEP-3-INITIATED &"+cacheValue);
            }

            //THE FOLLOWING STATEMENT STORES THE COOKIE IN CACHE AND ALSO ASSUMES A SUCCESSFUL RESPONSE IS RECEIVED | RESPONSE SHOULD COME FROM ANOTHER SERVICE
            redisTemplate.opsForValue().set(String.valueOf(cookie3.getName()+cookie3.getValue()), "STEP-3-COMPLETE");

            return ResponseEntity.ok(sagaStatus);
        }
        else
        {
            String sagaId = (String) cookieList.stream().filter(cookie -> cookie.getName().equals("ss-3")).findAny().get().getValue();
            String cacheKey = String.valueOf("ss-3"+sagaId);
            SagaStatus sagaStatus = new SagaStatus();

            String cacheValue = (String) redisTemplate.opsForValue().get(cacheKey);

            Cookie cookie3 = null;

            if(cacheValue == null)
            {
                sagaStatus.setSagaId(sagaId);
                sagaStatus.setStatus("STEP-2-IN-PROGRESS");
                sagaStatus.setSagaResponse("STEP-2-IN-PROGRESS");
            }
            else
            {
                sagaStatus.setSagaId(sagaId);
                sagaStatus.setStatus("SAGA-COMPLETE");
                sagaStatus.setSagaResponse("SAGA-COMPLETE");
            }

            return ResponseEntity.ok(sagaStatus);
        }

    }

}
