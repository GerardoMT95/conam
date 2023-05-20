import { Injectable, OnDestroy } from "@angular/core";
import { LoggerService } from "../../core/services/logger/logger.service";
import { Config } from "../../shared/module/datatable/classes/config";
import { ProntuarioVO } from "../../commons/vo/prontuario/prontuario-vo";
import { SelectionType } from "../module/datatable/classes/selection-type";

@Injectable()
export class ConfigSharedService implements OnDestroy {

    public configRicercaVerbale: Config = {
        pagination: {
            enable: true
        },
        sort: {
            enable: true,
        },
        selection: {
            enable: true,
        },
        buttonSelection: {
            label: "Dettaglio",
            enable: true
        },
        columns: [
            {
                columnName: 'numero',
                displayName: 'Numero Verbale'
            },
            {
                columnName: 'dataOraAccertamento',
                displayName: 'Data e ora di accertamento'
            },
            {
                columnName: 'numeroProtocollo',
                displayName: 'Numero protocollo'
            },
            {
                columnName: 'enteAccertatore.denominazione',
                displayName: 'Ente accertatore'
            },
            {
                columnName: 'stato.denominazione',
                displayName: 'Stato del fascicolo'
            },
            
            {
                columnName: 'user',
                displayName: 'Utente creatore'
            },
        ]
    };

    public configRicercaOrdinanza: Config = {
        pagination: {
            enable: true
        },
        sort: {
            enable: true,
        },
        selection: {
            enable: true,
        },
        buttonSelection: {
            label: "Dettaglio",
            enable: true
        },
        columns: [
            {
                columnName: 'numeroProtocollo',
                displayName: 'Numero Protocollo'
            },
            {
                columnName: 'numDeterminazione',
                displayName: 'Numero Determinazione'
            },
            {
                columnName: 'tipo.denominazione',
                displayName: 'Tipo Ordinanza'
            },
            {
                columnName: 'stato.denominazione',
                displayName: 'Stato ordinanza'
            },
            {
                columnName: 'dataOrdinanza',
                displayName: 'Data e ora caricamento'
            },
        ]
    };
    public configRicercaScrittoDifensivo: Config = {
        pagination: {
            enable: true
        },
        sort: {
            enable: true,
        },
        selection: {
            enable: true,
        },
        buttonSelection: {
            label: "Dettaglio",
            enable: true
        },
        columns: [
            {
                columnName: 'numeroProtocollo',
                displayName: 'Numero protocollo'
            },
            {
                columnName: 'numVerbaleAccertamento',
                displayName: 'Numero Verbale'
            },
            {
                columnName: 'soggettoStr',
                displayName: 'Soggetto Collegato'
            },
            {
                columnName: 'ambitoStr',
                displayName: 'Ambito'
            },
            {
                columnName: 'normaStr',
                displayName: 'Norma violata'
            }
        ] 
    };


    getConfigDocumentiVerbale(flag: boolean): Config {
        this.configDocumentiVerbale.delete.enable = flag;
        return this.configDocumentiVerbale;
    }

    private configDocumentiVerbale: Config = {
        pagination: {
            enable: false
        },
        selection: {
            enable: false
        },
        sort: {
            enable: false
        },
        delete: {
            enable: false
        },
        columns: [
            {
                columnName: 'theUrl',
                displayName: 'Nome allegato',
                link: {
                    enable: true
                }
            },
            {
                columnName: 'tipo.denominazione',
                displayName: 'Categoria Documento'
            },
            {
                columnName: 'numProtocollo',
                displayName: 'Numero Protocollo'
            },
            {
                columnName: 'dataOraCaricamento',
                displayName: 'Data e ora Caricamento'
            },
            {
                columnName: 'utente',
                displayName: 'Utente creatore'
            }
        ]
    };

