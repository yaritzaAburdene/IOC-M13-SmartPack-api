package com.smartpack.services;

import com.smartpack.dto.EmpresaRequestDto;
import com.smartpack.dto.EmpresaResponseDto;
import com.smartpack.models.Empresa;
import com.smartpack.repositories.EmpresaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Classe EmpresaService
 */
@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    /**
     * Constructor EmpresaService
     * 
     * @param usuariRepository EmpresaRepository
     */
    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
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