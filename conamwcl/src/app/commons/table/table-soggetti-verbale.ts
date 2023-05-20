import { SoggettoVO } from "../vo/verbale/soggetto-vo";
import { TipoAllegatoVO } from "../vo/select-vo";

export class TableSoggettiVerbale {
    identificativoSoggetto: string;
    nome: string;
    tipoSoggetto: string;
    ruolo: string;
    idVerbaleSoggetto: number;
    ordinanzaCreata: boolean;
    idTipoOrdinanza: TipoAllegatoVO;
    verbaleAudizioneCreato: boolean;
    idAllegatoVerbaleAudizione: number;
    nomeCognomeRagioneSociale: string;

    id: number;
    name: string;
    surname: string;
    birthdayDate: string;
    ragioneSociale: string;
    partitaIva: string;

    public static map(value: SoggettoVO): TableSoggettiVerbale {
        let tableSoggetti = new TableSoggettiVerbale();

        tableSoggetti.idVerbaleSoggetto = value.idSoggettoVerbale;
        tableSoggetti.identificativoSoggetto = value.codiceFiscale;
        tableSoggetti.nome = value.personaFisica ? (value.nome + " " + value.cognome) : (value.ragioneSociale);
        if(value.ruolo){
        tableSoggetti.ruolo = value.ruolo.denominazione;
        }
        tableSoggetti.tipoSoggetto = value.personaFisica ? "PERSONA FISICA" : "PERSONA GIURIDICA";
        tableSoggetti.ordinanzaCreata = value.idSoggettoOrdinanza != null ? true : false;
        tableSoggetti.verbaleAudizioneCreato = value.verbaleAudizioneCreato;
        tableSoggetti.idAllegatoVerbaleAudizione = value.idAllegatoVerbaleAudizione;
        tableSoggetti.nomeCognomeRagioneSociale = value.personaFisica? value.cognome + " " + value.nome : value.ragioneSociale;

        tableSoggetti.id = value.id;
        tableSoggetti.name = value.nome;
        tableSoggetti.surname = value.cognome;
        tableSoggetti.birthdayDate = value.dataNascita;
        tableSoggetti.ragioneSociale = value.ragioneSociale;
        tableSoggetti.partitaIva = value.partitaIva;
        return tableSoggetti;
    }
}