    public configDocumentiOrdinanza: Config = {
        pagination: {
            enable: false
        },
        selection: {
            enable: false
        },
        sort: {
            enable: false
        },
        columns: [
            {
                columnName: 'theUrl',
                displayName: 'Nome allegato',
                link: {
                    enable: true
                }
            },
            {
                columnName: 'tipo.denominazione',
                displayName: 'Categoria Documento'
            },
            {
                columnName: 'numProtocollo',
                displayName: 'Numero Protocollo'
            },
            {
                columnName: 'dataOraCaricamento',
                displayName: 'Data e ora Caricamento'
            },
            {
                columnName: 'utente',
                displayName: 'Utente creatore'
            }
        ]
    };

    public configDocumentiRateizzazione: Config = {
        pagination: {
            enable: false
        },
        selection: {
            enable: false
        },
        sort: {
            enable: false
        },
        columns: [
            {
                columnName: 'theUrl',
                displayName: 'Nome allegato',
                link: {
                    enable: true
                }
            },
            {
                columnName: 'tipo.denominazione',
                displayName: 'Categoria Documento'
            },
            {
                columnName: 'numProtocollo',
                displayName: 'Numero Protocollo'
            },
            {
                columnName: 'dataOraCaricamento',
                displayName: 'Data e ora Caricamento'
            },
            {
                columnName: 'utente',
                displayName: 'Utente creatore'
            }
        ]
    };

    public configDocumentiIstruttoria: Config = {
        pagination: {
            enable: false
        },
        selection: {
            enable: false
        },
        sort: {
            enable: false
        },
        columns: [
            {
                columnName: 'theUrl',
                displayName: 'Nome allegato',
                link: {
                    enable: true
                }
            },
            {
                columnName: "nomeCognomeRagioneSocialeSoggetti",
                displayName:"Cognome Nome/Ragione Sociale"
            },
            {
                columnName: 'tipo.denominazione',
                displayName: 'Categoria Documento'
            },
            {
                columnName: 'numProtocollo',
                displayName: 'Numero Protocollo'
            },
            {
                columnName: 'idActa',
                displayName: 'Riferimento Documento Master'
            },
            {
                columnName: 'dataOraCaricamento',
                displayName: 'Data e ora Caricamento'
            },
            {
                columnName: 'utente',
                displayName: 'Utente creatore'
            }
        ]
    };

    public configDocumentiGiurisdizionale: Config = {
        pagination: {
            enable: false
        },
        selection: {
            enable: false
        },
        sort: {
            enable: false
        },
        columns: [
            {
                columnName: 'theUrl',
                displayName: 'Nome allegato',
                link: {
                    enable: true
                }
            },
            {
                columnName: 'tipo.denominazione',
                displayName: 'Categoria Documento'
            },
            {
                columnName: 'numProtocollo',
                displayName: 'Numero Protocollo'
            },           
            {
                columnName: 'numeroDeterminazioneOrdinanza',
                displayName: 'Numero Determinazione Ordinanza'
            },
            {
                columnName: 'dataOraCaricamento',
                displayName: 'Data e ora Caricamento'
            },
            {
                columnName: 'utente',
                displayName: 'Utente creatore'
            }
        ]
    };

    public configProntuario: Config = {
        pagination: {
            enable: true
        },
        selection: {
            enable: false
        },
        sort: {
            enable: true
        },
        edit: {
            enable: true
        },
        delete: {
            enable: true,
            isDeletable: (prontuario: ProntuarioVO) => {
                return prontuario.eliminaEnable;
            }
        },
        filter: {
            enable: true
        },
        columns: [
            {
                columnName: 'ambito.acronimo',
                displayName: 'Acronimo ambito'
            },
            {
                columnName: 'norma.denominazione',
                displayName: 'Norma'
            },
            {
                columnName: 'articolo.denominazione',
                displayName: 'Articolo'
            },
            {
                columnName: 'comma.denominazione',
                displayName: 'Comma'
            },
            {
                columnName: 'lettera.denominazione',
                displayName: 'Lettera'
            },
        ]
    }

