package fr.ensisa.hassenforder.flight.server.network.dto;

public class PaymentResult {

	private PaymentState state;
	private int value;
	
	public PaymentResult(PaymentState state, int value) {
		super();
		this.state = state;
		this.value = value;
	}
	
	public PaymentState getState() {
		return state;
	}

	public int getValue() {
		return value;
	}

	public static PaymentResult no() {
		return new PaymentResult(PaymentState.NO, 0);
	}

	public static PaymentResult done(int remains) {
		return new PaymentResult(PaymentState.DONE, remains);
	}
	
	public static PaymentResult needBank(int cost) {
		return new PaymentResult(PaymentState.BANK, cost);
	}
	
	
}
