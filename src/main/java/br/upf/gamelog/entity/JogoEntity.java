package br.upf.gamelog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "jogo")
public class JogoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "titulo")
    private String titulo;

    @Basic(optional = false)
    @NotNull
    @Column(name = "plataforma")
    private String plataforma;

    @Basic(optional = false)
    @NotNull
    @Column(name = "genero")
    private String genero;

    @Basic(optional = false)
    @NotNull
    @Column(name = "formato")
    private String formato;

    @Min(1) @Max(10)
    @Column(name = "avaliacao")
    private Integer avaliacao;

    @Column(name = "observacao")
    private String observacao;

    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datahorareg")
    private Date datahorareg;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private UsuarioEntity idUsuario;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getPlataforma() { return plataforma; }
    public void setPlataforma(String plataforma) { this.plataforma = plataforma; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getFormato() { return formato; }
    public void setFormato(String formato) { this.formato = formato; }

    public Integer getAvaliacao() { return avaliacao; }
    public void setAvaliacao(Integer avaliacao) { this.avaliacao = avaliacao; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public Date getDatahorareg() { return datahorareg; }
    public void setDatahorareg(Date datahorareg) { this.datahorareg = datahorareg; }

    public UsuarioEntity getIdUsuario() { return idUsuario; }
    public void setIdUsuario(UsuarioEntity idUsuario) { this.idUsuario = idUsuario; }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final JogoEntity other = (JogoEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return titulo;
    }
}
