package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table (name="STATE_CANCEL")
public class StateCancel extends OrderStatus {
	
	public StateCancel(Date aDate) {
		super.setDate(aDate);
		super.setName("Cancelled");
	}
	public StateCancel() {
		super.setName("Cancelled");
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
