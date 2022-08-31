/**
 * 
 */
package com.axpe.exercices.presentation.http;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Clase que define las cabeceras específicas del Servio
 * 
 * @author axpe
 *
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomHeaders {
	/** Identificador único de la petición */
	public static final String X_REQUEST_ID = "X-Request-ID";
}
