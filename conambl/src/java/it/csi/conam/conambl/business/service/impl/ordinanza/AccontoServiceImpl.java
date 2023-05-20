/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.business.service.impl.ordinanza;

import it.csi.conam.conambl.business.service.common.CommonAllegatoService;
import it.csi.conam.conambl.business.service.ordinanza.AccontoService;
import it.csi.conam.conambl.business.service.ordinanza.OrdinanzaService;
import it.csi.conam.conambl.business.service.util.UtilsDate;
import it.csi.conam.conambl.business.service.util.UtilsDoqui;
import it.csi.conam.conambl.common.Constants;
import it.csi.conam.conambl.common.TipoProtocolloAllegato;
import it.csi.conam.conambl.integration.entity.*;
import it.csi.conam.conambl.integration.mapper.entity.AccontoEntityMapper;
import it.csi.conam.conambl.integration.repositories.*;
import it.csi.conam.conambl.request.ordinanza.SalvaAccontoRequest;
import it.csi.conam.conambl.security.UserDetails;
import it.csi.conam.conambl.vo.ordinanza.AccontoVO;
import it.csi.conam.conambl.vo.verbale.allegato.AllegatoFieldVO;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccontoServiceImpl implements AccontoService {

	@Autowired
	private AccontoEntityMapper accontoEntityMapper;
	
	@Autowired
	private CnmTOrdinanzaRepository cnmTOrdinanzaRepository;
	
	@Autowired
	private CnmTSoggettoRepository cnmTSoggettoRepository;

	@Autowired
	private CnmTAccontoRepository cnmTAccontoRepository;

	@Autowired
	private UtilsDate utilsDate;
	
	@Autowired
	private CommonAllegatoService commonAllegatoService;
	
	@Autowired
	private CnmTUserRepository cnmTUserRepository;
	
	@Autowired
	private CnmTAllegatoRepository cnmTAllegatoRepository;
	
	@Autowired
	private UtilsDoqui utilsDoqui;

	@Autowired
	private OrdinanzaService ordinanzaService;
	
	@Autowired
	private CnmRAllegatoOrdinanzaRepository cnmRAllegatoOrdinanzaRepository;
	
	@Override
	@Transactional
	public AccontoVO salvaAcconto(SalvaAccontoRequest request,UserDetails userDetails) {
		if (request == null)
			throw new IllegalArgumentException("request is null");
		
		AccontoVO acconto = request.getAcconto();
		
		CnmTAcconto cnmTAcconto = null;
		if (acconto.getId() == null) {
			cnmTAcconto = accontoEntityMapper.mapVOtoEntity(acconto);
		} else {
			cnmTAcconto = cnmTAccontoRepository.findOne(acconto.getId().intValue());
		}
		
		CnmTSoggetto cnmTSoggetto = cnmTSoggettoRepository.findOne(acconto.getIdSoggetto());
		if (cnmTSoggetto != null) {
			cnmTAcconto.setCnmTSoggetto(cnmTSoggetto);
		}
		
		CnmTOrdinanza cnmTOrdinanza = cnmTOrdinanzaRepository.findOne(acconto.getIdOrdinanza().intValue());
		if (cnmTOrdinanza != null) {
			cnmTAcconto.setCnmTOrdinanza(cnmTOrdinanza);
		}
		
		cnmTAcconto.setImporto(acconto.getImporto());
		cnmTAcconto.setDataPagamento(utilsDate.asDate(acconto.getDataPagamento()));
		cnmTAcconto.setContoCorrenteVersamento(acconto.getContoCorrenteVersamento());

		request.setIdTipoAllegato(Constants.ALLEGATO_ACCONTO_ORDINANZA);
		
		
		CnmTAllegato cnmTAllegato =
			createAllegato(
				cnmTAcconto,
				request.getFile(),
				request.getFilename(),
				request.getIdTipoAllegato(),
				request.getAllegatoField(),
				userDetails
			);
		cnmTAcconto.setCnmTAllegato(cnmTAllegato);
		
		cnmTAccontoRepository.save(cnmTAcconto);
		ordinanzaService.testIfOrdinanzaPagata(cnmTOrdinanza,userDetails);
		return accontoEntityMapper.mapEntityToVO(cnmTAcconto);
		
	}
	
	
	@Override
	@Transactional
	public AccontoVO salvaAcconto(List<InputPart> data, List<InputPart> file, UserDetails userDetails) {
		SalvaAccontoRequest request = commonAllegatoService.getRequest(data, file, SalvaAccontoRequest.class);
		return salvaAcconto(request,userDetails);
	}
	
	private CnmTAllegato createAllegato(
		CnmTAcconto cnmTAcconto,
		byte[] file,
		String filename,
		Long idTipoAllegato,
		List<AllegatoFieldVO> allegatoField,
		UserDetails userDetails
	) {
		
		
		if (file != null) {
			CnmTUser cnmTUser = cnmTUserRepository.findOne(userDetails.getIdUser());
			
			String folder = null;
			String idEntitaFruitore = null;
			TipoProtocolloAllegato tipoProtocolloAllegato = TipoProtocolloAllegato.NON_PROTOCOLLARE;
			String soggettoActa = null;
			String rootActa = null;
			String tipoActa = null;
			List<CnmTSoggetto> cnmTSoggettoList = null;

			soggettoActa = utilsDoqui.getSoggettoActa(cnmTAcconto.getCnmTOrdinanza());
			CnmTAllegato cnmTAllegato = commonAllegatoService.salvaAllegato(
				file,
				filename,
				idTipoAllegato,
				allegatoField,
				cnmTUser,
				tipoProtocolloAllegato,
				folder,
				idEntitaFruitore,
				true,
				false,
				soggettoActa,
				rootActa,
				0,
				0,
				tipoActa,
				cnmTSoggettoList
			);

			cnmTAllegatoRepository.save(cnmTAllegato);
			
			
			
			// 20210708_LC Jira 150: relazione ordinanza - allegato (meglio ovs???) (XXX)
			CnmRAllegatoOrdinanza cnmRAllegatoOrdinanza = new CnmRAllegatoOrdinanza();
			CnmRAllegatoOrdinanzaPK cnmRAllegatoOrdinanzaPK = new CnmRAllegatoOrdinanzaPK();
			cnmRAllegatoOrdinanzaPK.setIdAllegato(cnmTAllegato.getIdAllegato());
			cnmRAllegatoOrdinanzaPK.setIdOrdinanza(cnmTAcconto.getCnmTOrdinanza().getIdOrdinanza());
			cnmRAllegatoOrdinanza.setCnmTUser(cnmTUser);
			cnmRAllegatoOrdinanza.setDataOraInsert(utilsDate.asTimeStamp(LocalDateTime.now()));
			cnmRAllegatoOrdinanza.setId(cnmRAllegatoOrdinanzaPK);
			cnmRAllegatoOrdinanzaRepository.save(cnmRAllegatoOrdinanza);

			return cnmTAllegato;
		}
		return null;
	}
	
}
