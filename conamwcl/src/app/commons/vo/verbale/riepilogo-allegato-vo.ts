import { AllegatoVO } from "./allegato-vo";

export class RiepilogoAllegatoVO{
    public verbale: Array<AllegatoVO>;
	public istruttoria: Array<AllegatoVO>;
	public giurisdizionale: Array<AllegatoVO>;
	public rateizzazione: Array<AllegatoVO>;

	constructor() {
		if(!this.verbale) this.verbale = new Array<AllegatoVO> ();
		if(!this.istruttoria) this.istruttoria = new Array<AllegatoVO> ();
		if(!this.giurisdizionale) this.giurisdizionale = new Array<AllegatoVO> ();
		if(!this.rateizzazione) this.rateizzazione = new Array<AllegatoVO> ();
	}
}