import { Injectable, OnDestroy } from "@angular/core";
import { LoggerService } from "../../core/services/logger/logger.service";

@Injectable()
export class NumberUtilsSharedService implements OnDestroy {
  constructor(private logger: LoggerService) {
    this.logger.createService(NumberUtilsSharedService.name);
  }

  numberValid(code: number): boolean {
    let isFirefox = navigator.userAgent.toLowerCase().indexOf("firefox") > -1;
    let isIEOrEdge = /msie\s|trident\/|edge\//i.test(
      window.navigator.userAgent
    );

    if (isFirefox || isIEOrEdge) {
      //numeri
      if (code >= 48 && code <= 57) return true;
      //block numeric
      if ((code >= 96 && code <= 105) || code == 110) return true;
      //punto virgola backspace canc
      if (code == 8 || code == 46) return true;
      else return false;
    }
    //xchrome
    else {
      return code != 69 && code != 107 && code != 109;
    }
  }
 
  ngOnDestroy(): void {
    this.logger.destroyService(NumberUtilsSharedService.name);
  }
}
