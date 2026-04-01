package fr.ensisa.hassenforder.flight.network;

public class Protocol {

    public static final int TRAVEL_TCP_PORT				= 7777;

    private static final int REQUEST_START				= 1000;
    private static final int REPLY_START				= 2000;

	public static final int REQUEST_CONNECT 			= REQUEST_START+11;
	public static final int REQUEST_DISCONNECT 			= REQUEST_START+12;
	public static final int REQUEST_GET_ACCOUNT			= REQUEST_START+13;
	public static final int REQUEST_CREATE_ACCOUNT 		= REQUEST_START+14;
	public static final int REQUEST_UPDATE_ACCOUNT 		= REQUEST_START+15;
	public static final int REQUEST_GET_ALL_TRAVELS		= REQUEST_START+16;
	public static final int REQUEST_GET_FILTERED_TRAVELS= REQUEST_START+17;
	public static final int REQUEST_BOOK_TRAVEL			= REQUEST_START+18;
	public static final int REQUEST_GET_TICKET 			= REQUEST_START+19;
	public static final int REQUEST_PAY_TICKET 			= REQUEST_START+20;
	public static final int REQUEST_GET_ALL_TICKETS		= REQUEST_START+21;
	public static final int REQUEST_CANCEL_TICKET 		= REQUEST_START+22;
	public static final int REQUEST_GET_IMAGE	 		= REQUEST_START+23;
		

	public static final int REPLY_NOWAY					= REPLY_START+1;
    public static final int REPLY_KO					= REPLY_START+2;
    public static final int REPLY_OK					= REPLY_START+3;

	public static final int REPLY_USER 					= REPLY_START+11;
	public static final int REPLY_ACCOUNT				= REPLY_START+13;
	public static final int REPLY_TRAVELS				= REPLY_START+16;
	public static final int REPLY_BOOKED_TRAVEL			= REPLY_START+18;
	public static final int REPLY_TICKET				= REPLY_START+19;
	public static final int REPLY_PAYMENT				= REPLY_START+20;
	public static final int REPLY_TICKETS				= REPLY_START+21;
	public static final int REPLY_IMAGE					= REPLY_START+23;
	
	public static final int WITH_FROM	= 0x01;
	public static final int WITH_TO 	= 0x02;
	public static final int WITH_DAY	= 0x04;

}
