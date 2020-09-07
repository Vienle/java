package com.pairlearning.expensetrackerapi.enpoint;

import com.pairlearning.expensetrackerapi.entities.User;
import com.pairlearning.expensetrackerapi.exceptions.EtAuthException;
import com.pairlearning.expensetrackerapi.jwt.Constants;
import com.pairlearning.expensetrackerapi.services.UserServices;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserEnpoint {
    @Autowired
    UserServices userServices;

    @PostMapping(value = "login")
    public ResponseEntity<Object> loginUser(@RequestBody Map<String,Object> userMap){
        String email = String.valueOf(userMap.get("email"));
        String password = String.valueOf(userMap.get("password"));
        User user = userServices.validateUser(email,password);
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Object> registerUser(@RequestBody Map<String,Object> userMap){
        String firstName = (String) userMap.get("firstName");
        String lastName = (String) userMap.get("lastName");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");

        User user = userServices.registerUser(firstName,lastName,email,password);
        Map<String,String> map = new HashMap<>();
        map.put("message","registered successfully");
        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }

    private Map<String,Object> generateJWTToken(User user){
        long timestamp = Calendar.getInstance().getTimeInMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256 ,Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDAITY))
                .claim("userId",user.getUserId())
                .claim("email",user.getEmail())
                .claim("firstName",user.getFirstName())
                .claim("lastName",user.getLastName())
                .compact();
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        return map;
    }
}
