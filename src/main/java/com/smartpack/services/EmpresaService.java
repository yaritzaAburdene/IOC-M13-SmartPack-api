package com.smartpack.services;

import com.smartpack.dto.AssignarUsuariEmpresaRequest;
import com.smartpack.dto.EmpresaRequestDto;
import com.smartpack.dto.EmpresaResponseDto;
import com.smartpack.models.Empresa;
import com.smartpack.models.Usuari;
import com.smartpack.repositories.EmpresaRepository;
import com.smartpack.repositories.UsuariRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Classe EmpresaService
 */
@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    private UsuariRepository usuariRepository;

    /**
     * Constructor EmpresaService
     * 
     * @param EmpresaRepository EmpresaRepository
     * @param usuariRepository  UsuariRepository
     */
    public EmpresaService(EmpresaRepository empresaRepository, UsuariRepository usuariRepository) {
        this.empresaRepository = empresaRepository;
        this.usuariRepository = usuariRepository;
    }

    /**
     * All Empresas
     * Retorna totes les empreses en llistat
     * 
     * @return Empresa llistat
     */
    public List<Empresa> getAllEmpresas() {
        // nou llistat
        List<Empresa> empresas = new ArrayList<>();

        empresaRepository.findAll().forEach(empresas::add); // afegeix elements a nou arrayList

        return empresas;
    }

    /**
     * Get all Empreses
     * retorn totes les empreses
     * 
     * @return EmpresaResponseDto llistat
     */
    public List<EmpresaResponseDto> getAllEmpreses() {
        // recupera el llistat de empresa amb el format: convertToResponseDto
        return empresaRepository.findAll()
                .stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Get Empresa By Id
     * Obté una
     * 
     * @param id long
     * @return EmpresaResponseDto
     */
    public EmpresaResponseDto getEmpresaById(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no trobat"));
        return convertToResponseDto(empresa);
    }

    /**
     * Create Empresa
     * crear una empresa
     * 
     * @param request EmpresaRequestDto
     * @return EmpresaResponseDto
     */
    public EmpresaResponseDto createEmpresa(EmpresaRequestDto request) {
        Empresa empresa = new Empresa();
        empresa.setEmail(request.getEmail());
        empresa.setNif(request.getNif());
        empresa.setAdreça(request.getAdreça());
        empresa.setTelefon(request.getTelefon());
        empresa.setNom(request.getNom());

        Empresa savedEmpresa = empresaRepository.save(empresa);
        return convertToResponseDto(savedEmpresa);
    }

    /**
     * Update Empresa
     * actualitzar empresa
     * 
     * @param id      long
     * @param request EmpresaRequestDto
     * @return EmpresaResponseDto
     */
    public EmpresaResponseDto updateEmpresa(Long id, EmpresaRequestDto request) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no trobada"));

        if (request.getEmail() != null) {
            empresa.setEmail(request.getEmail());
        }
        if (request.getNif() != null) {
            empresa.setNif(request.getNif());
        }
        if (request.getAdreça() != null) {
            empresa.setAdreça(request.getAdreça());
        }
        if (request.getTelefon() != null) {
            empresa.setTelefon(request.getTelefon());
        }
        if (request.getNom() != null) {
            empresa.setNom(request.getNom());
        }

        Empresa updatedEmpresa = empresaRepository.save(empresa);
        return convertToResponseDto(updatedEmpresa);
    }

    /**
     * Desactivar Empresa
     * 
     * @param id long
     */
    public void deactivateEmpresa(Long id) {
        Empresa empresa = empresaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empresa no trobada"));
        empresa.setActive(false);
        empresaRepository.save(empresa);
    }

    /**
     * Assignar usuari
     * 
     * @param request AssignarUsuariEmpresaRequest
     */
    public void assignarUsuariAEmpresa(AssignarUsuariEmpresaRequest request) {
        Empresa empresa = empresaRepository.findById(request.getEmpresaId())
                .orElseThrow(() -> new EntityNotFoundException("Empresa no trobada"));

        Usuari usuari = usuariRepository.findById(request.getUsuariId())
                .orElseThrow(() -> new EntityNotFoundException("Usuari no trobat"));

        if (usuari.getEmpresa() != null && usuari.getEmpresa().getId().equals(empresa.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Aquest usuari ja està assignat a aquesta empresa");
        }

        usuari.setEmpresa(empresa);
        usuariRepository.save(usuari);
    }

    /**
     * Desassignar Usuari
     * 
     * @param usuariId Long
     */
    public void desassignarUsuari(Long usuariId) {
        Usuari usuari = usuariRepository.findById(usuariId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuari no trobat"));

        if (usuari.getEmpresa() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Aquest usuari no està assignat a cap empresa");
        }

        usuari.setEmpresa(null);
        usuariRepository.save(usuari);
    }

    /**
     * Convert To Response DTO
     * 
     * @param empresa Empresa
     * @return EmpresaResponseDto
     */
    private EmpresaResponseDto convertToResponseDto(Empresa empresa) {
        EmpresaResponseDto dto = new EmpresaResponseDto();
        dto.setId(empresa.getId());
        dto.setEmail(empresa.getEmail());
        dto.setNom(empresa.getNom());
        dto.setNif(empresa.getNif());
        dto.setAdreça(empresa.getAdreça());
        dto.setTelefon(empresa.getTelefon());
        return dto;
    }
}