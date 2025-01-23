package br.com.marcosalencarc.job_management.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    private MessageSource messageSource;

    public ExceptionHandlerController(MessageSource messageSource){
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroMessageDTO>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        List<ErroMessageDTO> errosDTO = new ArrayList<ErroMessageDTO>();
        e.getBindingResult().getFieldErrors().forEach(err ->{
            String message = messageSource.getMessage(err, LocaleContextHolder.getLocale());
            errosDTO.add(new ErroMessageDTO(message, err.getField()));
        });

        return new ResponseEntity<>(errosDTO, HttpStatus.BAD_REQUEST);
    }
    
}
