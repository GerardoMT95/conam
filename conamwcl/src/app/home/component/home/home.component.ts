import { Component, OnInit, ViewChild, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { LoggerService } from '../../../core/services/logger/logger.service';
import { DestroySubscribers } from '../../../core/decorator/destroy-subscribers';
import { UserService } from '../../../core/services/user.service';
import { ProfiloVO } from '../../../commons/vo/profilo-vo';



@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
@DestroySubscribers()
export class HomeComponent implements OnInit, OnDestroy {


  public subscribers: any = {};
  public profilo: ProfiloVO;
  public loaded: boolean;

  constructor(
    private logger: LoggerService,
    private router: Router,
    private userService: UserService,
  ) {
  }

  ngOnInit() {
    this.logger.init(HomeComponent.name);
    this.loadProfile();
  }

  private loadProfile() {
    this.subscribers.userProfile = this.userService.profilo$.subscribe(data => {
      if (data != null) {
        this.profilo = data;
        this.loaded = true;
      }
    },
      (err) => {
        this.logger.error("Errore recupero Servizio Profilatura");
      }
    )
  }

  ngOnDestroy(): void {
    this.logger.destroy(HomeComponent.name);
  }

}