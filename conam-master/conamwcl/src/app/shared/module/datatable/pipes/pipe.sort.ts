import { Pipe } from "@angular/core";
import { UtilsService } from "../services/utilities";

@Pipe({
    name: "sort",
    pure: false,
})
export class ArraySortPipe {
    transform(array: Array<any>, _column: string, order: number): Array<string> {
        if (order === 0)
            return array;
        array.sort((a: any, b: any) => {
            if (this.utils.byString(a,_column) < this.utils.byString(b,_column)) {
                return -1 * order;
            } else if (this.utils.byString(a,_column) > this.utils.byString(b,_column)) {
                return 1 * order;
            } else {
                return 0;
            }
        });
        return array;
    }

    constructor(private utils:UtilsService){ }
}