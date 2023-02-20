package model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity 
@Table(name="User")
public class User{
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long idUser;
	private String email;
	private String name;
	private String username;
	private String pass;
	private Date birthDate;
	
	public User(){}
	public User(String aMail, String aPass, String aUserName, String aName, Date aBirthDate ) {
		this.setName(aName);
		this.setMail(aMail);
		this.setUserName(aUserName);
		this.setPass(aPass);
		this.setBirthDate(aBirthDate);
	}
	public void setId(Long aId) {
		this.idUser=aId;}
	public void setMail(String aMail) {
		this.email=aMail;}
	public void setName(String aName) {
		this.name=aName;}
	public void setUserName(String aUserName) {
		this.username=aUserName;}
	public void setPass(String aPass) {
		this.pass=aPass;}
	public void setBirthDate(Date aBirthDate) {
		this.birthDate=aBirthDate;}
	
	@Column(name="ID")
	public Long getId() {
		return this.idUser;}
	@Column(name="BIRTH_DATE")
	public Date getBirthDate() {
		return this.birthDate;}
	@Column(name="EMAIL")
	public String getEmail() {
		return this.email;}
	@Column(name="NAME")
	public String getName() {
		return this.name;}
	@Column(name="USER_NAME")
	public String getUsername() {
		return this.username;}
	@Column(name="PASS")
	public String getPass() {
		return this.pass;}

}