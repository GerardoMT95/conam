import { SoggettoVO } from "../verbale/soggetto-vo";
import { StatoPianoVO } from "../select-vo";

export class MinPianoRateizzazioneVO {

	public id: number;
	public numProtocollo: string;
	public stato: StatoPianoVO;
	public soggetti: Array<SoggettoVO>;
	public dataCreazione: string;
	public saldo: string;
	public superatoIlMassimo: boolean;

}