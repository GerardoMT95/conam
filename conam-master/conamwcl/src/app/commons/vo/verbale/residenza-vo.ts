import { RegioneVO, ComuneVO, ProvinciaVO, NazioneVO } from "../select-vo";

export class ResidenzaVO{
    public id: number;
    public regioneResidenza: RegioneVO;
	public comuneResidenza: ComuneVO;
	public provinciaResidenza: ProvinciaVO; 
	public indirizzoResidenza: string;
	public cap: string;
	public civicoResidenza: string;
	public nazioneResidenza: NazioneVO;
	public residenzaEstera: boolean;
	public denomComuneResidenzaEstero: string;
	
	public idStas: number;
	
	
	constructor() {
		if (!this.regioneResidenza) this.regioneResidenza = new RegioneVO();
		if (!this.comuneResidenza) this.comuneResidenza = new ComuneVO();
		if (!this.provinciaResidenza) this.provinciaResidenza = new ProvinciaVO();
		if (!this.nazioneResidenza ) this.nazioneResidenza = new NazioneVO();

	}

	

}