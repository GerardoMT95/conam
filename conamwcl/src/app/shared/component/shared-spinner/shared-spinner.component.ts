import { Component, OnInit, Input } from '@angular/core';
import { LoggerService } from '../../../core/services/logger/logger.service';

/*
* input : diameter : size del mat spinner
*/
@Component({
    selector: 'shared-spinner',
    templateUrl: './shared-spinner.component.html',
    styleUrls: ['./shared-spinner.component.css']
})
export class SharedSpinnerComponent implements OnInit {

    @Input() diameter: number;
    @Input() position: string
    size: number;
    left: string;
    right: string;

    ie9: Boolean = false;

    constructor(private logger: LoggerService) {
        this.size = 150;
        this.left = "auto";
        this.right = "auto";
    }


    ngOnInit() {
        if (this.diameter)
            this.size = this.diameter;

        if (this.position === "right") {
            this.right = "0px";
            this.left = "auto";
        }
        if (this.position === "left") {
            this.right = "auto";
            this.left = "0px";
        }

        this.ie9 = this.detectIE9();


    }

    detectIE9() {
        let ua = window.navigator.userAgent;

        let msie = ua.indexOf('MSIE ');
        if (msie > 0) {
            // IE 10 or older => return version number
            let version: number = parseInt(ua.substring(msie + 5, ua.indexOf('.', msie)), 10);
            if (version == 9)
                return true;
            else return false;
        }

        // other browser
        return false;
    }

}
