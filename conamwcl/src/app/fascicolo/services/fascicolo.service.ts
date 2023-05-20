import { Injectable } from "@angular/core";
import { MessageVO } from "../../commons/vo/messageVO";
import { TipoAllegatoVO } from "../../commons/vo/select-vo";
import { DocumentoProtocollatoVO } from "../../commons/vo/verbale/documento-protocollato-vo";

export interface NumberDocument {
  value: number;
  readonly: boolean;
}

@Injectable()
export class FascicoloService {
  private _ref: string;
  private _successPage: string;
  private _backPage: string;
  private _numberDocument: NumberDocument;
  private _tipoAllegatoModel: Array<TipoAllegatoVO>;
  private _searchFormRicercaProtocol: string;
  private _dataRicercaProtocollo: Array<DocumentoProtocollatoVO>;
  private _idOrdinanzaVerbaleSoggetto: Array<number> = [];
  private _categoriesDuplicated: Array<number> = [];
  private _message: MessageVO;
  constructor() {}

  set ref(ref: string) {
    this._ref = ref;
  }

  get ref(): string {
    return this._ref;
  }

  set message(message: MessageVO) {
    this._message = message;
  }

  get message(): MessageVO {
    return this._message;
  }

  set successPage(success: string) {
    this._successPage = success;
  }

  get successPage(): string {
    return this._successPage;
  }

  set backPage(back: string) {
    this._backPage = back;
  }

  get backPage(): string {
    return this._backPage ? this._backPage : this._ref;
  }

  set searchFormRicercaProtocol(searchFormRicercaProtocol: string) {
    this._searchFormRicercaProtocol = searchFormRicercaProtocol;
  }

  get searchFormRicercaProtocol(): string {
    return this._searchFormRicercaProtocol;
  }

  setNumberDocument(): NumberDocument {
    // TODO in base al ref devo gestire i diversi casi
    this._numberDocument = {
      value: 1,
      readonly: false,
    };
    // controdeduzioni
    if (
      this._ref &&
      this._ref.indexOf("controdeduzioni-verbale-allegato") > -1
    ) {
      this._numberDocument = {
        value: 1,
        readonly: true,
      };
    }
    // istanza di rateizzazione
    if (
      this._ref &&
      this._ref.indexOf("rateizzazione-allegato-ordinanza") > -1
    ) {
      this._numberDocument = {
        value: 1,
        readonly: true,
      };
    }
    // opposizione giuridiszionale
    if (this._ref && this._ref.indexOf("ricorso-allegato-ordinanza") > -1) {
      this._numberDocument = {
        value: 1,
        readonly: true,
      };
    }
    // disposizioni del giudice
    if (this._ref && this._ref.indexOf("sentenza-allegato-ordinanza") > -1) {
      // salvaAllegatoOrdinanzaSoggetto punto 4 idOrdinanzaVerbaleSoggetto
      this._numberDocument = {
        value: 1,
        readonly: true,
      };
    }
    return this._numberDocument;
  }

  getTypeLoadTipoAllegato(): number {
    let type = 1;
    // controdeduzioni
    if (
      this._ref &&
      this._ref.indexOf("controdeduzioni-verbale-allegato") > -1
    ) {
      type = 2;
    }
    // istanza di rateizzazione
    if (
      this._ref &&
      this._ref.indexOf("rateizzazione-allegato-ordinanza") > -1
    ) {
      // salvaAllegatoOrdinanzaSoggetto punto 4 idOrdinanzaVerbaleSoggetto
      type = 5;
    }
    // opposizione giuridiszionale
    if (this._ref && this._ref.indexOf("ricorso-allegato-ordinanza") > -1) {
      // salvaAllegatoOrdinanza punto 5 idOrdinanza
      type = 4;
    }
    // disposizioni del giudice
    if (this._ref && this._ref.indexOf("sentenza-allegato-ordinanza") > -1) {
      // salvaAllegatoOrdinanzaSoggetto punto 4 idOrdinanzaVerbaleSoggetto
      type = 3;
    }
    // pregresso
    if (this._ref && this._ref.indexOf("pregresso/allegato") > -1) {
      // salvaAllegatoOrdinanzaSoggetto punto 4 idOrdinanzaVerbaleSoggetto
      type = 6;
    }

    // pregresso
    if (this._ref && this._ref.indexOf("pregresso/riepilogo-ordinanze") > -1) {
      // salvaAllegatoOrdinanzaSoggetto punto 4 idOrdinanzaVerbaleSoggetto
      type = 7;
    }

    return type;
  }

