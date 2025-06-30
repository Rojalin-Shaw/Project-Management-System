package com.rojalin.backend.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity     
@Data
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
   private long id;
   
   private String fullname;
   private String email;
   private String password;
   
   @OneToMany(mappedBy = "assignee",cascade = CascadeType.ALL)
   private List<Issue>assignedIssues = new ArrayList<>();
   
   private int projectSize;
   
}
