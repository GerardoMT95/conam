import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  Output,
  EventEmitter,
} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import {
  EnteVO,
  StatoVerbaleVO,
  AmbitoVO,
  NormaVO,
  SelectVO,
} from "../../../commons/vo/select-vo";
import { UserService } from "../../../core/services/user.service";
import { RifNormativiService } from "../../../verbale/services/rif-normativi.service";
import { SoggettoVerbaleRequest } from "../../../commons/request/verbale/soggetto-verbale-request";
import { RicercaVerbaleRequest } from "../../../commons/request/verbale/ricerca-verbale-request";
import { SharedVerbaleService } from "../../service/shared-verbale.service";
import { Constants } from "../../../commons/class/constants";
import { RicercaScrittoDifensivoRequest } from "../../../commons/request/verbale/ricerca-scritto-difensivo-request";

@Component({
  selector: "shared-verbale-ricerca--scritto-difensivo",
  templateUrl: "./shared-verbale-ricerca-scritto-difensivo.component.html",
  styleUrls: ["./shared-verbale-ricerca-scritto-difensivo.component.scss"],
})
export class SharedVerbaleRicercaScrittoDifensivoComponent implements OnInit, OnDestroy {
  public subscribers: any = {};

  //tab1
  public entiLegge: Array<EnteVO> = new Array<EnteVO>();
  public tabScrittoDifensivo: boolean = false;
  public loadedStatoVerbale: boolean;
  public loadedEnte: boolean;
  public loadedAmbito: boolean;
  public loadedNorma: boolean;
  public statoVerbaleModel: Array<StatoVerbaleVO> = new Array<StatoVerbaleVO>();
  public ambitoModel = new Array<AmbitoVO>();
  public normaModel = new Array<NormaVO>();
  public statoSelected: StatoVerbaleVO;

  //tab2
  public tabTrasgressore: boolean = false;
  public isTrasgressorePersonaFisica: boolean = false;
  public isTrasgressorePersonaGiuridica: boolean = false;
  public soggettoVerbaleTrasgressore: SoggettoVerbaleRequest;

  //tab3
  public tabObbligatoInSolido: boolean = false;
  public isObbligatoInSolidoPersonaFisica: boolean = false;
  public soggettoVerbaleObbligato: SoggettoVerbaleRequest;
  public isObbligatoInSolidoPersonaGiuridica: boolean = false;

  //ricerca
  public ricercaScrittoDifensivo: RicercaScrittoDifensivoRequest;

  @Output() onSearch = new EventEmitter<RicercaScrittoDifensivoRequest>();
  @Input() request: RicercaScrittoDifensivoRequest;

  @Input() config: ConfigVerbaleRicerca;

  constructor(
    private logger: LoggerService,
    private sharedVerbaleService: SharedVerbaleService,
    private userService: UserService,
  ) {}

  ngOnInit(): void {
    if (this.config == null) this.config = this.createDefaultConfig();

    this.loadedEnte = false;
    this.loadedAmbito = true;
    this.loadedNorma = true;
    this.logger.init(SharedVerbaleRicercaScrittoDifensivoComponent.name);
    this.userService.profilo$.subscribe((data) => {
      if (data != null) {
        this.entiLegge = data.entiLegge;
        this.loadedEnte = true;
      }
    });

    this.setRequest();

    this.statoVerbale(this.config.tipoRicerca);
  }

