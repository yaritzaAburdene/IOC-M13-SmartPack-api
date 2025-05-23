package com.smartpack.services;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import com.smartpack.dto.ConfirmacioEntregaDto;
import com.smartpack.dto.PaquetRequestDto;
import com.smartpack.dto.PaquetResponseDto;
import com.smartpack.dto.ServeiHistorialDto;
import com.smartpack.dto.ServeiRequestDto;
import com.smartpack.dto.ServeiResponseDto;
import com.smartpack.models.Estat;
import com.smartpack.models.Paquet;
import com.smartpack.models.Servei;
import com.smartpack.models.ServeiHistorial;
import com.smartpack.models.Transportista;
import com.smartpack.models.Usuari;
import com.smartpack.repositories.ServeiRepository;
import com.smartpack.repositories.TransportistaRepository;
import com.smartpack.repositories.UsuariRepository;
import com.smartpack.repositories.FacturaRepository;
import com.smartpack.repositories.PaquetRepository;
import com.smartpack.repositories.ServeiHistorialRepository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Classe ServeiService
 */
@Service
public class ServeiService {

    private final ServeiRepository serveiRepository;
    private final UsuariRepository usuariRepository;
    private final TransportistaRepository transportistaRepository;
    private final PaquetRepository paquetRepository;
    private final QrCodeService qrCodeService;
    private final ServeiHistorialRepository serveiHistorialRepository;
    private final EtiquetaService etiquetaService;
    private final FacturaRepository facturaRepository;

    /**
     * Constructor ServeiService
     * 
     * @param serveiRepository          ServeiRepository
     * @param usuariRepository          UsuariRepository
     * @param transportistaRepository   TransportistaRepository
     * @param paquetRepository          PaquetRepository
     * @param qrCodeService             QrCodeService
     * @param serveiHistorialRepository ServeiHistorialRepository
     * @param etiquetaService           EtiquetaService
     * @param facturaRepository         FacturaRepository
     */
    public ServeiService(ServeiRepository serveiRepository, UsuariRepository usuariRepository,
            TransportistaRepository transportistaRepository, PaquetRepository paquetRepository,
            QrCodeService qrCodeService, ServeiHistorialRepository serveiHistorialRepository,
            EtiquetaService etiquetaService, FacturaRepository facturaRepository) {
        this.serveiRepository = serveiRepository;
        this.usuariRepository = usuariRepository;
        this.transportistaRepository = transportistaRepository;
        this.paquetRepository = paquetRepository;
        this.qrCodeService = qrCodeService;
        this.serveiHistorialRepository = serveiHistorialRepository;
        this.etiquetaService = etiquetaService;
        this.facturaRepository = facturaRepository;
    }

    /**
     * Crear Servei
     * 
     * @param request ServeiRequestDto
     * @return ServeiResponseDto
     */
    public ServeiResponseDto crearServei(ServeiRequestDto request) {
        Usuari usuari = usuariRepository.findById(request.getUsuariId())
                .orElseThrow(() -> new EntityNotFoundException("Usuari no trobat"));

        Transportista transportista = null;

        if (request.getPaquet().getPes() <= 0) {
            throw new IllegalArgumentException("El pes del paquet és obligatori i ha de ser major que 0.");
        }

        // Crear paquet
        Paquet paquet = new Paquet();
        paquet.setDetalls(request.getPaquet().getDetalls());
        paquet.setPes(request.getPaquet().getPes());
        paquet.setMida(request.getPaquet().getMida());
        paquet.setNomDestinatari(request.getPaquet().getNomDestinatari());
        paquet.setAdreçadestinatari(request.getPaquet().getAdreçadestinatari());
        paquet.setTelefondestinatari(request.getPaquet().getTelefondestinatari());

        // Crear Servei
        Servei servei = new Servei();
        servei.setUsuari(usuari);
        servei.setTransportista(transportista);
        servei.setPaquet(paquet);
        servei.setEstat(request.getEstat());

        serveiRepository.save(servei);

        // Generar QR
        // informació necessaria per afegir al qr
        String textQR = "Servei Codi: [ " + servei.getId() + " ] " + paquet.getNomDestinatari() + " - " +
                paquet.getAdreçadestinatari() + " - " +
                paquet.getTelefondestinatari();

        try {
            byte[] qrImage = qrCodeService.generateQrCodeImage(textQR, 200, 200);
            String qrBase64 = Base64.getEncoder().encodeToString(qrImage);
            paquet.setCodiqr(qrBase64);
            paquetRepository.save(paquet);
        } catch (Exception e) {
            // Si falla ho deixem a null
            e.printStackTrace();
            paquet.setCodiqr(null);
            paquetRepository.save(paquet);
        }

        guardarCanviHistorial(servei, "CREACIÓ", "Servei creat per l'usuari");

        return convertirADto(servei);
    }

