package model;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="STATE_PENDING")
public class StatePending extends OrderStatus{
	public StatePending() {
		super.setName("Pending");
	}
	
	public  Boolean canCancel() {
		return true;
	}
	public  Boolean canFinish() {
		return false;
	}
	public  Boolean canDeliver() {
		return true;
	}
}
