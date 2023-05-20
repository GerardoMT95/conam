import { AllegatoMultipleFieldVO } from "../vo/verbale/allegato-multiple-field-vo";


export class SalvaAllegatoMultipleRequest {
    public allegati: Array<AllegatoMultipleFieldVO>;
    public idVerbale?: number;
    public idOrdinanzaVerbaleSoggettoList?: number| number[];
}