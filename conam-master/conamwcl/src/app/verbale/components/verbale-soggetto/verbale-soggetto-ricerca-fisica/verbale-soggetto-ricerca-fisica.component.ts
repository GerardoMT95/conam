import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  Output,
  EventEmitter,
  OnChanges,
  ViewChild,
} from "@angular/core";
import { LoggerService } from "../../../../core/services/logger/logger.service";
import {
  ProvinciaVO,
  ComuneVO,
  RegioneVO,
  NazioneVO,
} from "../../../../commons/vo/select-vo";
import { SoggettoVO } from "../../../../commons/vo/verbale/soggetto-vo";
import { MinSoggettoVO } from "../../../../commons/vo/verbale/min-soggetto-vo";
import { ComboVO, ComboForm } from "../../../../commons/class/constants";
import { LuoghiService } from "../../../../core/services/luoghi.service";
import { NgForm } from "@angular/forms";
import { DestroySubscribers } from "../../../../core/decorator/destroy-subscribers";
import { THIS_EXPR } from "@angular/compiler/src/output/output_ast";

declare var $: any;

@Component({
  selector: "verbale-soggetto-ricerca-fisica",
  templateUrl: "./verbale-soggetto-ricerca-fisica.component.html",
})
@DestroySubscribers()
export class VerbaleSoggettoRicercaFisicaComponent
  implements OnInit, OnDestroy, OnChanges {
  @Input()
  soggetto: SoggettoVO;
  @Output()
  ricerca: EventEmitter<MinSoggettoVO> = new EventEmitter<MinSoggettoVO>();
  @Output()
  pulisci: EventEmitter<MinSoggettoVO> = new EventEmitter<MinSoggettoVO>();
  @Input()
  modalita: string; //R o E
  @Input()
  comuneEstero: boolean;
  @Input()
  comuneEsteroDisabled: boolean;
  @Output()
  formValid: EventEmitter<boolean> = new EventEmitter<boolean>();
  @ViewChild("soggettiFisico")
  private soggettiFisico: NgForm;

  public sesso: Array<ComboVO> = ComboForm.SESSO;

  public subscribers: any = {};

  public loaderRegioni: boolean;
  public loaderProvince: boolean = true;
  public loaderComuni: boolean = true;
  public loaderNazioni: boolean;
  public regioneModel: Array<RegioneVO> = new Array<RegioneVO>();
  public provinciaModel: Array<ProvinciaVO>;
  public comuneModel: Array<ComuneVO>;
  public nazioneNascitaModel: Array<NazioneVO> = new Array<NazioneVO>();
  public isRegioneStas: boolean;
  //utilizzato per validazione
  copySoggetto: SoggettoVO;

  constructor(
    private logger: LoggerService,
    private luoghiService: LuoghiService
  ) {}

  ngOnInit(): void {
    this.logger.init(VerbaleSoggettoRicercaFisicaComponent.name);
    if (!this.modalita) {
      this.modalita = "R";
      this.isRegioneStas = this.soggetto.regioneNascita.id != null;
    }
    this.loadNazioni();
    this.loadRegions();
    this.manageDatePicker();

    //VALIDAZIONE FORM
    if (this.modalita == "E") {
      this.subscribers.sog = this.soggettiFisico.valueChanges.subscribe(
        (data) => {
          if (this.soggettiFisico.status == "DISABLED")
            this.formValid.next(true);
          else this.formValid.next(this.soggettiFisico.valid);
        }
      );
    }
  }

  loadRegions() {
    this.loaderRegioni = false;
    this.subscribers.regioni = this.luoghiService.getRegioniNascita().subscribe(
      (data) => {
        this.regioneModel = data;
        this.loaderRegioni = true;
      },
      (err) => {
        this.logger.error("Errore nel recupero delle regioni");
      }
    );
  }

  manageLuoghiBySoggetto() {
    if (
      this.soggetto.regioneNascita != null &&
      this.soggetto.regioneNascita.id != null
    ) {
      this.loadProvince(this.soggetto.regioneNascita.id);
    }
    if (
      this.soggetto.provinciaNascita != null &&
      this.soggetto.provinciaNascita.id != null
    ) {
      this.loadComuni(this.soggetto.provinciaNascita.id);
    }
  }

  loadProvince(id: number) {
    this.loaderProvince = false;
    this.subscribers.provinceByRegione = this.luoghiService
      .getProvinciaByIdRegioneNascita(id)
      .subscribe(
        (data) => {
          this.comuneModel = null;
          this.provinciaModel = data;
          this.loaderProvince = true;
        },
        (err) => {
          this.logger.error("Errore nel recupero delle province");
        }
      );
  }

  loadComuni(id: number) {
    this.loaderComuni = false;
    this.subscribers.comuniByProvince = this.luoghiService
      .getComuneByIdProvinciaNascita(id)
      .subscribe(
        (data) => {
          this.comuneModel = data;
          this.loaderComuni = true;
        },
        (err) => {
          this.logger.error("Errore nel recupero delle comuni");
        }
      );
  }

  loadNazioni() {
    this.loaderNazioni = false;
    this.subscribers.nazioni = this.luoghiService.getNazioni().subscribe(
      (data) => {
        this.nazioneNascitaModel = data;
        this.loaderNazioni = true;
      },
      (err) => {
        this.logger.error("Errore nel recupero delle nazioni");
      }
    );
  }

  isDisable(field: string) {
    let s = this.soggetto;
    if (this.modalita == "R") {
      if (field == "C") return !this.isEmpty(s.codiceFiscale);
      if (field == "N") {
        return !this.isEmpty(s.codiceFiscale);
      }
      if (field == "DN") {
        return !this.isEmpty(s.codiceFiscale);
      }
      if (field == "RN") {
        return !this.isEmpty(s.codiceFiscale) || s.nazioneNascitaEstera;
      }
      if (field == "PN") {
        return this.isDisabledComuneProvincia("P");
      }
      if (field == "CN") {
        return this.isDisabledComuneProvincia("C");
      }
      if (field == "NE") {
        return !this.isEmpty(s.codiceFiscale);
      }
      if (field == "NN") {
        return !s.nazioneNascitaEstera;
      }
      if (field == "CE") {
        return !s.nazioneNascitaEstera && this.comuneEsteroDisabled;
      }
      if (field == "SS") {
        return !this.isEmpty(s.codiceFiscale);
      }
      if (field == "CF") {
        let condRegioni = !s.regioneNascita.id;
        let condNazioni = !s.nazioneNascita.id;
        let cond =
          !this.isEmpty(s.cognome) ||
          !this.isEmpty(s.nome) ||
          !this.isEmpty(s.dataNascita) ||
          !condRegioni ||
          !condNazioni ||
          !this.isEmpty(s.sesso);
        return cond;
      }
    }
    if (this.modalita == "E") {
      if (this.soggetto.idStas != null) {
        if (field == "RN") {
          return this.isRegioneStas || s.isRegioneNascitaFromStas;
        }
        if (field == "PN") {
          return (
            this.isDisabledComuneProvincia("P") || s.isRegioneNascitaFromStas
          );
        }
        if (field == "CN") {
          return (
            this.isDisabledComuneProvincia("C") || s.isRegioneNascitaFromStas
          );
        }
        if (field == "NE") {
          return (
            this.isRegioneStas ||
            s.isRegioneNascitaFromStas ||
            s.isNazioneNascitaFromStas
          );
        }
        if (field == "NN") {
          return !s.nazioneNascitaEstera || s.isNazioneNascitaFromStas;
        }
        if (field == "CE") {
          return (
            (!s.nazioneNascitaEstera || s.isComuneNascitaEsteroFromStas) &&
            this.comuneEsteroDisabled
          );
        }
        return true;
      }
      if (this.soggetto.id) {
        return true;
      } else {
        let copy = this.copySoggetto;
        if (field == "CF") {
          if (!this.isEmpty(copy.codiceFiscale)) return true;
          else return false;
        } else {
          if (!this.isEmpty(copy.codiceFiscale)) {
            if (field == "RN") {
              return s.nazioneNascitaEstera;
            }
            if (field == "PN") {
              return this.isDisabledComuneProvincia("P");
            }
            if (field == "CN") {
              return this.isDisabledComuneProvincia("C");
            }
            if (field == "NE") {
              return false;
            }
            if (field == "NN") {
              return !s.nazioneNascitaEstera;
            }
            if (field == "CE") {
              return !s.nazioneNascitaEstera && this.comuneEsteroDisabled;
            }
            if (field == "SS") {
              return false;
            } else return false;
          } else return true;
        }
      }
    }
  }

  isEmpty(str) {
    return !str || 0 === str.length;
  }

  public isDisabledComuneProvincia(type: string) {
    if (type == "P" && this.soggetto.regioneNascita.id == null) return true;
    if (type == "C" && this.soggetto.provinciaNascita.id == null) return true;
    return false;
  }

  cittadinanzaEstera(e) {
    this.soggetto.nazioneNascitaEstera = e;
    if (e) {
      this.soggetto.regioneNascita.id = null;
      this.soggetto.provinciaNascita.id = null;
      this.soggetto.comuneNascita.id = null;
    }
    this.soggetto.nazioneNascita.id = null;
  }

  manageDatePicker() {
    if ($("#datepicker").length) {
      $("#datepicker").datetimepicker({
        format: "DD/MM/YYYY",
        locale: "it",
      });
    }
  }

  emitRicerca() {
    this.ricerca.emit(this.soggetto);
  }

  emitPulisci() {
    this.pulisci.emit(this.soggetto);
  }

  ngOnChanges() {
    this.logger.change(VerbaleSoggettoRicercaFisicaComponent.name);
    this.copySoggetto = JSON.parse(JSON.stringify(this.soggetto));
    this.manageLuoghiBySoggetto();
  }

  ngOnDestroy(): void {
    this.logger.destroy(VerbaleSoggettoRicercaFisicaComponent.name);
  }
}
