package br.upf.gamelog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "usuario")
public class UsuarioEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "nome")
    private String nome;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    @Column(name = "email")
    private String email;

    @Basic(optional = false)
    @NotNull
    @Column(name = "senha")
    private String senha;

    @Basic(optional = false)
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datahorareg")
    private Date datahorareg;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public Date getDatahorareg() { return datahorareg; }
    public void setDatahorareg(Date datahorareg) { this.datahorareg = datahorareg; }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final UsuarioEntity other = (UsuarioEntity) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return nome;
    }
}
