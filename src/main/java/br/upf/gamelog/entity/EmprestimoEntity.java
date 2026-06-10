package br.upf.gamelog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "emprestimo")
public class EmprestimoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "nome_amigo")
    private String nomeAmigo;

    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.DATE)
    @Column(name = "data_emprestimo")
    private Date dataEmprestimo;

    @Temporal(TemporalType.DATE)
    @Column(name = "previsao_devolucao")
    private Date previsaoDevolucao;

    @Basic(optional = false)
    @NotNull
    @Column(name = "devolvido")
    private boolean devolvido;

    @Column(name = "observacao")
    private String observacao;

    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datahorareg")
    private Date datahorareg;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "titulo_jogo")
    private String tituloJogo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_usuario", referencedColumnName = "id")
    private UsuarioEntity idUsuario;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNomeAmigo() { return nomeAmigo; }
    public void setNomeAmigo(String nomeAmigo) { this.nomeAmigo = nomeAmigo; }

    public Date getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(Date dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

    public Date getPrevisaoDevolucao() { return previsaoDevolucao; }
    public void setPrevisaoDevolucao(Date previsaoDevolucao) { this.previsaoDevolucao = previsaoDevolucao; }

    public boolean isDevolvido() { return devolvido; }
    public void setDevolvido(boolean devolvido) { this.devolvido = devolvido; }

    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }

    public Date getDatahorareg() { return datahorareg; }
    public void setDatahorareg(Date datahorareg) { this.datahorareg = datahorareg; }

    public String getTituloJogo() { return tituloJogo; }
    public void setTituloJogo(String tituloJogo) { this.tituloJogo = tituloJogo; }

    public UsuarioEntity getIdUsuario() { return idUsuario; }
    public void setIdUsuario(UsuarioEntity idUsuario) { this.idUsuario = idUsuario; }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final EmprestimoEntity other = (EmprestimoEntity) obj;
        return Objects.equals(this.id, other.id);
    }
}
