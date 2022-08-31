/*******************************************************************************
 * 
 * Autor: coe.architecture@axpe.com
 * 
 * © Axpe Consulting S.L. 2022. Todos los derechos reservados.
 * 
 ******************************************************************************/

package com.axpe.exercices.util.exception;

/**
 * Interfaz de la que heredaran las excepciones de la aplicación
 * 
 * @author coe.architecture@axpe.com
 */
public interface GenericException {

	/**
	 * Retorna el código de error.
	 * @return Código que identifica el error producido
	 */
	ErrorCode getErrorCode();
}
