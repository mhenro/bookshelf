package ru.khadzhinov.bookshelf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "users")
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID", nullable = false, updatable = false)
    private Long id;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD_HASH", nullable = false)
    private String passwordHash;

    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    
    @Column(name = "ENABLED", nullable = false)
    private boolean enabled;
    
    /* verification token */
    @Column(name = "TOKEN", nullable = true)
    private String token;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "EXPIRE_DATE", nullable = true)
    private Date expireDate;
    
    /* constructors */
    MyUser() {}
    public MyUser(String email, String password, Role role) {
    	this.email = email;
    	this.passwordHash = password;
    	this.role = role;
    	this.enabled = false;
    	this.token = java.util.UUID.randomUUID().toString();
    	this.expireDate = new Date(new Date().getTime() + (24*60*60*1000));	//token will be active until 24 hours
    }

    /* getters, setters */
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    public boolean getEnabled() {
    	return enabled;
    }
    
    public void setEnabled(boolean enabled) {
    	this.enabled = enabled;
    }
    
    public String getToken() {
    	return token;
    }
    
    public void setToken(String token) {
    	this.token = token;
    }
    
    public Date getExpireDate() {
    	return expireDate;
    }
    
    public void setExpireDate(Date expireDate) {
    	this.expireDate = expireDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + //.replaceFirst("@.*", "@***") +
                ", passwordHash='" + passwordHash + 	//.substring(0, 10) +
                ", role=" + role +
                ", enabled=" + enabled +
                ", token=" + token +
                ", date=" + expireDate +
                '}';
    }
}