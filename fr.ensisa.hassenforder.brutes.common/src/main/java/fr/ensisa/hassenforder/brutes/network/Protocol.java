package fr.ensisa.hassenforder.brutes.network;

public class Protocol {

    public static final int BRUTES_TCP_PORT				= 7777;

    private static final int REQUEST_START				= 1000;
    private static final int REPLY_START				= 2000;

	public static final int REQUEST_POPULATE			= REQUEST_START+1;

	public static final int REQUEST_CREATE				= REQUEST_START+2;
	public static final int REQUEST_GET_CHARACTER		= REQUEST_START+3;
	public static final int REQUEST_GET_PICTURE			= REQUEST_START+4;
	public static final int REQUEST_FIGHT				= REQUEST_START+5;
	public static final int REQUEST_ALL					= REQUEST_START+6;

    public static final int REPLY_OK					= REPLY_START+1;
	public static final int REPLY_KO					= REPLY_START+2;

	public static final int REPLY_CHARACTER				= REPLY_START+3;
	public static final int REPLY_PICTURE				= REPLY_START+4;
	public static final int REPLY_FIGHT 				= REPLY_START+5;
	public static final int REPLY_CHARACTERS			= REPLY_START+6;

}
