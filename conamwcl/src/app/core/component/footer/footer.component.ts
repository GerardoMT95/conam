import { Component, OnInit, } from '@angular/core';
import { ConfigService } from '../../services/config.service';





@Component({
  selector: 'footer-core',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.scss']
})
export class FooterComponent implements OnInit {

  private link: string;

  constructor(private configService: ConfigService) { }

  ngOnInit() {
    this.link = this.configService.getInfoLink();
  }
}