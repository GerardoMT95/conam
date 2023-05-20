/*
 * @Author: Riccardo.bova 
 * @Date: 2017-01-17 11:12:18  
 */
import { Component, OnInit, Input } from '@angular/core';

/*
* input : diameter : size del mat spinner
*/
@Component({
    selector: 'shared-spinner-form',
    templateUrl: './shared-spinner-form.component.html',
    styleUrls: ['./shared-spinner-form.component.css']
})
export class SharedSpinnerFormComponent implements OnInit {

    @Input() diameter: number;
    @Input() position: string
    size: number;
    left: string;
    right: string;

    constructor() {
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


    }

}