    private configRicercaProtocollo: Config = {
        pagination: {
            enable: false
        },
        sort: {
            enable: false,
        },
        selection: {
            enable: true,
        },
        delete: {
            enable: false
        },
        columns: [
            {
                columnName: 'filename',
                displayName: 'Documento individuato',
                link: {
                    enable: true
                }
            },
            {
                columnName: 'numProtocollo',
                displayName: 'Numero protocollo'
            },
            {
                columnName: 'classificazione',
                displayName: 'Classificazione Doqui ACTA'
            }
        ],
        comparatorField: 'documentoUUID'
    };

    getConfigRicercaProtocollo(flag: boolean,multipleSelection:boolean = false): Config {
        let configRicercaProtocolloTmp={...this.configRicercaProtocollo};
        configRicercaProtocolloTmp.selection = {...this.configRicercaProtocollo.selection};
        configRicercaProtocolloTmp.selection.enable = flag;
        if(multipleSelection){
            configRicercaProtocolloTmp.selection.selectionType = SelectionType.MULTIPLE;
        }else{
            configRicercaProtocolloTmp.selection.selectionType = SelectionType.SINGLE;
        }
        return configRicercaProtocolloTmp;
    }

    public configPiani: Config = {
        pagination: {
            enable: true
        },
        sort: {
            enable: true,
        },
        selection: {
            enable: true,
        },
        buttonSelection: {
            label: "Dettaglio",
            enable: true
        },
        columns: [
            {
                columnName: 'numeroRate',
                displayName: 'Numero Rate'
            },
            {
                columnName: 'importoSanzione',
                displayName: 'Importo Sanzione'
            },
            {
                columnName: 'importoSpeseProcessuali',
                displayName: 'Importo spese processuali'
            },
            {
                columnName: 'importoSpeseNotifica',
                displayName: 'Importo spese notifica'
            },
            {
                columnName: 'importoMaggiorazione',
                displayName: 'Importo maggiorazione'
            },
            {
                columnName: 'dataScadenzaPrimaRata',
                displayName: 'Data scadenza prima rata'
            },
        ]
    };

    public configSolleciti: Config = {
        pagination: {
            enable: true
        },
        sort: {
            enable: true,
        },
        selection: {
            enable: true,
        },
        buttonSelection: {
            label: "Dettaglio",
            enable: true
        },
        columns: [
            {
                columnName: 'importoSollecitato',
                displayName: 'Importo sollecitato'
            },
            {
                columnName: 'maggiorazione',
                displayName: 'Maggiorazione'
            },
            {
                columnName: 'numeroProtocollo',
                displayName: 'Numero Protocollo'
            },
            {
                columnName: 'dataScadenza',
                displayName: 'Data scadenza'
            },
        ]
    };

    public configSentenze: Config = {
        pagination: {
            enable: false
        },
        selection: {
            enable: false
        },
        sort: {
            enable: false
        },
        columns: [
            {
                columnName: "identificativoSoggetto",
                displayName:"Identificativo Soggetto"
            },
            {
                columnName: 'nome',
                displayName: 'Cognome Nome/Ragione Sociale'
            },
            {
                columnName: 'posizione',
                displayName: 'Posizione'
            },
            {
                columnName: 'dataDisposizione',
                displayName: 'Data disposizione'
            },
            {
                columnName: 'importoTitoloSanzione',
                displayName: 'Importo a titolo di sanzione'
            }
        ]
    };

    public configRicevute: Config = {
        pagination: {
            enable: false
        },
        selection: {
            enable: false
        },
        sort: {
            enable: false
        },
        columns: [
            {
                columnName: "identificativoSoggetto",
                displayName:"Identificativo Soggetto"
            },
            {
                columnName: 'nomeCognomeRagioneSociale',
                displayName: 'Cognome Nome/Ragione Sociale'
            },
            {
                columnName: 'importoPagato',
                displayName: 'Importo pagato'
            },
            {
                columnName: 'dataPagamento',
                displayName: 'Data pagamento'
            },
            {
                columnName: 'contoCorrenteVersamento',
                displayName: 'Conto corrente versamento'
            }
        ]
    };

    constructor(private logger: LoggerService) {
        this.logger.createService(ConfigSharedService.name);
    }

    ngOnDestroy(): void {
        this.logger.destroyService(ConfigSharedService.name);
    }
}