  tabActions: any = {
    changeScrittoDifensivo: () => {
  
      this.tabScrittoDifensivo = !this.tabScrittoDifensivo;
      if (!this.tabScrittoDifensivo) {
        this.ricercaScrittoDifensivo.numeroProtocollo = null;
      }else{
        this.tabTrasgressore = false;
        this.soggettoVerbaleTrasgressore = new SoggettoVerbaleRequest();
        this.soggettoVerbaleTrasgressore.tipoSoggetto = "T";
        this.isTrasgressorePersonaFisica = false;
        this.isTrasgressorePersonaGiuridica = false;
       
      }
    },
    changeTrasgressore: () => {
    
      this.tabTrasgressore = !this.tabTrasgressore;
      if (!this.tabTrasgressore) {
        this.soggettoVerbaleTrasgressore = new SoggettoVerbaleRequest();
        this.soggettoVerbaleTrasgressore.tipoSoggetto = "T";
        this.isTrasgressorePersonaFisica = false;
        this.isTrasgressorePersonaGiuridica = false;
      } else {
        this.tabScrittoDifensivo = false;
        this.isTrasgressorePersonaFisica = true;
        this.isTrasgressorePersonaGiuridica = false;
        this.ricercaScrittoDifensivo.numeroProtocollo = null;
      }
    },
    changeObbligatoInSolido: () => {
      this.tabObbligatoInSolido = !this.tabObbligatoInSolido;
      if (!this.tabObbligatoInSolido) {
        this.soggettoVerbaleObbligato = new SoggettoVerbaleRequest();
        this.soggettoVerbaleObbligato.tipoSoggetto = "O";
        this.isObbligatoInSolidoPersonaFisica = false;
        this.isObbligatoInSolidoPersonaGiuridica = false;
      } else {
        this.isObbligatoInSolidoPersonaFisica = true;
        this.isObbligatoInSolidoPersonaGiuridica = false;
      }
    },
    checkTrasgressorePersonaFisica: () => {
      this.isTrasgressorePersonaFisica = !this.isTrasgressorePersonaFisica;
      this.isTrasgressorePersonaGiuridica = false;
      this.soggettoVerbaleTrasgressore = new SoggettoVerbaleRequest();
      this.soggettoVerbaleTrasgressore.tipoSoggetto = "T";
      this.soggettoVerbaleTrasgressore.personaFisica = true;
    },
    checkTrasgressorePersonaGiuridica: () => {
      this.isTrasgressorePersonaGiuridica = !this
        .isTrasgressorePersonaGiuridica;
      this.isTrasgressorePersonaFisica = false;
      this.soggettoVerbaleTrasgressore = new SoggettoVerbaleRequest();
      this.soggettoVerbaleTrasgressore.tipoSoggetto = "T";
      this.soggettoVerbaleTrasgressore.personaFisica = false;
    },
    checkObbligatoInSolidoPersonaFisica: () => {
      this.isObbligatoInSolidoPersonaFisica = !this
        .isObbligatoInSolidoPersonaFisica;
      this.isObbligatoInSolidoPersonaGiuridica = false;
      this.soggettoVerbaleObbligato = new SoggettoVerbaleRequest();
      this.soggettoVerbaleObbligato.tipoSoggetto = "O";
      this.soggettoVerbaleObbligato.personaFisica = true;
    },
    checkObbligatoInsolidoPersonaGiuridica: () => {
      this.isObbligatoInSolidoPersonaGiuridica = !this
        .isObbligatoInSolidoPersonaGiuridica;
      this.isObbligatoInSolidoPersonaFisica = false;
      this.soggettoVerbaleObbligato = new SoggettoVerbaleRequest();
      this.soggettoVerbaleObbligato.tipoSoggetto = "O";
      this.soggettoVerbaleObbligato.personaFisica = false;
    },
  };

  ricercaScritto() {
    if (this.tabTrasgressore) {
      if(this.isTrasgressorePersonaFisica){
        delete this.ricercaScrittoDifensivo.ragioneSociale;
        this.ricercaScrittoDifensivo.cognome = this.soggettoVerbaleTrasgressore.cognome;
        this.ricercaScrittoDifensivo.nome = this.soggettoVerbaleTrasgressore.nome;
      }else{
        delete this.ricercaScrittoDifensivo.cognome;
        delete this.ricercaScrittoDifensivo.nome;
        this.ricercaScrittoDifensivo.ragioneSociale = this.soggettoVerbaleTrasgressore.ragioneSociale;
      }
      delete this.ricercaScrittoDifensivo.numeroProtocollo;
    }
    if (this.tabScrittoDifensivo) {
      delete this.ricercaScrittoDifensivo.cognome;
      delete this.ricercaScrittoDifensivo.nome;
      delete this.ricercaScrittoDifensivo.ragioneSociale;
    }
    this.onSearch.emit(this.ricercaScrittoDifensivo);
  }

  statoVerbale(tipoRicerca: string) {
    this.loadedStatoVerbale = false;
    this.subscribers.statoVerbale = this.sharedVerbaleService
      .getStatiRicercaVerbale()
      .subscribe(
        (data) => {
          if (tipoRicerca == "GC") {
            this.statoVerbaleModel = data.filter(
              (a) =>
                a.id == Constants.ID_STATO_VERBALE_IN_SCRITTI_DIFENSIVI ||
                a.id == Constants.ID_STATO_VERBALE_ACQUISITO ||
                a.id == Constants.ID_STATO_VERBALE_ORDINANZA
            );
          } else if (tipoRicerca == "GCORD") {
            this.statoVerbaleModel = data.filter(
              (a) =>
                a.id == Constants.ID_STATO_VERBALE_IN_SCRITTI_DIFENSIVI ||
                a.id == Constants.ID_STATO_VERBALE_ACQUISITO
            );
          } else {
            this.statoVerbaleModel = data;
          }
          this.loadedStatoVerbale = true;
        },
        (err) => {
          this.logger.error("Errore nel recupero degli stati dei verbali");
        }
      );
  }

 
  pulisciFiltri() {
    this.ricercaScrittoDifensivo = new RicercaScrittoDifensivoRequest();
    this.soggettoVerbaleTrasgressore = new SoggettoVerbaleRequest();
    this.soggettoVerbaleTrasgressore.tipoSoggetto = "T";
    this.soggettoVerbaleObbligato = new SoggettoVerbaleRequest();
    this.soggettoVerbaleObbligato.tipoSoggetto = "O";
    this.tabScrittoDifensivo = false;
    this.tabObbligatoInSolido = false;
    this.isObbligatoInSolidoPersonaFisica = false;
    this.isObbligatoInSolidoPersonaGiuridica = false;
    this.tabTrasgressore = false;
    this.isTrasgressorePersonaFisica = false;
    this.isTrasgressorePersonaGiuridica = false;
    this.statoSelected = null;
  }

