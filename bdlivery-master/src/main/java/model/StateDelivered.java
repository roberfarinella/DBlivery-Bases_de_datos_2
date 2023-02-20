package model;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="STATE_Delivered")
public class StateDelivered extends OrderStatus {
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
