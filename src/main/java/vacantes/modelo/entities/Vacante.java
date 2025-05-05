package vacantes.modelo.entities;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@EqualsAndHashCode(of="idVacante")
@Builder
@Entity
@Table(name="vacantes")
public class Vacante implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_vacante")
	private int idVacante;
	private String nombre;
	private String descripcion;
	private Date fecha;
	private double salario;
	@Enumerated(EnumType.STRING)
	private Estatus estatus;
	private boolean destacado; //TINYINT en bbdd
	private String imagen;
	private String detalles;
	
	@ManyToOne
    @JoinColumn(name="id_categoria", referencedColumnName="id_categoria")
    private Categoria categoria;


    @ManyToOne
    @JoinColumn(name="id_empresa", referencedColumnName="id_empresa")
    private Empresa empresa;
}
