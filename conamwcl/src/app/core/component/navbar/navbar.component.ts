import { Component, OnInit, Inject, OnDestroy } from "@angular/core";
import { LoggerService } from "../../services/logger/logger.service";
import { DestroySubscribers } from "../../decorator/destroy-subscribers";
import { Router } from "@angular/router";
import { Routing } from "../../../commons/routing";
import { UserService } from "../../services/user.service";
import { ProfiloVO } from "../../../commons/vo/profilo-vo";
import { DOCUMENT } from "@angular/platform-browser";
import { ConfigService } from "../../services/config.service";
import { UseCase } from "../../../commons/class/use-case";

@Component({
  selector: "core-navbar",
  templateUrl: "./navbar.component.html",
  styleUrls: ["./navbar.component.scss"],
})
@DestroySubscribers()
export class Navbar implements OnInit, OnDestroy {
  public subscribers: any = {};
  public loaded;
  public profilo: ProfiloVO;
  isGestioneFascicoloPregressoEnabled: Array<Boolean> = [false, false, false];
  isGestioneFascicoloEnabled: Array<Boolean> = [false, false, false];
  isUtilityNormeEnabled: Array<Boolean> = [false];
  isGestioneContenziosoAmministativoEnabled: Array<Boolean> = [
    false,
    false,
    false,
    false,
    false,
    false,
  ];
  isGestioneFaseGiurisdizionaleEnabled: Array<Boolean> = [
    false,
    false,
    false,
    false,
    false,
    false,
  ];
  isRiscossioneEnabled: Array<Boolean> = [false, false, false];
  isGestionePagamentiEnabled: Array<Boolean> = [
    false,
    false,
    false,
    false,
    false,
    false,
  ];
 action: string = 'annullamento'
  constructor(
    private logger: LoggerService,
    private router: Router,
    private userService: UserService,
    @Inject(DOCUMENT) private document: any,
    private configService: ConfigService
  ) {}

  ngOnInit() {
    this.logger.init(Navbar.name);
    this.loadProfile();
  }

  private loadProfile() {
    this.userService.getProfilo();
    this.subscribers.userProfile = this.userService.profilo$.subscribe(
      (data) => {
        if (data != null) {
          this.profilo = data;
          this.loaded = true;
          this.configMenu();
        }
      },
      (err) => {
        this.logger.error("Errore recupero Servizio Profilatura");
      }
    );
  }

  public logOut() {
    this.subscribers.logOut = this.userService.logOut().subscribe(
      (data) => {
        this.logger.info(
          "logout success :: " + this.configService.getSSOLogoutURL()
        );
        this.document.location.href = this.configService.getSSOLogoutURL();
      },
      (err) => {
        this.logger.error("error logout");
      }
    );
  }

