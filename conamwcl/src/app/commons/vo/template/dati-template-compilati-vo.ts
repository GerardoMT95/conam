import { DatiTemplateAnnullamentoVO } from "./dati-template-annullamento-vo";

export class DatiTemplateCompilatiVO {
  public studioAvvocato: string;
  public indirizzoAvvocato: string;
  public comuneAvvocato: string;
  public mailAvvocato: string;
  public oggetto: string;
  public descrizione: string;

  //lettera ordinanza
  public riferimentoNormativo: string;

  //sollecito pagamento
  public scadenzaPagamento: string;

  public processiVerbali: string;
  public nomeAvvocato: string;
  public indirizzo: string;
  public oraInizio: string;
  public anno: string;
  public mese: string;
  public giorno: string;
  public dichiara: string;

  //epc
  
  public indirizzoOrganoAccertatoreRiga1: string;
  public indirizzoOrganoAccertatoreRiga2: string;
  public indirizzoOrganoAccertatoreRiga3: string;
  public testoLibero: string;
  //  public datiLetteraAnnullamento: {

  public infoOrganoAccertatore?: string;
  public direzione?: string;
  public settore?: string;
  public oggettoLettera?: string;
  public corpoLettera?: string;
  public salutiLettera?: string;
  public dirigenteLettera?: string;
  public inizialiLettera?: string;
  public sedeEnte?: string;
  public datiLetteraAnnullamento?: DatiTemplateAnnullamentoVO = new DatiTemplateAnnullamentoVO();
  public listaSoggetti?: any;
  public mailSettoreTributi?: string;
  public funzionario?: string;
}
