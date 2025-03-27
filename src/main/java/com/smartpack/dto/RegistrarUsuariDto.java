package com.smartpack.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Classe RegistrarUsuariDto
 */
public class RegistrarUsuariDto {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String role;

    private String nom;

    private String cognom;

    private String telefon;

    private String adreça;

    private String observacio;

    private String secret;

    /**
     * Get Email
     * Obtenir email
     * 
     * @return string
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set Email
     * 
     * @param email string
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get Password
     * 
     * @return string
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set Password
     * 
     * @param password string
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get Role
     * 
     * @return String
     */
    public String getRole() {
        return role;
    }

    /**
     * Set Role
     * Modificar rol
     * 
     * @param role String
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Get Nom
     * 
     * @return String
     */
    public String getNom() {
        return nom;
    }

    /**
     * Set Nom
     * 
     * @param nom String
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Get Cognom
     * 
     * @return String
     */
    public String getCognom() {
        return cognom;
    }

    /**
     * Set Cognom
     * 
     * @param cognom String
     */
    public void setCognom(String cognom) {
        this.cognom = cognom;
    }

    /**
     * Get Telefono
     * 
     * @return String
     */
    public String getTelefon() {
        return telefon;
    }

    /**
     * Set Telefono
     * 
     * @param telefon String
     */
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    /**
     * Get Adreça
     * 
     * @return String
     */
    public String getAdreça() {
        return adreça;
    }

    /**
     * Set Adreça
     * Modificar adreça
     * 
     * @param adreça String
     */
    public void setAdreça(String adreça) {
        this.adreça = adreça;
    }

    /**
     * Get Observacio
     * 
     * @return String
     */
    public String getObservacio() {
        return observacio;
    }

    /**
     * Set Observacio
     * modificar observació
     * 
     * @param observacio string
     */
    public void setObservacio(String observacio) {
        this.observacio = observacio;
    }

    /**
     * Get Secret
     * Obtenir Secret
     * 
     * @return String
     */
    public String getSecret() {
        return secret;
    }

    /**
     * Set Secret
     * modificar Secret
     * 
     * @param secret String
     */
    public void setSecret(String secret) {
        this.secret = secret;
    }

}