  actions: any = {
    //VERBALE
    goToDatiVerbale: () => {
      this.router.navigateByUrl(Routing.VERBALE_DATI);
    },
    goToRicercaVerbale: () => {
      this.router.navigateByUrl(Routing.VERBALE_RICERCA);
    },
    goToInserimentoPregresso: () => {
      this.router.navigateByUrl(Routing.PREGRESSO_INSERIMENTO);
    },
    goToRicercaPregresso: () => {
      this.router.navigateByUrl(Routing.PREGRESSO_RICERCA);
    },
    goToInserimentoScrittiDifensivi: () => {
      this.router.navigateByUrl(Routing.VERBALE_INSERIMENTO_SCRITTI_DIFENSIVI);
    },
    goToRicercaScrittiDifensivi: () => {
      this.router.navigateByUrl(Routing.VERBALE_RICERCA_SCRITTI_DIFENSIVI);
    },
    
    //PRONTUARIO
    goToProntuarioUtilityNorme: () => {
      this.router.navigateByUrl(Routing.PRONTUARIO_UTILITY_NORME);
    },
    //GESTIONE_CONT_AMMINISTRATIVO
    goToInserimentoControDeduzioni: () => {
      this.router.navigateByUrl(
        Routing.GESTIONE_CONT_AMMI_INSERIMENTO_CONTRODEDUZIONI_RICERCA
      );
    },
    goToEmissioneConvocazioneAudizione: () => {
      this.router.navigateByUrl(
        Routing.GESTIONE_CONT_AMMI_CONVOCAZIONE_AUDIZIONE_RICERCA
      );
    },
    goToEmissioneVerbaleAudizione: () => {
      this.router.navigateByUrl(
        Routing.GESTIONE_CONT_AMMI_VERBALE_AUDIZIONE_RICERCA
      );
    },
    goToInserimentoOrdinanza: () => {
      this.router.navigateByUrl(
        Routing.GESTIONE_CONT_AMMI_INS_ORDINANZA_RICERCA_VERBALE
      );
    },
    goToInserimentoOrdinanzaAnnullamento: () => {
      this.router.navigate([Routing.GESTIONE_CONT_AMMI_RICERCA_ORDINANZA,
        {azione: this.action}
      ]) ;
    },
    goToRicercaOrdinanza: () => {
      this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_RICERCA_ORDINANZA );
    },
    goToInserimentoRecidivita: () => {
      this.router.navigateByUrl(Routing.GESTIONE_CONT_AMMI_INSERIMENTO_RECIDIVITA_RICERCA );
    },
    //FASE GIURISDIZIONALE
    goToInserimentoComunicazioniCancelleriaFaseGiurisdizionale: () => {
      this.router.navigateByUrl(
        Routing.FASE_GIURISDIZIONALE_CANCELLERIA_RICERCA_ORDINANZA
      );
    },
    goToInserimentoIstanzaRateizzazione: () => {
      this.router.navigateByUrl(
        Routing.FASE_GIURISDIZIONALE_RATEIZZAZIONE_RICERCA_ORDINANZA
      );
    },
    goToInserimentoRicorsoFaseGiurisdizionale: () => {
      this.router.navigateByUrl(
        Routing.FASE_GIURISDIZIONALE_RICORSO_RICERCA_ORDINANZA
      );
    },
    goToCalendarioUdienzeFaseGiurisdizionale: () => {
      this.router.navigateByUrl(
        Routing.FASE_GIURISDIZIONALE_CALENDARIO_UDIENZE
      );
    },
    goToAcquisizioneFaseGiurisdizionale: () => {
      this.router.navigateByUrl(
        Routing.FASE_GIURISDIZIONALE_SENTENZA_RICERCA_ORDINANZA
      );
    },
    /*NUOVO*/
    goToInserimentoComparse: () => {
      this.router.navigateByUrl(
        Routing.FASE_GIURISDIZIONALE_INSERIMENTO_COMPARSA_RICERCA
      );
    },