    /**
     * editarServei
     * 
     * @param id      Long
     * @param request ServeiRequestDto
     * @return ServeiResponseDto
     */
    public ServeiResponseDto editarServei(Long id, ServeiRequestDto request) {
        Servei servei = serveiRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Servei no trobat"));

        Usuari usuari = usuariRepository.findById(request.getUsuariId())
                .orElseThrow(() -> new EntityNotFoundException("Usuari no trobat"));

        Transportista transportista = null;
        if (request.getTransportistaId() != null) {
            transportista = transportistaRepository.findById(request.getTransportistaId())
                    .orElseThrow(() -> new EntityNotFoundException("Transportista no trobat"));
        }

        Paquet paquet = servei.getPaquet(); // mantenim el paquet actual
        PaquetRequestDto paquetDto = request.getPaquet();

        if (paquetDto.getPes() <= 0) {
            throw new IllegalArgumentException("El pes del paquet és obligatori i ha de ser major que 0.");
        }

        if (paquetDto != null) {
            paquet.setDetalls(paquetDto.getDetalls());
            paquet.setPes(paquetDto.getPes());
            paquet.setMida(paquetDto.getMida());
            paquet.setNomDestinatari(paquetDto.getNomDestinatari());
            paquet.setAdreçadestinatari(paquetDto.getAdreçadestinatari());
            paquet.setTelefondestinatari(paquetDto.getTelefondestinatari());
            // Comprovació per generar qr
            if (paquet.getNomDestinatari() != paquetDto.getNomDestinatari() ||
                    paquet.getAdreçadestinatari() != paquetDto.getAdreçadestinatari() ||
                    paquet.getTelefondestinatari() != paquetDto.getTelefondestinatari()) {

                // Generar QR
                // informació necessaria per afegir al qr
                String textQR = "Servei Codi: [ " + servei.getId() + " ] " + paquet.getNomDestinatari() + " - " +
                        paquet.getAdreçadestinatari() + " - " +
                        paquet.getTelefondestinatari();

                try {
                    byte[] qrImage = qrCodeService.generateQrCodeImage(textQR, 200, 200);
                    String qrBase64 = Base64.getEncoder().encodeToString(qrImage);
                    paquet.setCodiqr(qrBase64);
                } catch (Exception e) {
                    // Si falla ho deixem a null
                    e.printStackTrace();
                    paquet.setCodiqr(null);
                }
                guardarCanviHistorial(servei, "MODIFICACIÓ", "Canvi destinatari");
            }
        }

        servei.setEstat(request.getEstat());
        servei.setUsuari(usuari);
        servei.setTransportista(transportista);
        servei.setPaquet(paquet);

        serveiRepository.save(servei);
        return convertirADto(servei);
    }

    /**
     * getServeiById
     * 
     * @param id Long
     * @return ServeiResponseDto
     */
    public ServeiResponseDto getServeiById(Long id) {
        Servei servei = serveiRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Servei no trobat o està desactivat"));
        return convertirADto(servei);
    }

