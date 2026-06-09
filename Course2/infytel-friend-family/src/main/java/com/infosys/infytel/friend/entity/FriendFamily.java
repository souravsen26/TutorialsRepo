package com.infosys.infytel.friend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="friendFamily")
public class FriendFamily {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id ;
	
	@Column(name="phone_no")
	long phoneNo;
	
	@Column(name = "friend_and_family")
	long friendAndFamily ;

	
	
	public FriendFamily(long phoneNo, long friendAndFamily) {
		super();
		this.phoneNo = phoneNo;
		this.friendAndFamily = friendAndFamily;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(long phoneNo) {
		this.phoneNo = phoneNo;
	}

	public long getFriendAndFamily() {
		return friendAndFamily;
	}

	public void setFriendAndFamily(long friendAndFamily) {
		this.friendAndFamily = friendAndFamily;
	}

	public FriendFamily() {
		super();
		// TODO Auto-generated constructor stub
	}

}
