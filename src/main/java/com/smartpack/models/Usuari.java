package com.smartpack.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Classe Usuari
 */
@Entity
@Data
@Table(name = "usuari")
public class Usuari implements UserDetails {

    /**
     * ID autogenerat
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Email
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Password
     */
    @Column(nullable = false)
    private String password;

    /**
     * Role
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol role;

    /**
     * nom
     */
    @Column(nullable = false)
    private String nom;

    /**
     * cognom
     */
    @Column(nullable = false)
    private String cognom;

    /**
     * telefon
     */
    @Column(nullable = false)
    private String telefon;

    /**
     * Adreça
     */
    @Column(nullable = false)
    private String adreça;

    /**
     * observacio
     */
    @Column
    private String observacio;

    /**
     * Empresa asociada a usuaio opcional
     */
    @ManyToOne(optional = true)
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    /**
     * Activacio d'usuari
     */
    @Column(name = "active", nullable = false)
    private boolean active = true;

    /**
     * reset token
     */
    @Column(name = "reset_token")
    private String resetToken;

    /**
     * quan es va crear
     */
    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    /**
     * ultima vegada actualitzat
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * Get Password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get Email
     * 
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set Password
     * 
     * @param password String
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Set Email
     * 
     * @param email String
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Set Role
     * 
     * @param role Role
     */
    public void setRole(Rol role) {
        this.role = role;
    }

    /**
     * set Active
     * 
     * @param active boolean
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}