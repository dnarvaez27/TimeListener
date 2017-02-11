package com.dnarvaez27.time_listener;

import java.util.Calendar;

/**
 * Clase que modela un TimeListener <br>
 * Permite la escucha de eventos de tiempo en intervalos o en tiempos especificos
 *
 * @author d.narvaez11
 */
public class TimeListener
{
	/**
	 * Constante para identificar los intervalos de tiempo en milisegundos
	 */
	public static final int MILLISECOND_INTERVAL = 0;

	/**
	 * Constante para identificar los intervalos de tiempo en minutos
	 */
	public static final int MINUTE_INTERVAL = 2;

	/**
	 * Constante para identificar los intervalos de tiempo en segundos
	 */
	public static final int SECOND_INTERVAL = 1;

	/**
	 * Constante para identificar el tiempo como una hora especifica
	 */
	private static final int TIME_SPECIFIC = 3;

	/**
	 * Intervalo para la ejecución del listener
	 */
	private int interval;

	/**
	 * Numero de intervalos transcurridos
	 */
	private int lapses;

	/**
	 * Constante que contiene un minuto dado en milisegundos
	 */
	private final int MINUTO = 60000;

	/**
	 * Constante que contiene un segundo dado en milisegundos
	 */
	private final int SEGUNDO = 1000;

	/**
	 * Thread que lleva el proceso del tiempo
	 */
	private Thread thread;

	/**
	 * Hora especifica para la ejecución del listener
	 */
	private Time time;

	/**
	 * TimeEventListener el cual será notificado cuando algun evento ocurra
	 */
	private TimeEventListener timeEventListener;

	/**
	 * Tipo de intervalo
	 */
	private int typeInterval;

	/**
	 * Construye un TimeListener con intervalo de Milisegundos dado por parametro
	 *
	 * @param timeEventListener TimeEventListener que será ejecutado cuando se lance un evento
	 * @param intervalMillis Intervalo en milisegundos para el lanzamiento de eventos
	 */
	public TimeListener( TimeEventListener timeEventListener, int intervalMillis )
	{
		this( timeEventListener, MILLISECOND_INTERVAL, intervalMillis );
	}

	/**
	 * Construye un TimeListener con un tipo de intervalo dado por parametro al igual que la definicion del mismo
	 *
	 * @param timeEventListener TimeEventListener que será ejecutado cuando se lance un evento
	 * @param typeInterval Tipo de intervalo para el lanzamiento de eventos
	 * @param interval Intervalo en el que se lanzarán eventos
	 */
	public TimeListener( TimeEventListener timeEventListener, int typeInterval, int interval )
	{
		if( timeEventListener == null )
		{
			throw new IllegalArgumentException( "The interface TimeEventListener must not be null" );
		}
		if( interval < 0 )
		{
			throw new IllegalArgumentException( "The interval of lapses must be a positive number" );
		}
		this.timeEventListener = timeEventListener;
		this.interval = interval;
		this.typeInterval = typeInterval;
	}

	/**
	 * Construye un TimeListener con una hora especifica a la cual deberá lanzarse un evento
	 *
	 * @param timeEventListener TimeEventListener que será ejecutado cuando se lance un evento
	 * @param time Hora especifica a la cual se lanzará el evento
	 */
	public TimeListener( TimeEventListener timeEventListener, Time time )
	{
		if( timeEventListener == null )
		{
			throw new IllegalArgumentException( "The interface TimeEventListener must not be null" );
		}
		if( interval < 0 )
		{
			throw new IllegalArgumentException( "The interval of lapses must be a positive number" );
		}

		this.timeEventListener = timeEventListener;
		typeInterval = TIME_SPECIFIC;
		this.time = time;
	}

	/**
	 * Obtiene la hora especifica con la cual el listener ha sido iniciado
	 *
	 * @return Hora especifica con la que se ha inicializado el listener
	 */
	public Time getHora( )
	{
		return time;
	}

	/**
	 * Retorna el numero de intervalos transcurridos
	 *
	 * @return Numero de intervalos transcurridos
	 */
	public int getLapses( )
	{
		return lapses;
	}

	/**
	 * Reinicia el numero de intervalos transcurridos
	 */
	public void resetLapses( )
	{
		lapses = 0;
	}

	/**
	 * Inicia el listener sin delay y sin ningun lapso de muerte
	 */
	public void start( )
	{
		start( 0, -1 );
	}

	/**
	 * Inicia el listener sin delay y con un lapso de muerte.<br>
	 * Cuando el numero de lapsos de intervalos coincide con el parametro, se detiene la ejecución
	 *
	 * @param lapsesToKill Numero de lapsos, los cuales despues de transcurridos el mismo número de lapsos de intervalos,
	 *            se detiene la ejecución
	 */
	public void start( int lapsesToKill )
	{
		start( 0, lapsesToKill );
	}

	/**
	 * Inicia el listener con delay y con un lapso de muerte
	 *
	 * @param delay Delay para empezar la escucha de eventos
	 * @param lapsesToKill Numero de lapsos, los cuales despues de transcurridos el mismo número de lapsos de intervalos,
	 *            se detiene la ejecución
	 */
	public void start( long delay, int lapsesToKill )
	{
		thread = new Thread( "TimeListener Thread" )
		{
			@Override
			public void run( )
			{
				if( delay > 0 )
				{
					try
					{
						sleep( delay );
					}
					catch( InterruptedException e1 )
					{
						e1.printStackTrace( );
						return;
					}
				}
				while( true )
				{
					try
					{
						if( typeInterval != TIME_SPECIFIC )
						{
							long duration = 0;
							if( typeInterval == MINUTE_INTERVAL )
							{
								duration = MINUTO * interval;
							}
							else if( typeInterval == SECOND_INTERVAL )
							{
								duration = SEGUNDO * interval;
							}
							else if( typeInterval == MILLISECOND_INTERVAL )
							{
								duration = interval;
							}
							else
							{
								break;
							}
							sleep( duration );
							lapses++;
							timeEventListener.intervalReached( );
							if( lapsesToKill == lapses )
							{
								break;
							}
						}
						else
						{
							Calendar c = Calendar.getInstance( );
							int sec = c.get( Calendar.SECOND );
							if( sec == 0 )
							{
								c = Calendar.getInstance( );
								int h = c.get( Calendar.HOUR_OF_DAY );
								int m = c.get( Calendar.MINUTE );
								if( ( time.getHora( ) == h ) && ( time.getMinutos( ) == m ) )
								{
									timeEventListener.intervalReached( );
									break;
								}
								sleep( MINUTO );
							}
							else
							{
								sleep( ( 59 - sec ) * SEGUNDO );
							}
						}
					}
					catch( InterruptedException e )
					{
						System.err.println( e.getMessage( ) );
						break;
					}
				}
			}
		};
		thread.start( );
	}

	/**
	 * Inicia el Inicia el listener sin delay y con solo una repetición de lapso
	 */
	public void startAndKill( )
	{
		start( 0, 1 );
	}

	/**
	 * Detiene el listener
	 *
	 * @see Thread#interrupt()
	 */
	public void stop( )
	{
		if( thread != null )
		{
			thread.interrupt( );
		}
	}
}