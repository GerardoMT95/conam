import { ComuneVO, NazioneVO, ProvinciaVO, RegioneVO, RuoloVO, StatoOrdinanzaVO } from "../select-vo";
import { ResidenzaVO } from "./residenza-vo";
import { SoggettoVO } from "./soggetto-vo";

export class SoggettoPregressiVO  extends SoggettoVO {
	

	public residenzaList: ResidenzaVO[];

	constructor() {
		super();
	}

	static editSoggettoFromSoggetto(soggetto: SoggettoPregressiVO, data: SoggettoPregressiVO): SoggettoPregressiVO {
		data.personaFisica = soggetto.personaFisica;
		soggetto = data;
		if (data.ruolo == null) soggetto.ruolo = new RuoloVO();
		if (data.regioneResidenza == null) soggetto.regioneResidenza = new RegioneVO();
		if (data.provinciaResidenza == null) soggetto.provinciaResidenza = new ProvinciaVO();
		if (data.comuneResidenza == null) soggetto.comuneResidenza = new ProvinciaVO();
		if (data.regioneNascita == null) soggetto.regioneNascita = new RegioneVO();
		if (data.comuneNascita == null) soggetto.comuneNascita = new ComuneVO();
		if (data.provinciaNascita == null) soggetto.provinciaNascita = new ProvinciaVO();
		if (data.nazioneNascita == null) soggetto.nazioneNascita = new NazioneVO();
		if (data.nazioneResidenza == null) soggetto.nazioneResidenza = new NazioneVO();
		if (data.statoOrdinanza == null ) soggetto.statoOrdinanza = new StatoOrdinanzaVO();
		return soggetto;
	}
	
	static setResidenzaList(soggetto: SoggettoPregressiVO, residenza:ResidenzaVO):SoggettoPregressiVO{
		if(residenza){
			if(residenza.regioneResidenza!=null)
				soggetto.regioneResidenza = {...residenza.regioneResidenza};
			
			if(residenza.comuneResidenza!=null)
				soggetto.comuneResidenza = {...residenza.comuneResidenza};
			
			if(residenza.provinciaResidenza!=null)
				soggetto.provinciaResidenza = {...residenza.provinciaResidenza};
			
			soggetto.indirizzoResidenza = residenza.indirizzoResidenza;

			soggetto.comuneResidenza = {...residenza.comuneResidenza};

			soggetto.indirizzoResidenza = residenza.indirizzoResidenza;

			soggetto.cap = residenza.cap;
			soggetto.civicoResidenza = residenza.civicoResidenza;

			if(residenza.nazioneResidenza!=null)
				soggetto.nazioneResidenza = {...residenza.nazioneResidenza};
			soggetto.residenzaEstera = residenza.residenzaEstera;

			soggetto.denomComuneResidenzaEstero = residenza.denomComuneResidenzaEstero;

			soggetto.indirizzoResidenza = residenza.indirizzoResidenza;

			soggetto.idStas = residenza.idStas;
		}
		return soggetto;
	}
}