  disableForm(validForm: boolean) {
    let condition: boolean =
      !this.tabScrittoDifensivo &&
      (!this.tabTrasgressore ||
        (!this.isTrasgressorePersonaFisica &&
          !this.isTrasgressorePersonaGiuridica)) &&
      (!this.tabObbligatoInSolido ||
        (!this.isObbligatoInSolidoPersonaFisica &&
          !this.isObbligatoInSolidoPersonaGiuridica));
    if (!validForm || condition) return true;
    return false;
  }

  isDisable(field: string, type: string) {
    if (type == "T") {
      let condFisica: Boolean =
        !this.tabTrasgressore || !this.isTrasgressorePersonaFisica;
      if (field == "CFT")
        return (
          condFisica ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.cognome) ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.nome)
        );
      if (field == "CG" || field == "NM")
        return (
          condFisica ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.codiceFiscale)
        );
      let condGiuridica: Boolean =
        !this.tabTrasgressore || !this.isTrasgressorePersonaGiuridica;
      if (field == "DN")
        return (
          condGiuridica ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.partitaIva) ||
          !this.isEmpty(
            this.soggettoVerbaleTrasgressore.codiceFiscalePersGiuridica
          )
        );
      if (field == "PI")
        return (
          condGiuridica ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.ragioneSociale) ||
          !this.isEmpty(
            this.soggettoVerbaleTrasgressore.codiceFiscalePersGiuridica
          )
        );
      if (field == "CFPG")
        return (
          condGiuridica ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.partitaIva) ||
          !this.isEmpty(this.soggettoVerbaleTrasgressore.ragioneSociale)
        );
    }
    if (type == "O") {
      let condFisica: Boolean =
        !this.tabObbligatoInSolido || !this.isObbligatoInSolidoPersonaFisica;
      if (field == "CFT")
        return (
          condFisica ||
          !this.isEmpty(this.soggettoVerbaleObbligato.cognome) ||
          !this.isEmpty(this.soggettoVerbaleObbligato.nome)
        );
      if (field == "CG" || field == "NM")
        return (
          condFisica ||
          !this.isEmpty(this.soggettoVerbaleObbligato.codiceFiscale)
        );
      let condGiuridica: Boolean =
        !this.tabObbligatoInSolido || !this.isObbligatoInSolidoPersonaGiuridica;
      if (field == "DN")
        return (
          condGiuridica ||
          !this.isEmpty(this.soggettoVerbaleObbligato.partitaIva) ||
          !this.isEmpty(
            this.soggettoVerbaleObbligato.codiceFiscalePersGiuridica
          )
        );
      if (field == "PI")
        return (
          condGiuridica ||
          !this.isEmpty(this.soggettoVerbaleObbligato.ragioneSociale) ||
          !this.isEmpty(
            this.soggettoVerbaleObbligato.codiceFiscalePersGiuridica
          )
        );
      if (field == "CFPG")
        return (
          condGiuridica ||
          !this.isEmpty(this.soggettoVerbaleObbligato.partitaIva) ||
          !this.isEmpty(this.soggettoVerbaleObbligato.ragioneSociale)
        );
    }
  }

  isEmpty(str) {
    return !str || 0 === str.length;
  }

  setRequest() {
    if (this.request == null) this.pulisciFiltri();
    else {
      this.ricercaScrittoDifensivo = this.request;
      this.soggettoVerbaleTrasgressore = new SoggettoVerbaleRequest();
      this.soggettoVerbaleTrasgressore.tipoSoggetto = "T";
      this.soggettoVerbaleObbligato = new SoggettoVerbaleRequest();
      this.soggettoVerbaleObbligato.tipoSoggetto = "O";

      if(this.ricercaScrittoDifensivo.numeroProtocollo){
        this.tabScrittoDifensivo = true;
        this.tabTrasgressore = false;
      }else{
        this.tabScrittoDifensivo = false;
        this.tabTrasgressore = true;
      }
      if(this.ricercaScrittoDifensivo.ragioneSociale){
        this.soggettoVerbaleTrasgressore.ragioneSociale=this.ricercaScrittoDifensivo.ragioneSociale;
        this.isTrasgressorePersonaFisica = false;
        this.isTrasgressorePersonaGiuridica = true;
      }else{
        this.soggettoVerbaleTrasgressore.nome=this.ricercaScrittoDifensivo.nome;
        this.soggettoVerbaleTrasgressore.cognome=this.ricercaScrittoDifensivo.cognome;
        this.isTrasgressorePersonaFisica = true;
        this.isTrasgressorePersonaGiuridica = false;
      }
    }
  }

  compareFn(c1: SelectVO, c2: SelectVO): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }

  ngOnDestroy(): void {
    this.logger.destroy(SharedVerbaleRicercaScrittoDifensivoComponent.name);
  }

  private createDefaultConfig(): ConfigVerbaleRicerca {
    return { showFieldStatoVerbale: true, tipoRicerca: "GF" }; //GF: Gestione Fascicolo
  }
}

//aggiungere eventuali altre proprieta
export interface ConfigVerbaleRicerca {
  showFieldStatoVerbale: boolean;
  tipoRicerca: string;
}
