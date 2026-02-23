package com.mvalentin.usuario.infrastructure.exceptions;

public class IllegalArgumentsException  extends RuntimeException{

    public IllegalArgumentsException (String mensagem){
        super(mensagem);
    }

    public IllegalArgumentsException (String mensagem, Throwable throwable){
        super(mensagem, throwable);
    }
}
