package com.mvalentin.usuario.infrastructure.exceptions;

public class ResourcesNotFoundException extends RuntimeException {
    public ResourcesNotFoundException(String mensagem ) {
        super(mensagem);
    }
    public ResourcesNotFoundException(String mensagem, Throwable throwable){
        super(mensagem);
    }
}
