package ar.edu.unlp.info.bd2.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity 
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class OrderStatus {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long idState;
	public void setId(Long aId) {
		this.idState=aId;}
	public Long getId() {
		return this.idState;
	}
	
	private String name;
	private Date date;
	
	public OrderStatus() {}
	
	public void setName(String aName) {
		this.name=aName;}
	public void setDate(Date aDate) {
		this.date=aDate;}
	
	public String getStatus() {
		return this.name;}
	public Date getDate(){
		return this.date;}
	
	public abstract Boolean canCancel();
	public abstract Boolean canFinish();
	public abstract Boolean canDeliver();
	
}