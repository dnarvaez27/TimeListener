package com.dnarvaez27.time_listener;

/**
 * Interface necesaria para la escucha de eventos de {@link TimeListener}
 *
 * @author d.narvaez11
 */
public interface TimeEventListener
{
	/**
	 * Se invoca cada vez que se cumple el intervalo o se ha llegado a una hora especifica
	 */
	public void intervalReached( );
}