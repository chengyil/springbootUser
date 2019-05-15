package hello;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class User {
	
	private @Id @GeneratedValue Long id;
	private String name;
	private double salary;
	
	public User(String name, double salary) {
		this.name=name;
		this.salary=salary;
	}
	
	User() {}

	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getSalary() {
		return this.salary;
	}
	public void setSalary(double salary) {
		this.salary=salary;
	}
}