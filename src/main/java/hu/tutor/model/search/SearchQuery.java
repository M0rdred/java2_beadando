package hu.tutor.model.search;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "search_query")
public class SearchQuery implements Serializable {

	@Id
	@Column(name = "id")
	private Integer id;
	@Column(name = "subject_name")
	private String subjectName;
	@Column(name = "teacher_name")
	private String teacherName;
	@Column(name = "max_distance")
	private Integer maxDistance;
}
