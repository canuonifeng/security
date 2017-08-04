package com.edu.biz.security.entity;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.edu.biz.base.BaseEntity;
import com.edu.biz.org.entity.Faculty;
import com.edu.biz.org.entity.Organization;
import com.edu.biz.validgroup.Create;
import com.edu.biz.viewgroup.JsonViews;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "user")
public class User extends BaseEntity implements UserDetails {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "用户名不能为空")
	@ApiModelProperty(value = " 用户名")
	private String username;
	
	@ApiModelProperty(value = "姓名")
	private String name;
	
	@ApiModelProperty(value = "电话")
	private String phone;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = " 性别")
    private Gender gender;
	
	@JsonProperty(access = Access.READ_ONLY)
	private Date lastLoginTime;
	
	@JsonProperty(access = Access.READ_ONLY)
	private String lastLoginIp;

	@JsonProperty(access = Access.WRITE_ONLY)
	@NotEmpty(message = "密码不能为空", groups = { Create.class })
	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "昵称")
	private String nickname;
	
	@ManyToOne(targetEntity = Faculty.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "faculty_id")
	@ApiModelProperty(value = "院系")
	@JsonView({JsonViews.Cascade.class})
	private Faculty faculty;

	@NotEmpty(message = "email不能为空")
	@Email(message = "email格式不正确")
	@ApiModelProperty(value = "邮件")
	private String email;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	private String salt;
	
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "用户状态")
	private UserStatus status = UserStatus.enable;

	@ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id") , inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id") )
	@ApiModelProperty(value = "角色列表")
	@JsonView({JsonViews.Cascade.class})
	private List<Role> roles;

	@ManyToOne(targetEntity = Organization.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "org_id")
	@ApiModelProperty(value = "所属组织机构")
	@JsonView({JsonViews.Cascade.class})
	private Organization org;

	public String getUsername() {
		return username;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}
	
	public String getStatusName() {
		return status.getName();
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		this.getRoles().forEach(r -> authorities.add(new SimpleGrantedAuthority(r.getCode())));
		return authorities;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return true;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Gender getGender() {
		return gender;
	}
	
	public String getGenderName() {
		return gender.getName();
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public Faculty getFaculty() {
		return faculty;
	}

	public void setFaculty(Faculty faculty) {
		this.faculty = faculty;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}
	
	
}
