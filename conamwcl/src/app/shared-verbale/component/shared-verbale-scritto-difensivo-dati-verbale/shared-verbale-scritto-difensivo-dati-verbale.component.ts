import {
  Component,
  OnInit,
  OnDestroy,
  Input,
  Output,
  EventEmitter,
  ViewChild,
} from "@angular/core";
import { LoggerService } from "../../../core/services/logger/logger.service";
import { SharedVerbaleService } from "../../service/shared-verbale.service";
import { TableSoggettiVerbale } from "../../../commons/table/table-soggetti-verbale";
import { SharedVerbaleConfigService } from "../../service/shared-verbale-config.service";
import { ScrittoDifensivoVO } from "../../../commons/vo/verbale/scritto-difensivo-vo";
import { AmbitoVO, ArticoloVO, CommaVO, EnteVO, LetteraVO, NormaVO, SelectVO } from "../../../commons/vo/select-vo";
import { NgForm } from "@angular/forms";
import { UserService } from "../../../core/services/user.service";
import { RifNormativiService } from "../../../verbale/services/rif-normativi.service";
import { RiferimentiNormativiVO } from "../../../commons/vo/verbale/riferimenti-normativi-vo";

@Component({
  selector: "shared-verbale-scritto-difensivo-dati-verbale",
  templateUrl: "./shared-verbale-scritto-difensivo-dati-verbale.component.html",
})
export class SharedVerbaleScrittoDifensivoDatiVerbaleComponent
  implements OnInit, OnDestroy {
  public subscribers: any = {};

  @Input()
  scrittoDifensivo: ScrittoDifensivoVO;

  @Input()
  isEdit: boolean = true;

  @Output()
  formValid: EventEmitter<boolean> = new EventEmitter<boolean>();
  @ViewChild('scrittoDifensivoDatiVerbale')
  private scrittoDifensivoDatiVerbale: NgForm;
  public enteAccertatoreModel: Array<EnteVO>;
  public enteModel: Array<EnteVO>;
  public ambitoModel: Array<AmbitoVO> = [];
  public normaModel: Array<NormaVO> = [];
  public articoloModel: Array<ArticoloVO> = [];
  public commaModel: Array<CommaVO> = [];
  public letteraModel: Array<LetteraVO> = [];
  public singoloEnte: boolean;
  public loadedAmbito: boolean;
  public loadedNorma: boolean;
  public loadedArticolo: boolean;
  public loadedComma: boolean;
  public loadedLettera: boolean;


  constructor(
    private logger: LoggerService,
    private userService: UserService,
    private rifNormativiService: RifNormativiService,
  ) {

  }

  byId(o1: SelectVO, o2: SelectVO) {
    return o1 && o2 ? o1.id === o2.id : o1 === o2;
  }

  ngOnInit(): void {
    this.logger.init(SharedVerbaleScrittoDifensivoDatiVerbaleComponent.name);
    this.subscribers.userProfile = this.userService.profilo$.subscribe(
      (data) => {
        if (data != null) {
          this.loadEnti(data.entiAccertatore, data.entiLegge);
        }
      }
    );
    //VALIDAZIONE FORM
    this.subscribers.sog = this.scrittoDifensivoDatiVerbale.valueChanges.subscribe(data => {
      if (this.scrittoDifensivoDatiVerbale.status == "DISABLED") this.formValid.next(true);
      else
        this.formValid.next(this.scrittoDifensivoDatiVerbale.valid);
    });
  }

  //chiamata solo nel modifica
  loadRiferimentiNormativiByIdEnte(idEnte: number) {
    this.loadAmbitiByIdEnte(idEnte);

    if(this.scrittoDifensivo.ambito && this.scrittoDifensivo.ambito.id){
      this.scrittoDifensivo.riferimentoNormativo.ambito = {...this.scrittoDifensivo.ambito};
    }

    if(this.scrittoDifensivo.riferimentoNormativo){
      if (this.scrittoDifensivo.riferimentoNormativo.ambito && this.scrittoDifensivo.riferimentoNormativo.ambito.id) {
        this.loadNormeByIdAmbitoAndIdEnte(
          this.scrittoDifensivo.riferimentoNormativo.ambito.id,
          idEnte
        );
      }
      if (this.scrittoDifensivo.riferimentoNormativo.norma && this.scrittoDifensivo.riferimentoNormativo.norma.id) {
        this.loadArticoliByIdNormaAndIdEnte(
          this.scrittoDifensivo.riferimentoNormativo.norma.id,
          idEnte
        );
      }
      if (this.scrittoDifensivo.riferimentoNormativo.articolo && this.scrittoDifensivo.riferimentoNormativo.articolo.id) {
        this.loadCommaByIdArticolo(
          this.scrittoDifensivo.riferimentoNormativo.articolo.id
        );
      }
      if (this.scrittoDifensivo.riferimentoNormativo.comma && this.scrittoDifensivo.riferimentoNormativo.comma.id) {
        this.loadLettereByIdComma(
          this.scrittoDifensivo.riferimentoNormativo.comma.id
        );
      }
    }
  }

  loadEnti(listEnteAccertatore: Array<EnteVO>, listEnteLegge: Array<EnteVO>) {
    this.singoloEnte = false;
    this.enteAccertatoreModel = listEnteAccertatore;
    this.enteModel = listEnteLegge;
    if (this.enteModel.length == 1)
      this.scrittoDifensivo.enteRiferimentiNormativi = this.enteModel[0];

    if (listEnteLegge.length == 1) {
      this.singoloEnte = true;
    } else {
      this.loadedAmbito = true;
    }
    this.loadedNorma = true;
    this.loadedArticolo = true;
    this.loadedComma = true;
    this.loadedLettera = true;

    this.nuovoRiferimentoNormativo();
  }

  nuovoRiferimentoNormativo() {
    let newElement = new RiferimentiNormativiVO();
    Object.defineProperty(newElement, "__id", {
      value: 0,
      enumerable: false,
    });
    let newIndex = 0;
    this.loadedArticolo = true;
    this.loadedComma = true;
    this.loadedLettera = true;
    this.loadedNorma = true;
    if (this.scrittoDifensivo.enteRiferimentiNormativi){
      this.loadAmbitiByIdEnte(
        this.scrittoDifensivo.enteRiferimentiNormativi.id
      );
      // popolo i select per il caso edit
      if(this.scrittoDifensivo.enteRiferimentiNormativi.id){
        this.loadRiferimentiNormativiByIdEnte(this.scrittoDifensivo.enteRiferimentiNormativi.id);
      }
    }
  }
  loadAmbitiByIdEnte(idEnte: number) {
    this.loadedAmbito = false;
    this.subscribers.ambitiByIdEnte = this.rifNormativiService
      .getAmbitiByIdEnte(idEnte, true)
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

  loadNormeByIdAmbitoAndIdEnte(idAmbito: number, idEnte) {
    this.loadedNorma = false;
    this.subscribers.normeByIdAmbito = this.rifNormativiService
      .getNormeByIdAmbitoAndIdEnte(idAmbito, idEnte, true)
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

  loadArticoliByIdNormaAndIdEnte(idNorma: number, idEnte) {
    this.loadedArticolo = false;
    this.subscribers.articoliByIdNorma = this.rifNormativiService
      .getArticoliByIdNormaAndIdEnte(idNorma, idEnte, true)
      .subscribe(
        (data) => {
          this.articoloModel = data;
          this.loadedArticolo = true;
        },
        (err) => {
          this.logger.error("Errore nel recupero degli articoli");
        }
      );
  }

  loadCommaByIdArticolo(idArticolo: number) {
    this.loadedComma = false;
    this.subscribers.commaByIdArticolo = this.rifNormativiService
      .getCommaByIdArticolo(idArticolo, true)
      .subscribe(
        (data) => {
          this.commaModel = data;
          this.loadedComma = true;
        },
        (err) => {
          this.logger.error("Errore nel recupero dei commi");
        }
      );
  }

  loadLettereByIdComma(idComma: number) {
    this.loadedLettera = false;
    this.subscribers.lettereByIdComma = this.rifNormativiService
      .getLetteraByIdComma(idComma, true)
      .subscribe(
        (data) => {
          this.letteraModel = data;
          this.loadedLettera = true;
        },
        (err) => {
          this.logger.error("Errore nel recupero delle lettere");
        }
      );
  }


  changeEnte(idEnte: number) {
    this.scrittoDifensivo.riferimentoNormativo = new RiferimentiNormativiVO();
    this.loadAmbitiByIdEnte(idEnte);
  }

  changeAmbito() {
    let idAmbito = this.scrittoDifensivo.riferimentoNormativo.ambito.id;
    this.scrittoDifensivo.riferimentoNormativo.norma = null;
    this.scrittoDifensivo.riferimentoNormativo.articolo = null;
    this.scrittoDifensivo.riferimentoNormativo.comma = null;
    this.scrittoDifensivo.riferimentoNormativo.lettera = null;
    this.commaModel = null;
    this.letteraModel = null;
    this.loadNormeByIdAmbitoAndIdEnte(
      idAmbito,
      this.scrittoDifensivo.enteRiferimentiNormativi.id
    );
  }

  changeNormativa() {
    let idNorma = this.scrittoDifensivo.riferimentoNormativo.norma.id;
    this.scrittoDifensivo.riferimentoNormativo.articolo = null;
    this.scrittoDifensivo.riferimentoNormativo.comma = null;
    this.scrittoDifensivo.riferimentoNormativo.lettera = null;
    this.commaModel = null;
    this.letteraModel = null;
    this.loadArticoliByIdNormaAndIdEnte(
      idNorma,
      this.scrittoDifensivo.enteRiferimentiNormativi.id
    );
  }

  changeArticolo() {
    let idArticolo = this.scrittoDifensivo.riferimentoNormativo.articolo.id;
    this.scrittoDifensivo.riferimentoNormativo.comma = null;
    this.scrittoDifensivo.riferimentoNormativo.lettera = null;
    this.letteraModel = null;
    this.loadCommaByIdArticolo(idArticolo);
  }

  changeComma() {
    let idComma = this.scrittoDifensivo.riferimentoNormativo.comma.id;
    this.scrittoDifensivo.riferimentoNormativo.lettera = null;
    this.loadLettereByIdComma(idComma);
  }


  // solo il primo può essere disabled, nel caso in cui ci siano più enti e nessuno è stato ancora scelto
  isDisabledAmbito(): boolean {
    return (
      this.enteModel.length > 1 &&
      !this.scrittoDifensivo.enteRiferimentiNormativi
    );
  }

  isDisabledNorma(): boolean {
    return (
      !this.scrittoDifensivo.riferimentoNormativo.ambito ||
      (this.scrittoDifensivo.riferimentoNormativo.ambito &&
        !this.scrittoDifensivo.riferimentoNormativo.ambito.id)
    );
  }

  isDisabledArticolo(): boolean {
    return (
      !this.scrittoDifensivo.riferimentoNormativo.norma ||
      (this.scrittoDifensivo.riferimentoNormativo.norma &&
        !this.scrittoDifensivo.riferimentoNormativo.norma.id)
    );
  }

  isDisabledComma(): boolean {
    return (
      !this.scrittoDifensivo.riferimentoNormativo.articolo ||
      (this.scrittoDifensivo.riferimentoNormativo.articolo &&
        !this.scrittoDifensivo.riferimentoNormativo.articolo.id)
    );
  }

  isDisabledLettera(): boolean {
    return (
      !this.scrittoDifensivo.riferimentoNormativo.comma ||
      (this.scrittoDifensivo.riferimentoNormativo.comma &&
        !this.scrittoDifensivo.riferimentoNormativo.comma.id)
    );
  }

  ngOnDestroy(): void {
    this.logger.destroy(SharedVerbaleScrittoDifensivoDatiVerbaleComponent.name);
  }
}
