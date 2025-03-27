package com.smartpack.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

/**
 * Control global d'errors
 */
@Slf4j
@RestControllerAdvice(basePackages = "com.smartpack.controllers")
public class GlobalExceptionHandler {

    /**
     * Handle Bad Credentials
     * Error de credencials
     * 
     * @param exception BadCredentialsException
     * @return ProblemDetail
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ProblemDetail handleBadCredentials(BadCredentialsException exception) {
        log.warn("Credencials incorrectes: {}", exception.getMessage());
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, exception.getMessage());
        errorDetail.setProperty("description", "El usuari o contrasenya és incorrecta");
        return errorDetail;
    }

    /**
     * Account Status Exception
     * Error compte bloquejat
     * 
     * @param exception AccountStatusException
     * @return ProblemDetail
     */
    @ExceptionHandler(AccountStatusException.class)
    public ProblemDetail handleAccountStatus(AccountStatusException exception) {
        log.warn("Compte bloquejat o inactiu: {}", exception.getMessage());
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
        errorDetail.setProperty("description", "El compte està bloquejat");
        return errorDetail;
    }

    /**
     * Access Denied Exception
     * Error autorització
     * 
     * @param exception AccessDeniedException
     * @return ProblemDetail
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ProblemDetail handleAccessDenied(AccessDeniedException exception) {
        log.warn("Accés denegat: {}", exception.getMessage());
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
        errorDetail.setProperty("description", "No esteu autoritzat a accedir a aquest recurs");
        return errorDetail;
    }

    /**
     * Signature Exception
     * token invalid
     * 
     * @param exception SignatureException
     * @return ProblemDetail
     */
    @ExceptionHandler(SignatureException.class)
    public ProblemDetail handleSignature(SignatureException exception) {
        log.warn("Signatura JWT invàlida: {}", exception.getMessage());
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
        errorDetail.setProperty("description", "El token  JWT no és vàlida");
        return errorDetail;
    }

    /**
     * Expired Jwt Exception
     * Token caducat
     * 
     * @param exception ExpiredJwtException
     * @return ProblemDetail
     */
    @ExceptionHandler(ExpiredJwtException.class)
    public ProblemDetail handleExpiredJwt(ExpiredJwtException exception) {
        log.warn("Token JWT caducat: {}", exception.getMessage());
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, exception.getMessage());
        errorDetail.setProperty("description", "El token JWT ha caducat");
        return errorDetail;
    }

    /**
     * Entity Not Found Exception
     * Entitat no existeix
     * 
     * @param exception EntityNotFoundException
     * @return ProblemDetail
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ProblemDetail handleEntityNotFound(EntityNotFoundException exception) {
        log.warn("Entitat no trobada: {}", exception.getMessage());
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage());
        errorDetail.setProperty("description", "L'entitat sol·licitada no existeix");
        return errorDetail;
    }

    /**
     * Data Integrity Violation Exception
     * Error en base de dades
     * 
     * @param exception DataIntegrityViolationException
     * @return ProblemDetail
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail handleDataIntegrityViolation(DataIntegrityViolationException exception) {
        log.error("Violació d'integritat de dades: {}", exception.getMessage());
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "Error d'integritat de dades");
        errorDetail.setProperty("description",
                "Hi ha un conflicte amb les dades proporcionades (duplicats, valors nuls, etc.)");
        return errorDetail;
    }

    /**
     * Method Argument Not Valid Exception
     * Parametres invalid
     * 
     * @param exception MethodArgumentNotValidException
     * @return ProblemDetail
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidationErrors(MethodArgumentNotValidException exception) {
        log.warn("Error de validació: {}", exception.getMessage());
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Paràmetres de sol·licitud invàlids");
        errorDetail.setProperty("description", "Comproveu els parametres ");
        return errorDetail;
    }

    /**
     * Method Argument Type Mismatch Exception
     * Error en el tipus de parametres
     * 
     * @param exception MethodArgumentTypeMismatchException
     * @return ProblemDetail
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleTypeMismatch(MethodArgumentTypeMismatchException exception) {
        log.warn("Tipus de paràmetre incorrecte: {}", exception.getMessage());
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                "Tipus de paràmetre incorrecte");
        errorDetail.setProperty("description", "Comproveu el tipus de dades enviat");
        return errorDetail;
    }

    /**
     * ResponseStatusException
     * Excepcions amb codi d'estat específic
     *
     * @param exception ResponseStatusException
     * @return ProblemDetail
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ProblemDetail handleResponseStatus(ResponseStatusException exception) {
        log.warn("Error controlat: {}", exception.getMessage());
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(exception.getStatusCode(), exception.getReason());
        errorDetail.setProperty("description", "Hi ha hagut un problema amb la sol·licitud");
        return errorDetail;
    }

    /**
     * Exception
     * Excepcio generica
     * 
     * @param exception Exception
     * @return ProblemDetail
     */
    @ExceptionHandler(Exception.class)
    public ProblemDetail handleGeneralException(HttpServletRequest request, Exception exception) {
        String path = request.getRequestURI();

        if (path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")) {
            log.warn("Swagger error ignorado: {}", exception.getMessage());
            return null; // <-- ¡no lances una excepción aquí!
        }

        log.error("Error inesperat al servidor", exception);
        ProblemDetail errorDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR,
                exception.getMessage());
        errorDetail.setProperty("description", "Error intern del servidor desconegut.");
        return errorDetail;
    }
}
