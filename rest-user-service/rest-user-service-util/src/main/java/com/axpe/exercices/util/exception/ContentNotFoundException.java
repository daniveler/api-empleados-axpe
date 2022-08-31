/*******************************************************************************
 * 
 * Autor: coe.architecture@axpe.com
 * 
 * © Axpe Consulting S.L. 2022. Todos los derechos reservados.
 * 
 ******************************************************************************/

package com.axpe.exercices.util.exception;

/**
 * Excepción que define un error al no encontrar el recurso solicitado
 * 
 * @author coe.architecture@axpe.com
 */
public class ContentNotFoundException extends DevelopmentException {

    
    private static final long serialVersionUID = 8781638006723151848L;
    
    public ContentNotFoundException(ErrorCode pErrorCode) {
        super(pErrorCode);        
    }

    public ContentNotFoundException(ErrorCode pErrorCode, Exception newOriginalException,
            Object... pArguments) {
        super(pErrorCode, newOriginalException, pArguments);
    }

    public ContentNotFoundException(ErrorCode pErrorCode, Exception newOriginalException) {
        super(pErrorCode, newOriginalException);
    }

    public ContentNotFoundException(ErrorCode pErrorCode, Object... pArguments) {
        super(pErrorCode, pArguments);
    }    
}
