package br.com.sabiox.sabiox_tool.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "Projects")
public class SABiOxProject {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String title;
	private String description;
	private User user;
	
	protected SABiOxProject() {}
	
	public SABiOxProject(String title, String description) {
		this.title = title;
		this.description = description;
	}

	@Override
	public String toString() {
	  return String.format(
		  "Project[id=%d, title='%s', description='%s']",
		  id, title, description);
	}
	
	public Long getId() {return id;}
	public void setId(Long id) {this.id = id;}

	public String getTitle() {return title;}
	public void setTitle(String title) {this.title = title;}
	
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
}