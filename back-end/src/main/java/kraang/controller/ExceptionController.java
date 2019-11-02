package kraang.controller;

import kraang.controller.dto.common.Response;
import kraang.exception.KraangException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.stream.Collectors;

import static kraang.util.ExceptionUtil.getConstraintViolationMessage;
import static kraang.util.ExceptionUtil.getInvalidDataAccessResourceUsageExceptionMessage;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {


  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
    Response response = new Response();
    response.setMessage(ex.getMessage());
    return new ResponseEntity<>(response, status);
  }

  @Override
  protected ResponseEntity<Object> handleBindException(BindException ex,
                                                       HttpHeaders headers,
                                                       HttpStatus status,
                                                       WebRequest request) {
    return createResponse(ex.getBindingResult(), status);
  }

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                HttpHeaders headers,
                                                                HttpStatus status,
                                                                WebRequest request) {
    return createResponse(ex.getBindingResult(), status);
  }


  @ExceptionHandler(value = KraangException.class)
  protected ResponseEntity<Object> handleKraangException(KraangException e) {
    Response response = new Response();
    response.setMessage(e.getMessage());
    return new ResponseEntity(response, HttpStatus.OK);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
    Response response = new Response();
    response.setMessage(e.getMessage());
    return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
  }

  //db begin
  @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
  protected ResponseEntity<Object>
  handleDataIntegrityViolationException(org.springframework.dao.DataIntegrityViolationException e) {
    Response response = new Response(getConstraintViolationMessage(e));
    response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(InvalidDataAccessResourceUsageException.class)
  protected ResponseEntity<Object>
  handleInvalidDataAccessResourceUsageException(InvalidDataAccessResourceUsageException e) {
    Response response = new Response(getInvalidDataAccessResourceUsageExceptionMessage(e));
    response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(org.hibernate.exception.ConstraintViolationException.class)
  protected ResponseEntity<Object>
  handleConstraintViolationException(org.hibernate.exception.ConstraintViolationException e) {
    Response response = new Response(getConstraintViolationMessage(e));
    response.setMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }


  //db end

  private ResponseEntity createResponse(BindingResult bindingResult, HttpStatus status) {
    var response = new Response(bindingResult
            .getAllErrors()
            .stream()
            .map(x -> x.getDefaultMessage())
            .collect(Collectors.toList()));
    response.setMessage(status.getReasonPhrase());
    return new ResponseEntity(response, status);
  }
}
