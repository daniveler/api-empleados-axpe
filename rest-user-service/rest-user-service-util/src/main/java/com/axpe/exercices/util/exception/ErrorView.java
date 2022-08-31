/*******************************************************************************
 * 
 * Autor: coe.architecture@axpe.com
 * 
 * © Axpe Consulting S.L. 2022. Todos los derechos reservados.
 * 
 ******************************************************************************/

package com.axpe.exercices.util.exception;

import java.io.Serializable;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;

/**
 * Clase para mostrar mensaje de error en la llamada de servicios REST
 * 
 * @author coe.architecture@axpe.com
 *
 */
@Getter
@Builder
public class ErrorView implements Serializable {

	private static final long serialVersionUID = -8643686987756944916L;

	/**
	 * Mensajes de error
	 */
	@Singular
	private List<ErrorMessage> messages;

}
