package com.jm.tfg.restImpl;
import com.jm.tfg.Entidades.User;
import com.jm.tfg.Token.JWT.JwtUtils;
import com.jm.tfg.constantes.TfgConstants;
import com.jm.tfg.rest.UserRest;
import com.jm.tfg.service.UserService;
import com.jm.tfg.utils.TfgUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Slf4j
@RestController
public class UserRestImpl implements UserRest {
    @Autowired
    UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public ResponseEntity<String> registro(Map<String, String> requestMap) {
        try {
                return userService.registro(requestMap);
        }catch (Exception ex){
            log.error("Error al registrarse", ex);
        }
        return TfgUtils.personalizaResponseEntity("", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return userService.login(requestMap);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    public ResponseEntity<String> verifyToken(@RequestHeader("Authorization") String authorizationHeader) {
        // Verificar si el encabezado de autorización está presente y tiene el formato correcto
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            // Devolver un error si el encabezado de autorización no está presente o no tiene el formato correcto
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Se requiere un token de autenticación válido en el encabezado de autorización.");
        }

        // Extraer el token JWT del encabezado de autorización
        String token = authorizationHeader.substring(7); // Quita "Bearer " del encabezado

        // Verificar si el token ha caducado
//        Boolean caducado = jwtUtils.tokenCaducado(token);

        // Imprimir el token JWT en la consola
        System.out.println("Token JWT recibido: " + token);

        // Devolver una respuesta basada en si el token ha caducado o no
        if (true) {
            return ResponseEntity.ok("El token ha caducado");
        } else {
            return ResponseEntity.ok("El token aún es válido");
        }
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        ArrayList<User> arrayError = new ArrayList<>();// Cuando la respuesta des de error, se devuelve un array vacio
        try{
            return  userService.getAllUsers();
        }catch (Exception ex){
            log.error("Error al obtener todas las categorias", ex);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(arrayError);

    }

    @Override
    public ResponseEntity<String> updateUserStatus(Map<String, String> requestMap) {
        try {
            return userService.updateUserStatus(requestMap);
        } catch (Exception ex) {
            log.error("Error al actualizar el estatus del usuario", ex);
            return TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
//Primer commit

/**
 * funciones para iniciar sesión y registro que estan mal
 * @return
 *
 *
//    public ResponseEntity<String> registro(Map<String, String> requestMap) {
//        try {
//            userService.registro(requestMap);
//            return ResponseEntity.ok("Registro exitoso");
//        } catch (Exception ex) {
//            logger.error("Error al registrar usuario", ex); // Utilizamos logger.error para registrar el error
//        }
//        return TfgUtils.personalizaResponseEntity(TfgConstants.DATOS_NO_VALIDOS, HttpStatus.INTERNAL_SERVER_ERROR);
////        return TfgUtils.getResponseEntity("",HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }

//    public ResponseEntity<String> login(Map<String, String> requestMap) {
//        try {
//            // Llamar al servicio de inicio de sesión en userService
//            ResponseEntity<String> response = userService.login(requestMap);
//            return response;
//        } catch (Exception ex) {
//            logger.error("Error al iniciar sesión", ex);
//            return TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

 */

/**
 *     @Override
 *     public ResponseEntity<Object> registro(Map<String, String> requestMap) {
 *         try {
 *             userService.registro(requestMap);
 *             //return ResponseEntity.ok("Registro exitoso");
 *             return ResponseEntity.status(HttpStatus.OK).body(userService.registro(requestMap));
 *
 *         } catch (Exception ex) {
 *             ex.printStackTrace();
 *         }
 *         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(TfgConstants.ALGO_SALE_MAL);
 *     }
 * }
 */