  getTypeRicerca(): number {
    let type = 1;
    // pregresso
    if (this._ref && this._ref.indexOf("pregresso/allegato") > -1) {
      // salvaAllegatoOrdinanzaSoggetto punto 4 idOrdinanzaVerbaleSoggetto
      type = 2;
    }
    return type;
  }

  getTypeSaveRequest(): number {
    let type = 1;
    // controdeduzioni
    if (
      this._ref &&
      this._ref.indexOf("controdeduzioni-verbale-allegato") > -1
    ) {
      type = 1;
      // action caricato
    }
    // istanza di rateizzazione
    if (
      this._ref &&
      this._ref.indexOf("rateizzazione-allegato-ordinanza") > -1
    ) {
      // salvaAllegatoOrdinanzaSoggetto punto 4 idOrdinanzaVerbaleSoggetto
      type = 2;
      // action caricato
    }
    // opposizione giuridiszionale
    if (this._ref && this._ref.indexOf("ricorso-allegato-ordinanza") > -1) {
      // salvaAllegatoOrdinanza punto 5 idOrdinanza
      type = 3;
      // action caricato
    }
    // disposizioni del giudice
    if (this._ref && this._ref.indexOf("sentenza-allegato-ordinanza") > -1) {
      // salvaAllegatoOrdinanzaSoggetto punto 4 idOrdinanzaVerbaleSoggetto
      type = 2;
      // action caricato
    }
    // pregresso
    if (this._ref && this._ref.indexOf("pregresso/allegato") > -1) {
      // salvaAllegatoOrdinanzaSoggetto punto 4 idOrdinanzaVerbaleSoggetto
      type = 4;
    }

    if (this._ref && this._ref.indexOf("pregresso/riepilogo-ordinanze") > -1) {
      type = 5;
    }
    return type;
  }

  getSaveActionSuccessMessage(): string {
    let action = "caricato";
    // controdeduzioni
    if (
      this._ref &&
      this._ref.indexOf("controdeduzioni-verbale-allegato") > -1
    ) {
      action = "caricato";
    }
    // istanza di rateizzazione
    if (
      this._ref &&
      this._ref.indexOf("rateizzazione-allegato-ordinanza") > -1
    ) {
      // salvaAllegatoOrdinanzaSoggetto punto 4 idOrdinanzaVerbaleSoggetto
      action = "caricato";
    }
    // opposizione giuridiszionale
    if (this._ref && this._ref.indexOf("ricorso-allegato-ordinanza") > -1) {
      // salvaAllegatoOrdinanza punto 5 idOrdinanza
      action = "allegato";
    }
    // disposizioni del giudice
    if (this._ref && this._ref.indexOf("sentenza-allegato-ordinanza") > -1) {
      // salvaAllegatoOrdinanzaSoggetto punto 4 idOrdinanzaVerbaleSoggetto
      action = "caricato";
    }
    return action;
  }

  set tipoAllegatoModel(tipoAllegatoModel: Array<TipoAllegatoVO>) {
    this._tipoAllegatoModel = tipoAllegatoModel;
  }

  get tipoAllegatoModel(): Array<TipoAllegatoVO> {
    return this._tipoAllegatoModel;
  }

  set dataRicercaProtocollo(
    dataRicercaProtocollo: Array<DocumentoProtocollatoVO>
  ) {
    this._dataRicercaProtocollo = dataRicercaProtocollo;
  }

  get dataRicercaProtocollo(): Array<DocumentoProtocollatoVO> {
    return this._dataRicercaProtocollo;
  }

  set idOrdinanzaVerbaleSoggetto(idOrdinanzaVerbaleSoggetto: Array<number>) {
    this._idOrdinanzaVerbaleSoggetto = idOrdinanzaVerbaleSoggetto;
  }

  get idOrdinanzaVerbaleSoggetto(): Array<number> {
    return this._idOrdinanzaVerbaleSoggetto;
  }

  set categoriesDuplicated(categoriesNotDuplicated: Array<number>) {
    this._categoriesDuplicated = categoriesNotDuplicated;
  }

  get categoriesDuplicated(): Array<number> {
    return this._categoriesDuplicated;
  }
}
