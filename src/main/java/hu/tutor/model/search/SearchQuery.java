package hu.tutor.model.search;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import hu.tutor.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "subjectName", "teacherName", "maxDistance", "owner.id" })
@Entity
@Table(name = "search_query")
public class SearchQuery implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "query_sequence")
	@SequenceGenerator(name = "query_sequence", sequenceName = "query_sq")
	@Column(name = "id")
	private Integer id;
	@Column(name = "subject_name")
	private String subjectName;
	@Column(name = "teacher_name")
	private String teacherName;
	@Column(name = "max_distance")
	private Integer maxDistance;
	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false)
	private User owner;
}
