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

@Component({
  selector: "shared-verbale-ricerca",
  templateUrl: "./shared-verbale-ricerca.component.html",
  styleUrls: ["./shared-verbale-ricerca.component.scss"],
})
export class SharedVerbaleRicercaComponent implements OnInit, OnDestroy {
  public subscribers: any = {};

  //tab1
  public entiLegge: Array<EnteVO> = new Array<EnteVO>();
  public tabVerbale: boolean = false;
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
  public ricercaVerbale: RicercaVerbaleRequest;

  @Output() onSearch = new EventEmitter<RicercaVerbaleRequest>();
  @Input() request: RicercaVerbaleRequest;

  @Input() config: ConfigVerbaleRicerca;

  constructor(
    private logger: LoggerService,
    private sharedVerbaleService: SharedVerbaleService,
    private userService: UserService,
    private rifNormativiService: RifNormativiService
  ) {}

  ngOnInit(): void {
    if (this.config == null) this.config = this.createDefaultConfig();

    this.loadedEnte = false;
    this.loadedAmbito = true;
    this.loadedNorma = true;
    this.logger.init(SharedVerbaleRicercaComponent.name);
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
    changeVerbale: () => {
      this.tabVerbale = !this.tabVerbale;
      if (!this.tabVerbale) {
        this.ricercaVerbale.datiVerbale.ente = null;
        this.ricercaVerbale.datiVerbale.numeroProtocollo = null;
        this.ricercaVerbale.datiVerbale.numeroVerbale = null;
        this.ricercaVerbale.datiVerbale.ambito = null;
        this.ricercaVerbale.datiVerbale.norma = null;
        this.statoSelected = null;
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
        this.isTrasgressorePersonaFisica = true;
        this.isTrasgressorePersonaGiuridica = false;
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

  ricercaFascicolo() {
    this.ricercaVerbale.soggettoVerbale = new Array<SoggettoVerbaleRequest>();
    if (this.tabTrasgressore) {
      this.soggettoVerbaleTrasgressore.personaFisica = this.isTrasgressorePersonaFisica;
      this.ricercaVerbale.soggettoVerbale.push(
        this.soggettoVerbaleTrasgressore
      );
    }
    if (this.tabObbligatoInSolido) {
      this.soggettoVerbaleObbligato.personaFisica = this.isObbligatoInSolidoPersonaFisica;
      this.ricercaVerbale.soggettoVerbale.push(this.soggettoVerbaleObbligato);
    }

    if (this.statoSelected != null)
      this.ricercaVerbale.datiVerbale.stato = [this.statoSelected];
    else this.ricercaVerbale.datiVerbale.stato = this.statoVerbaleModel;
    this.onSearch.emit(this.ricercaVerbale);
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

  loadAmbitiByIdEnte() {
    this.loadedAmbito = false;
    this.subscribers.ambitiByIdEnte = this.rifNormativiService
      .getAmbitiByIdEnte(this.ricercaVerbale.datiVerbale.ente.id, true)
      .subscribe(
        (data) => {
          this.ambitoModel = data;
          this.loadedAmbito = true;
        },
        (err) => {
          this.logger.error("Errore nel recupero degli ambiti");
        }
      );
  }

  loadNormeByIdAmbitoAndIdEnte() {
    this.loadedNorma = false;
    this.subscribers.normeByIdAmbitoAndIdEnte = this.rifNormativiService
      .getNormeByIdAmbitoAndIdEnte(
        this.ricercaVerbale.datiVerbale.ambito.id,
        this.ricercaVerbale.datiVerbale.ente.id,
        false
      )
      .subscribe(
        (data) => {
          this.normaModel = data;
          this.loadedNorma = true;
        },
        (err) => {
          this.logger.error("Errore nel recupero delle norme");
        }
      );
  }

  changeEnte() {
    this.ricercaVerbale.datiVerbale.ambito = null;
    this.ricercaVerbale.datiVerbale.norma = null;
    this.loadAmbitiByIdEnte();
  }

  changeAmbito() {
    this.ricercaVerbale.datiVerbale.norma = null;
    this.loadNormeByIdAmbitoAndIdEnte();
  }

  pulisciFiltri() {
    this.ricercaVerbale = new RicercaVerbaleRequest();
    this.soggettoVerbaleTrasgressore = new SoggettoVerbaleRequest();
    this.soggettoVerbaleTrasgressore.tipoSoggetto = "T";
    this.soggettoVerbaleObbligato = new SoggettoVerbaleRequest();
    this.soggettoVerbaleObbligato.tipoSoggetto = "O";
    this.tabVerbale = false;
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
      !this.tabVerbale &&
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
      this.ricercaVerbale = this.request;
      if (this.ricercaVerbale.datiVerbale.ente != null) {
        this.loadAmbitiByIdEnte();
        this.loadNormeByIdAmbitoAndIdEnte();
        this.tabVerbale = true;
      }
      this.soggettoVerbaleTrasgressore = new SoggettoVerbaleRequest();
      this.soggettoVerbaleTrasgressore.tipoSoggetto = "T";
      this.soggettoVerbaleObbligato = new SoggettoVerbaleRequest();
      this.soggettoVerbaleObbligato.tipoSoggetto = "O";
      this.statoSelected =
        this.ricercaVerbale.datiVerbale.stato != null &&
        this.ricercaVerbale.datiVerbale.stato.length == 1
          ? this.ricercaVerbale.datiVerbale.stato[0]
          : null;
      if (
        this.request.soggettoVerbale != null &&
        this.request.soggettoVerbale.length > 0
      ) {
        for (let x of this.request.soggettoVerbale) {
          if (x.tipoSoggetto == "T") {
            this.soggettoVerbaleTrasgressore = x;
            this.tabTrasgressore = true;
            if (x.personaFisica) this.isTrasgressorePersonaFisica = true;
            else this.isTrasgressorePersonaGiuridica = true;
          } else if (x.tipoSoggetto == "O") {
            this.soggettoVerbaleObbligato = x;
            this.tabObbligatoInSolido = true;
            if (x.personaFisica) this.isObbligatoInSolidoPersonaFisica = true;
            else this.isObbligatoInSolidoPersonaGiuridica = true;
          }
        }
      }
    }
  }

  compareFn(c1: SelectVO, c2: SelectVO): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }

  ngOnDestroy(): void {
    this.logger.destroy(SharedVerbaleRicercaComponent.name);
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
