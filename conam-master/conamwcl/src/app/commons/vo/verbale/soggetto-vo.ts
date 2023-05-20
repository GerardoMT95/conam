import { RegioneVO, ComuneVO, RuoloVO, ProvinciaVO, NazioneVO, StatoSoggettoOrdinanzaVO, StatoOrdinanzaVO } from "../../../commons/vo/select-vo";
import { RelataNotifica } from "../relata-notifica-vo";
import { MinSoggettoVO } from "./min-soggetto-vo";

export class SoggettoVO extends MinSoggettoVO {
	public regioneResidenza: RegioneVO;
	public comuneResidenza: ComuneVO;
	public provinciaResidenza: ProvinciaVO; 
	public indirizzoResidenza: string;
	public cap: string;
	public civicoResidenza: string;
	public nazioneResidenza: NazioneVO;
	public residenzaEstera: boolean;
	public denomComuneResidenzaEstero: string;
	public ruolo: RuoloVO;

	public idStas: number;
	public id: number;
	public idSoggettoVerbale: number;
	public idSoggettoOrdinanza: number;
	public idPianoRateizzazione: number;
	public numDetOrdinanza: string;
	public statoOrdinanza: StatoOrdinanzaVO;
	public statoSoggettoOrdinanza: StatoSoggettoOrdinanzaVO;
	public noteSoggetto: string;

	public indirizzoResidenzaStas: string;
	public civicoResidenzaStas: string;
	public capStas: string;

	public pianoRateizzazioneCreato: boolean;
	public verbaleAudizioneCreato: boolean;
	public idAllegatoVerbaleAudizione: number;
	
	public superatoIlMassimo:boolean;

	public importoSpeseProcessuali: number;
	public importoTitoloSanzione: number;

	public comuneNascitaValido: boolean;
	public verbale: any;
	public hasMasterIstanza: boolean;
	public relataNotifica: RelataNotifica
	constructor() {
		super();
		if (!this.regioneResidenza) this.regioneResidenza = new RegioneVO();
		if (!this.comuneResidenza) this.comuneResidenza = new ComuneVO();
		if (!this.provinciaResidenza) this.provinciaResidenza = new ProvinciaVO();
		if (!this.ruolo) this.ruolo = new RuoloVO();
		if (!this.statoSoggettoOrdinanza) this.statoSoggettoOrdinanza = new StatoSoggettoOrdinanzaVO();
		if (!this.nazioneResidenza ) this.nazioneResidenza = new NazioneVO();
		if (!this.statoOrdinanza ) this.statoOrdinanza = new StatoOrdinanzaVO();
	}

	static editSoggettoFromSoggetto(soggetto: SoggettoVO, data: SoggettoVO): SoggettoVO {
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

}