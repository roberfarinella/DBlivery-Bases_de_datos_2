package model;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="STATE_SEND")
public class StateSend extends OrderStatus{
	public StateSend() {
		super.setName("Send");
	}
	
	public  Boolean canCancel() {
		return false;
	}
	public  Boolean canFinish() {
		return true;
	}
	public  Boolean canDeliver() {
		return false;
	}
}
