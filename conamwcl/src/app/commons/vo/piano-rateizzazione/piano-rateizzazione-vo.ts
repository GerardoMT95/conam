import { MinPianoRateizzazioneVO } from "./min-piano-rateizzazione-vo";
import { RataVO } from "./rata-vo";

export class PianoRateizzazioneVO extends MinPianoRateizzazioneVO {

    public numeroRate: number;
    public importoSanzione: number;
    public importoSpeseProcessuali: number;
    public importoSpeseNotifica: number;
    public importoMaggiorazione: number;
    public dataScadenzaPrimaRata: string;
    public dataDecorrenzaInteressi: string;
    public rate: Array<RataVO>;
    public isLetteraCreata: boolean;
    public isBollettiniEnable: boolean;
    public isRichiestaBollettiniCreata: boolean;
    public isNotificaCreata: boolean;
    public isNotificaEnable: boolean;
  
}