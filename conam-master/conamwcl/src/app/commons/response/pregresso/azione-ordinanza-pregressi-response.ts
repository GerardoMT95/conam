import { AzioneOrdinanzaResponse } from "../ordinanza/azione-ordinanza-response";

export class AzioneOrdinanzaPregressiResponse extends AzioneOrdinanzaResponse {
    public dettaglioPianoEnable: boolean;
    public dettaglioSollecitoEnable: boolean;
    public dettaglioDispGiudiceEnable: boolean;
    public dettaglioRicevutaPagamentoEnable: boolean;
}