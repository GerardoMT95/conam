import { RegioneVO, ComuneVO, ProvinciaVO, NazioneVO } from "../../../commons/vo/select-vo";
import { SoggettoVO } from "./soggetto-vo";

export class MinSoggettoVO {

	public cognome: string;
	public nome: string;
	public dataNascita: string;
	public regioneNascita: RegioneVO;
	public comuneNascita: ComuneVO;
	public provinciaNascita: ProvinciaVO;
	public nazioneNascita: NazioneVO;
	public nazioneNascitaEstera: boolean;
	public denomComuneNascitaEstero: string;
	public sesso: string;

	public isRegioneNascitaFromStas: boolean;
	public isNazioneNascitaFromStas: boolean;
	public isComuneNascitaEsteroFromStas: boolean;

	public personaFisica: boolean;

	public codiceFiscale: string;
	public ragioneSociale: string;
	public partitaIva: string;

	constructor() {
		if (!this.regioneNascita) this.regioneNascita = new RegioneVO();
		if (!this.comuneNascita) this.comuneNascita = new ComuneVO();
		if (!this.provinciaNascita) this.provinciaNascita = new ProvinciaVO();
		if (!this.nazioneNascita) this.nazioneNascita = new NazioneVO();
	}

	public static constructrFromSoggetto(soggetto: SoggettoVO): MinSoggettoVO {
		let min = new MinSoggettoVO();
		min.cognome = soggetto.cognome;
		min.nome = soggetto.nome;
		min.dataNascita = soggetto.dataNascita;
		min.regioneNascita = soggetto.regioneNascita;
		min.comuneNascita = soggetto.comuneNascita;
		min.provinciaNascita = soggetto.provinciaNascita;
		min.nazioneNascita = soggetto.nazioneNascita;
		min.nazioneNascitaEstera = soggetto.nazioneNascitaEstera;
		min.denomComuneNascitaEstero = soggetto.denomComuneNascitaEstero;
		min.isComuneNascitaEsteroFromStas = soggetto.isComuneNascitaEsteroFromStas;
		min.isNazioneNascitaFromStas = soggetto.isNazioneNascitaFromStas;
		min.isRegioneNascitaFromStas = soggetto.isRegioneNascitaFromStas;
		min.sesso = soggetto.sesso;
		min.personaFisica = soggetto.personaFisica;
		min.codiceFiscale = soggetto.codiceFiscale;
		min.ragioneSociale = soggetto.ragioneSociale;
		min.partitaIva = soggetto.partitaIva;
		return min;

	}
}