package hu.tutor.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@DiscriminatorValue("admin")
public class Administrator extends User {

}