    //RISCOSSIONE
    goToSollecitoPagamento: () => {
      this.router.navigateByUrl(Routing.RISCOSSIONE_SOLLECITO_RICERCA);
    },
    goToSollecitoPagamentoRate: () => {
      this.router.navigateByUrl(Routing.RISCOSSIONE_SOLLECITO_RICERCA_RATE);
    },
    goToRiscossioneCoattiva: () => {
      this.router.navigateByUrl(Routing.RISCOSSIONE_COATTIVA);
    },
    goToRiscossioneCoattivaElenco: () => {
      this.router.navigateByUrl(Routing.RISCOSSIONE_COATTIVA_ELENCO);
    },
    //PAGAMENTI
    goToCreaPianoPagamenti: () => {
      this.router.navigateByUrl(Routing.PAGAMENTI_CREA_PIANO);
    },
    goToRicercaPianoPagamenti: () => {
      this.router.navigateByUrl(Routing.PAGAMENTI_RICERCA_PIANO);
    },
    goToRiconciliaPagamentoVerbale: () => {
      this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_VERBALE_RICERCA);
    },
    goToRiconciliaPagamentoOrdinanza: () => {
      this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_ORDINANZA_RICERCA);
    },
    goRiconciliaPagamentiPianoRate: () => {
      this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_PIANO_RICERCA);
    },
    goRiconciliaPagamentiSollecito: () => {
      this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_SOLLECITO_RICERCA);
    },
    goRiconciliaPagamentiSollecitoRate: () => {
      this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_SOLLECITO_RATE_RICERCA);
    },
    goRiconciliaManualePagamentiAccontiOrdinanza: () => {
      this.router.navigateByUrl(Routing.PAGAMENTI_RICONCILIA_MANUALE_PAGAMENTI_ACCONTI_ORDINANZA);
    },
    //MOCK PER I TEMPLATE, RIMUOVERE!!!!
    goToTemplate: (id: string) => {
      this.router.navigateByUrl("template/template-container/" + id);
    },
  };

  configMenu() {
    let useCase: Array<String> = this.profilo.useCase;

    //GESTIONE FASCICOLO PREGRESSO
    if (useCase.includes(UseCase.INSERIMENTO_ALLEGATI_VERBALE_PREGRESSO))
      this.isGestioneFascicoloPregressoEnabled[1] = true;
    if (useCase.includes(UseCase.CONSULTAZIONE_VARIAZIONE_VERBALI_PREGRESSO))
      this.isGestioneFascicoloPregressoEnabled[2] = true;
    if (
      this.isGestioneFascicoloPregressoEnabled[1] ||
      this.isGestioneFascicoloPregressoEnabled[2]
    )
      this.isGestioneFascicoloPregressoEnabled[0] = true;

    //GESTIONE FASCICOLO
    if (
      useCase.includes(UseCase.CREAZIONE_VERBALE_SOGGETTI) ||
      useCase.includes(UseCase.INSERIMENTO_ALLEGATI_VERBALE)
    )
      this.isGestioneFascicoloEnabled[1] = true;
    if (useCase.includes(UseCase.CONSULTAZIONE_VARIAZIONE_VERBALI))
      this.isGestioneFascicoloEnabled[2] = true;
    if (
      this.isGestioneFascicoloEnabled[1] ||
      this.isGestioneFascicoloEnabled[2]
    )
      this.isGestioneFascicoloEnabled[0] = true;

    //PRONTUARIO
    if (useCase.includes(UseCase.INSERIMENTO_PRONTUARIO))
      this.isUtilityNormeEnabled[0] = true;

    //GESTIONE CONTENZIOSO AMMINISTRATIVO
    if (useCase.includes(UseCase.INSERIMENTO_CONTRODEDUZIONI))
      this.isGestioneContenziosoAmministativoEnabled[1] = true;
    if (useCase.includes(UseCase.EMISSIONE_VERBALE_AUDIZIONE))
      this.isGestioneContenziosoAmministativoEnabled[2] = true;
    if (useCase.includes(UseCase.EMISSIONE_ORDINANZA_ARCHIVIAZIONE))
      this.isGestioneContenziosoAmministativoEnabled[3] = true;
    if (useCase.includes(UseCase.IRROGAZIONE_ORDINANZA_INGIUNZIONE))
      this.isGestioneContenziosoAmministativoEnabled[4] = true;
    if (useCase.includes(UseCase.EMISSIONE_CONVOCAZIONE_AUDIZIONE))
      this.isGestioneContenziosoAmministativoEnabled[5] = true;
    if (
      this.isGestioneContenziosoAmministativoEnabled[1] ||
      this.isGestioneContenziosoAmministativoEnabled[2] ||
      this.isGestioneContenziosoAmministativoEnabled[3] ||
      this.isGestioneContenziosoAmministativoEnabled[4] ||
      this.isGestioneContenziosoAmministativoEnabled[5]
    )
      this.isGestioneContenziosoAmministativoEnabled[0] = true;

    //GESTIONE FASE GIURISDIZIONALE
    if (useCase.includes(UseCase.INSERIMENTO_COMUNICAZIONI_CANCELLERIA))
      this.isGestioneFaseGiurisdizionaleEnabled[1] = true;
    if (useCase.includes(UseCase.INSERIMENTO_INSTANZA_RATEIZZAZIONE))
      this.isGestioneFaseGiurisdizionaleEnabled[2] = true;
    if (useCase.includes(UseCase.INSERIMENTO_RICORSO))
      this.isGestioneFaseGiurisdizionaleEnabled[3] = true;
    if (useCase.includes(UseCase.GESTIONE_CALENDARIO_UDIENZE))
      this.isGestioneFaseGiurisdizionaleEnabled[4] = true;
    if (useCase.includes(UseCase.ACQUISIZIONE_SENTENZA))
      this.isGestioneFaseGiurisdizionaleEnabled[5] = true;
    if (useCase.includes(UseCase.INSERIMENTO_COMPARSA))
      this.isGestioneFaseGiurisdizionaleEnabled[6] = true;
    if (
      this.isGestioneFaseGiurisdizionaleEnabled[1] ||
      this.isGestioneFaseGiurisdizionaleEnabled[2] ||
      this.isGestioneFaseGiurisdizionaleEnabled[3] ||
      this.isGestioneFaseGiurisdizionaleEnabled[4] ||
      this.isGestioneFaseGiurisdizionaleEnabled[5] ||
      this.isGestioneFaseGiurisdizionaleEnabled[6]
    )
      this.isGestioneFaseGiurisdizionaleEnabled[0] = true;

    //RISCOSSIONE
    if (useCase.includes(UseCase.SOLLECITO_PAGAMENTO))
      this.isRiscossioneEnabled[1] = true;
    if (useCase.includes(UseCase.RISCOSSIONE_COATTIVA))
      this.isRiscossioneEnabled[2] = true;
    if (this.isRiscossioneEnabled[1] || this.isRiscossioneEnabled[2])
      this.isRiscossioneEnabled[0] = true;

    //GESTIONE PAGAMENTI
    if (useCase.includes(UseCase.INSERIMENTO_PIANO_RATEIZZAZIONE))
      this.isGestionePagamentiEnabled[1] = true;
    if (useCase.includes(UseCase.RICERCA_PIANO_RATEIZZAZIONE))
      this.isGestionePagamentiEnabled[2] = true;
    if (useCase.includes(UseCase.RICONCILIAZIONE_MANUALE_VERBALE))
      this.isGestionePagamentiEnabled[3] = true;
    if (useCase.includes(UseCase.RICONCILIAZIONE_MANUALE_ORDINANZA))
      this.isGestionePagamentiEnabled[4] = true;
    if (useCase.includes(UseCase.ACQUISIZIONE_NOTIFICHE_PAGAMENTO))
      this.isGestionePagamentiEnabled[5] = true;
    if (useCase.includes(UseCase.ACQUISIZIONE_NOTIFICHE_PAGAMENTO))
      this.isGestionePagamentiEnabled[6] = true;
    if (useCase.includes(UseCase.ACQUISIZIONE_NOTIFICHE_PAGAMENTO_RATE))
      this.isGestionePagamentiEnabled[7] = true;
    if (
      this.isGestionePagamentiEnabled[1] ||
      this.isGestionePagamentiEnabled[2] ||
      this.isGestionePagamentiEnabled[3] ||
      this.isGestionePagamentiEnabled[4] ||
      this.isGestionePagamentiEnabled[5] ||
      this.isGestionePagamentiEnabled[6] ||
      this.isGestionePagamentiEnabled[7]
    )
      this.isGestionePagamentiEnabled[0] = true;
  }

  ngOnDestroy(): void {
    this.logger.destroy(Navbar.name);
  }
}
