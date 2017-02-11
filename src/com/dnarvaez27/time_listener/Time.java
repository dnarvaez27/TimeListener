package com.dnarvaez27.time_listener;

import java.util.Calendar;

/**
 * Clase que modela una Hora especifica.
 * Se modela con horas (Formato 24 Horas) y minutos
 *
 * @author d.narvaez11
 */
public class Time
{
	/**
	 * Hora del dia (Formato 24 Horas)
	 */
	private int hora;

	/**
	 * Minutos de la hora.
	 */
	private int minutos;

	/**
	 * Crea una instancia de una hora especifica
	 *
	 * @param hora Hora del día (Formato 24 Horas)
	 * @param minutos Minutos de la Hora
	 */
	public Time( int hora, int minutos )
	{
		this.hora = hora;
		this.minutos = minutos;
	}

	/**
	 * Aumenta en 1 la cantidad de horas
	 *
	 * @return La instancia de Time
	 */
	public Time addHora( )
	{
		hora++;
		return this;
	}

	/**
	 * Aumenta las horas dada la cantidad dada por parametro
	 *
	 * @param horasAgregar Cantidad de horas a agregar
	 * @return La instancia de Time
	 */
	public Time addHoras( int horasAgregar )
	{
		hora += horasAgregar;
		return this;
	}

	/**
	 * Aumenta en 1 la cantidad de minutos
	 *
	 * @return La instancia de Time
	 */
	public Time addMinuto( )
	{
		minutos++;
		return this;
	}

	/**
	 * Agrega una cantidad dada por parametro a los minutos
	 *
	 * @param minutosAgregar Cantidad de minutos a agregar
	 * @return La instancia de Time
	 */
	public Time addMinutos( int minutosAgregar )
	{
		minutos += minutosAgregar;
		return this;
	}

	/**
	 * Retorna la hora del día en un formato de 24 horas
	 *
	 * @return Hora del día (Formato 24 Horas)
	 */
	public int getHora( )
	{
		return hora;
	}

	/**
	 * Retorna los minutos de la hora
	 *
	 * @return Minutos de la hora
	 */
	public int getMinutos( )
	{
		return minutos;
	}

	/**
	 * Establece la hora del día en formato 24 horas
	 *
	 * @param hora Hora del día en formato 24 horas
	 */
	public void setHora( int hora )
	{
		this.hora = hora;
	}

	/**
	 * Establece los minutos de la hora
	 *
	 * @param minutos Minutos de la hora
	 */
	public void setMinutos( int minutos )
	{
		this.minutos = minutos;
	}

	/**
	 * Retorna un String con la información del Time<br>
	 * Formato hh:mm
	 */
	@Override
	public String toString( )
	{
		String h = String.valueOf( hora );
		h = h.length( ) == 1 ? "0" + h : h;

		String m = String.valueOf( minutos );
		m = m.length( ) == 1 ? "0" + m : m;
		return h + ":" + m;
	}

	/**
	 * Retorna una instancia de la hora con los datos actuales
	 *
	 * @return Instancia de la hora con los datos actuales
	 * @see {@link Calendar#getInstance()}
	 */
	public static Time getInstance( )
	{
		Calendar c = Calendar.getInstance( );
		int h = c.get( Calendar.HOUR_OF_DAY );
		int m = c.get( Calendar.MINUTE );

		return new Time( h, m );
	}
}