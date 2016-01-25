package colegio.model.entidades;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the col_respuesta database table.
 * 
 */
@Entity
@Table(name="col_respuesta")
@NamedQuery(name="ColRespuesta.findAll", query="SELECT c FROM ColRespuesta c")
public class ColRespuesta implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="COL_RESPUESTA_RESID_GENERATOR", sequenceName="SEQ_RESPUESTA", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COL_RESPUESTA_RESID_GENERATOR")
	@Column(name="res_id")
	private Integer resId;

	@Column(name="res_fecha")
	private Timestamp resFecha;

	//bi-directional many-to-one association to ColEvaluacionEstudiantil
	@OneToMany(mappedBy="colRespuesta")
	private List<ColEvaluacionEstudiantil> colEvaluacionEstudiantils;

	//bi-directional many-to-one association to ColOpcionesRespuesta
	@ManyToOne
	@JoinColumn(name="opr_id")
	private ColOpcionesRespuesta colOpcionesRespuesta;

	//bi-directional many-to-one association to ColPregunta
	@ManyToOne
	@JoinColumn(name="pre_id")
	private ColPregunta colPregunta;

	public ColRespuesta() {
	}

	public Integer getResId() {
		return this.resId;
	}

	public void setResId(Integer resId) {
		this.resId = resId;
	}

	public Timestamp getResFecha() {
		return this.resFecha;
	}

	public void setResFecha(Timestamp resFecha) {
		this.resFecha = resFecha;
	}

	public List<ColEvaluacionEstudiantil> getColEvaluacionEstudiantils() {
		return this.colEvaluacionEstudiantils;
	}

	public void setColEvaluacionEstudiantils(List<ColEvaluacionEstudiantil> colEvaluacionEstudiantils) {
		this.colEvaluacionEstudiantils = colEvaluacionEstudiantils;
	}

	public ColEvaluacionEstudiantil addColEvaluacionEstudiantil(ColEvaluacionEstudiantil colEvaluacionEstudiantil) {
		getColEvaluacionEstudiantils().add(colEvaluacionEstudiantil);
		colEvaluacionEstudiantil.setColRespuesta(this);

		return colEvaluacionEstudiantil;
	}

	public ColEvaluacionEstudiantil removeColEvaluacionEstudiantil(ColEvaluacionEstudiantil colEvaluacionEstudiantil) {
		getColEvaluacionEstudiantils().remove(colEvaluacionEstudiantil);
		colEvaluacionEstudiantil.setColRespuesta(null);

		return colEvaluacionEstudiantil;
	}

	public ColOpcionesRespuesta getColOpcionesRespuesta() {
		return this.colOpcionesRespuesta;
	}

	public void setColOpcionesRespuesta(ColOpcionesRespuesta colOpcionesRespuesta) {
		this.colOpcionesRespuesta = colOpcionesRespuesta;
	}

	public ColPregunta getColPregunta() {
		return this.colPregunta;
	}

	public void setColPregunta(ColPregunta colPregunta) {
		this.colPregunta = colPregunta;
	}

}