    /**
     * getServeisByUsuariId
     * 
     * @param usuariId Long
     * @return ServeiResponseDto List
     */
    public List<ServeiResponseDto> getServeisByUsuariId(Long usuariId) {
        return serveiRepository.findByUsuariIdAndActiveTrue(usuariId).stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    /**
     * getServeisByTransportistaId
     * 
     * @param transportistaId Long
     * @return ServeiResponseDto List
     */
    public List<ServeiResponseDto> getServeisByTransportistaId(Long transportistaId) {
        return serveiRepository.findByTransportistaIdAndActiveTrue(transportistaId).stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    /**
     * convertirADto
     * 
     * @param servei Servei
     * @return ServeiResponseDto
     */
    private ServeiResponseDto convertirADto(Servei servei) {
        ServeiResponseDto dto = new ServeiResponseDto();
        dto.setId(servei.getId());
        dto.setEstat(servei.getEstat());
        dto.setUsuariId(servei.getUsuari().getId());
        dto.setTransportistaId(servei.getTransportista() != null ? servei.getTransportista().getId() : null);
        dto.setPaquet(convertirPaquetADto(servei.getPaquet()));
        return dto;
    }

    /**
     * assignarTransportista
     * 
     * @param serveiId        Long
     * @param transportistaId Long
     * @return ServeiResponseDto
     */
    @Transactional
    public ServeiResponseDto assignarTransportista(Long serveiId, Long transportistaId) {
        Servei servei = serveiRepository.findById(serveiId)
                .orElseThrow(() -> new EntityNotFoundException("Servei no trobat"));

        Transportista transportista = transportistaRepository.findById(transportistaId)
                .orElseThrow(() -> new EntityNotFoundException("Transportista no trobat"));

        servei.setTransportista(transportista);
        serveiRepository.save(servei);
        guardarCanviHistorial(servei, "Assignació de transportista",
                "S'ha assignat el transportista amb ID: " + transportistaId);

        return convertirADto(servei);
    }

    /**
     * canviarEstatServei
     * 
     * @param serveiId Long
     * @param nouEstat Estat
     * @return ServeiResponseDto
     */
    public ServeiResponseDto canviarEstatServei(Long serveiId, Estat nouEstat) {
        Servei servei = serveiRepository.findById(serveiId)
                .orElseThrow(() -> new EntityNotFoundException("Servei no trobat"));

        servei.setEstat(nouEstat);
        serveiRepository.save(servei);
        guardarCanviHistorial(servei, "ESTAT", "Estat canviat a " + nouEstat);

        return convertirADto(servei);
    }

    /**
     * getAllServeis
     * 
     * @return ServeiResponseDto
     */
    public List<ServeiResponseDto> getAllServeis() {
        return serveiRepository.findByActiveTrue().stream()
                .map(this::convertirADto)
                .collect(Collectors.toList());
    }

    /**
     * desactivarServei
     * 
     * @param serveiId Long
     * @return ServeiResponseDto
     */
    public ServeiResponseDto desactivarServei(Long serveiId) {
        Servei servei = serveiRepository.findById(serveiId)
                .orElseThrow(() -> new EntityNotFoundException("Servei no trobat"));

        servei.setActive(false);
        serveiRepository.save(servei);
        return convertirADto(servei);
    }

    /**
     * regenerarCodiQr
     * 
     * @param serveiId Long
     * @return ServeiResponseDto
     */
    public ServeiResponseDto regenerarCodiQrPorServei(Long serveiId) {
        Servei servei = serveiRepository.findById(serveiId)
                .orElseThrow(() -> new EntityNotFoundException("Servei no trobat"));

        Paquet paquet = servei.getPaquet();
        if (paquet == null) {
            throw new EntityNotFoundException("El servei no té cap paquet associat");
        }

        // Generar QR
        // informació necessaria per afegir al qr
        String textQR = "Servei Codi: [ " + servei.getId() + " ] " + paquet.getNomDestinatari() + " - " +
                paquet.getAdreçadestinatari() + " - " +
                paquet.getTelefondestinatari();

        try {
            byte[] qrImage = qrCodeService.generateQrCodeImage(textQR, 200, 200);
            String qrBase64 = Base64.getEncoder().encodeToString(qrImage);
            paquet.setCodiqr(qrBase64);
            paquetRepository.save(paquet);

            return convertirADto(servei);
        } catch (Exception e) {
            throw new RuntimeException("No s'ha pogut generar el codi QR", e);
        }
    }

    /**
     * getHistorialPerServei
     * 
     * @param serveiId Long
     * @return ServeiHistorial List
     */
    public List<ServeiHistorialDto> getHistorialPerServei(Long serveiId) {
        List<ServeiHistorial> historial = serveiHistorialRepository.findByServeiIdOrderByDataCanviAsc(serveiId);
        return historial.stream()
                .map(ServeiHistorialDto::new)
                .collect(Collectors.toList());
    }

    /**
     * get Etiqueta
     * Genera etiqueta i valida el servei
     * 
     * @param serveiId Long
     * @return byte[]
     */
    public ResponseEntity<byte[]> getEtiqueta(Long serveiId) {

        try {
            Servei servei = serveiRepository.findById(serveiId)
                    .orElseThrow(() -> new EntityNotFoundException("Servei no trobat"));

            Paquet paquet = servei.getPaquet();

            String nom = paquet.getNomDestinatari();
            String adreça = paquet.getAdreçadestinatari();
            String telefon = paquet.getTelefondestinatari();

            // el numero de paquet de moment per defecte es 1.
            String textPaquet = "Servei #: " + serveiId + "  Paquet #: 1";
            String textPes = "Pes: " + paquet.getPes() + "kg";
            String textMida = "Mida: " + paquet.getMida() + "cm";
            String details = paquet.getDetalls();

            // Generar QR
            // informació necessaria per afegir al qr
            String textQR = "Servei Codi: [ " + servei.getId() + " ] " + nom + " - " + adreça + " - " +
                    telefon;
            byte[] qrBytes = qrCodeService.generateQrCodeImage(textQR, 150, 150);

            // Generar etiqueta
            byte[] etiquetaBytes = etiquetaService.generateEtiqueta(nom, adreça, telefon, textPaquet, textPes, textMida,
                    details, qrBytes);

            // Resposta HTTP
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(etiquetaBytes.length);

            return new ResponseEntity<>(etiquetaBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Error generando l'etiqueta", e);
        }
    }

    /**
     * Eliminar Servei
     * 
     * @param serveiId Long
     */
    @Transactional
    public void eliminarServei(Long serveiId) {
        Servei servei = serveiRepository.findById(serveiId)
                .orElseThrow(() -> new EntityNotFoundException("Servei no trobat"));

        if (servei.getEstat() != Estat.ORDENAT) {
            throw new IllegalStateException("Només es pot eliminar un servei amb estat ORDENAT.");
        }

        if (facturaRepository.existsByServeiId(serveiId)) {
            throw new IllegalStateException("No es pot editar un servei que ja té una factura generada.");
        }

        serveiHistorialRepository.deleteByServeiId(serveiId);
        serveiRepository.delete(servei);
    }

    /**
     * confirmarEntrega
     * 
     * @param serveiId Long
     * @param dto      Long
     */
    @Transactional
    public void confirmarEntrega(Long serveiId, ConfirmacioEntregaDto dto) {
        Servei servei = serveiRepository.findById(serveiId)
                .orElseThrow(() -> new EntityNotFoundException("Servei no trobat"));

        if (servei.getEstat() != Estat.TRANSIT) {
            throw new IllegalStateException("El servei no està en estat TRANSIT");
        }

        String telefonCorrecte = servei.getPaquet().getTelefondestinatari();

        if (!telefonCorrecte.equals(dto.getTelefonDestinatari())) {
            throw new IllegalArgumentException("El telèfon no coincideix");
        }

        servei.setEstat(Estat.ENTREGAT);
        serveiRepository.save(servei);
    }

    /**
     * guardarCanviHistorial
     * 
     * @param servei     Servei
     * @param tipusCanvi String
     * @param descripcio String
     */
    private void guardarCanviHistorial(Servei servei, String tipusCanvi, String descripcio) {

        ServeiHistorial historial = new ServeiHistorial();
        historial.setServei(servei);
        historial.setEstat(servei.getEstat().name());
        historial.setTipusCanvi(tipusCanvi);
        historial.setDescripcioCanvi(descripcio);
        historial.setAdreçaDestinatari(servei.getPaquet().getAdreçadestinatari());
        historial.setDataCanvi(LocalDateTime.now());

        serveiHistorialRepository.save(historial);
    }

    /**
     * convertirPaquetADto
     * 
     * @param paquet Paquet
     * @return PaquetResponseDto
     */
    private PaquetResponseDto convertirPaquetADto(Paquet paquet) {
        PaquetResponseDto dto = new PaquetResponseDto();
        dto.setId(paquet.getId());
        dto.setDetalls(paquet.getDetalls());
        dto.setPes(paquet.getPes());
        dto.setMida(paquet.getMida());
        dto.setNomDestinatari(paquet.getNomDestinatari());
        dto.setAdreçadestinatari(paquet.getAdreçadestinatari());
        dto.setTelefondestinatari(paquet.getTelefondestinatari());
        dto.setCodiqr(paquet.getCodiqr());
        return dto;
    }
}
