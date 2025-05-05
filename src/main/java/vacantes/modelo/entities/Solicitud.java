package vacantes.modelo.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(of = "idSolicitud")
@Builder
@Entity
@Table(name = "solicitudes")
public class Solicitud implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_solicitud")
	private int idSolicitud;
	private Date fecha;
	private String archivo;
	private String comentarios;
	private boolean estado; //en la bbdd es TINYINT (1 0) 
	private String curriculum;

	@ManyToOne
	@JoinColumn(name = "id_vacante", referencedColumnName = "id_vacante")
	private Vacante vacante;

	@ManyToOne
	@JoinColumn(name = "email", referencedColumnName = "email")
	private Usuario usuario;

}
