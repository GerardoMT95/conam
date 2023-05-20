import { StatoSoggettoOrdinanzaVO, StatoOrdinanzaVO } from "../vo/select-vo";
import { SoggettoVO } from "../vo/verbale/soggetto-vo";
import { CurrencyPipe } from "@angular/common";

export class TableSoggettiOrdinanza {

    public idSoggetto: number;
    public idSoggettoOrdinanza: number;
    public idPianoRateizzazione: number;
    public identificativoSoggetto: string;
    public idSoggettoVerbale: number;
    public nomeCognomeRagioneSociale: string; 
    public tipoSoggetto: string;
    public ruolo: string;
    public noteSoggetto: string;
    public statoSoggettoOrdinanza: StatoSoggettoOrdinanzaVO;
    public pianoRateizzazioneCreato: boolean;
    public numeroDeterminazione: string;
    public statoOrdinanza: StatoOrdinanzaVO;
    public superatoIlMassimo:boolean;
    public importoSpeseProcessuali: string;
	public importoTitoloSanzione: string;
    public verbale : any;
    public hasMasterIstanza:boolean;
    public hasMasterIstanzaStr:string;
    
    public static setMasterIstanza(value: TableSoggettiOrdinanza,hasMasterIstanza:boolean): TableSoggettiOrdinanza {
        value.hasMasterIstanza=hasMasterIstanza;
        value.hasMasterIstanzaStr=(value.hasMasterIstanza)?'Si':'No';
        return value;
    }

    public static map(value: SoggettoVO): TableSoggettiOrdinanza {
        let tableSoggetti = new TableSoggettiOrdinanza();
        if (value.codiceFiscale == null) {
            tableSoggetti.identificativoSoggetto = value.partitaIva
        } else tableSoggetti.identificativoSoggetto = value.codiceFiscale;
        
        tableSoggetti.idSoggetto = value.id;
        tableSoggetti.idSoggettoVerbale = value.idSoggettoVerbale;
        tableSoggetti.ruolo = value.ruolo != null ? value.ruolo.denominazione : null;
        tableSoggetti.noteSoggetto = value.noteSoggetto != null ? value.noteSoggetto : null;
        tableSoggetti.tipoSoggetto = value.personaFisica ? "PERSONA FISICA" : "PERSONA GIURIDICA";
        tableSoggetti.idSoggettoOrdinanza = value.idSoggettoOrdinanza;
        tableSoggetti.idPianoRateizzazione = value.idPianoRateizzazione;
        tableSoggetti.nomeCognomeRagioneSociale = value.personaFisica ? value.cognome + " " + value.nome : value.ragioneSociale;
        tableSoggetti.statoSoggettoOrdinanza = value.statoSoggettoOrdinanza != null ? value.statoSoggettoOrdinanza : new StatoSoggettoOrdinanzaVO();
        tableSoggetti.pianoRateizzazioneCreato = value.pianoRateizzazioneCreato;
        tableSoggetti.numeroDeterminazione = value.numDetOrdinanza;
        tableSoggetti.statoOrdinanza = value.statoOrdinanza;
        tableSoggetti.superatoIlMassimo = value.superatoIlMassimo;
        tableSoggetti.verbale = value.verbale;
        if (value.hasMasterIstanza == null) {
            value.hasMasterIstanza = false;
        }
        tableSoggetti.hasMasterIstanzaStr=(value.hasMasterIstanza)?'Si':'No';
        tableSoggetti.hasMasterIstanza=value.hasMasterIstanza;
        let currencyPipe = new CurrencyPipe('it-IT');
        if(value.importoTitoloSanzione!=null){
            value.importoSpeseProcessuali = value.importoSpeseProcessuali==null ? 0 : value.importoSpeseProcessuali;
            tableSoggetti.importoSpeseProcessuali = currencyPipe.transform(value.importoSpeseProcessuali, 'EUR', 'symbol');
            tableSoggetti.importoTitoloSanzione = currencyPipe.transform(value.importoTitoloSanzione, 'EUR', 'symbol');
        }
        return tableSoggetti;
    }
}