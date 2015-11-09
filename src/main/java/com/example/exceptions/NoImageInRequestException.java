package com.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Dmitrij on 30.10.2015.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "No image found in the request")
public class NoImageInRequestException extends RuntimeException {
}
