package br.upf.gamelog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "wishlist")
public class WishlistEntity implements Serializable {

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
    @Column(name = "prioridade")
    private String prioridade;

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

    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public Date getDatahorareg() { return datahorareg; }
    public void setDatahorareg(Date datahorareg) { this.datahorareg = datahorareg; }

    public UsuarioEntity getIdUsuario() { return idUsuario; }
    public void setIdUsuario(UsuarioEntity idUsuario) { this.idUsuario = idUsuario; }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final WishlistEntity other = (WishlistEntity) obj;
        return Objects.equals(this.id, other.id);
    }
}
