package br.upf.gamelog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "backlog")
public class BacklogEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "status")
    private String status;

    @Column(name = "horas_jogadas")
    private Integer horasJogadas;

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

    @Temporal(TemporalType.DATE)
    @Column(name = "data_inicio")
    private Date dataInicio;

    @Temporal(TemporalType.DATE)
    @Column(name = "data_termino")
    private Date dataTermino;

    @Basic(optional = false)
    @NotNull
    @Column(name = "titulo_jogo")
    private String tituloJogo;

    @Column(name = "capa_url")
    private String capaUrl;

    /** Review/diario de impressoes — disponivel apenas apos Concluido ou Platinado. */
    @Column(name = "review", columnDefinition = "TEXT")
    private String review;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private UsuarioEntity idUsuario;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getHorasJogadas() { return horasJogadas; }
    public void setHorasJogadas(Integer horasJogadas) { this.horasJogadas = horasJogadas; }

    public Integer getAvaliacao() { return avaliacao; }
    public void setAvaliacao(Integer avaliacao) { this.avaliacao = avaliacao; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public Date getDatahorareg() { return datahorareg; }
    public void setDatahorareg(Date datahorareg) { this.datahorareg = datahorareg; }

    public Date getDataInicio() { return dataInicio; }
    public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }

    public Date getDataTermino() { return dataTermino; }
    public void setDataTermino(Date dataTermino) { this.dataTermino = dataTermino; }

    public String getTituloJogo() { return tituloJogo; }
    public void setTituloJogo(String tituloJogo) { this.tituloJogo = tituloJogo; }

    public String getCapaUrl() { return capaUrl; }
    public void setCapaUrl(String capaUrl) { this.capaUrl = capaUrl; }

    public String getReview() { return review; }
    public void setReview(String review) { this.review = review; }

    public UsuarioEntity getIdUsuario() { return idUsuario; }
    public void setIdUsuario(UsuarioEntity idUsuario) { this.idUsuario = idUsuario; }

    public boolean isAvaliacaoLiberada() {
        return "Concluido".equals(status) || "Platinado".equals(status);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final BacklogEntity other = (BacklogEntity) obj;
        return Objects.equals(this.id, other.id);
    }
}
