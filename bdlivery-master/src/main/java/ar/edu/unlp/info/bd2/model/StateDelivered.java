package ar.edu.unlp.info.bd2.model;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="STATE_Delivered")
public class StateDelivered extends OrderStatus {
	public StateDelivered(Date aDate) {
		super.setDate(aDate);
		super.setName("Delivered");
	}
	public StateDelivered() {
		super.setName("Delivered");
	}
	
	public  Boolean canCancel() {
		return false;
	}
	public  Boolean canFinish() {
		return false;
	}
	public  Boolean canDeliver() {
		return false;
	}
}
