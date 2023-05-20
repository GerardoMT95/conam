import { Injectable } from '@angular/core';
import { Logger } from './logger.service';
import { ConfigService } from '../config.service';


const noop = (): any => undefined;

@Injectable()
export class ConsoleLoggerService implements Logger {

    private level: string;

    get info() {
        if (this.level == "info") {
            return console.info.bind(console);
        } else {
            return noop;
        }
    }

    get warn() {
        if (this.level == "warn" || this.level == "info") {
            return console.warn.bind(console);
        } else {
            return noop;
        }
    }

    get error() {
        if (this.level == "error" || this.level == "info" || this.level == "warn") {
            return console.error.bind(console);
        } else {
            return noop;
        }
    }

    invokeConsoleMethod(type: string, args?: any): void {
        const logFn: Function = (console)[type] || console.log || noop;
        logFn.apply(console, [args]);
    }

    init(name: string): void {
        this.invokeConsoleMethod('info', name + " ---> INIT");
    }

    destroy(name: string): void {
        this.invokeConsoleMethod('info', name + " ---> DESTROY");
    }

    change(name: string): void {
        this.invokeConsoleMethod('info', name + " ---> CHANGE");
    }

    createService(name: string): void {
        this.invokeConsoleMethod('info', name + " ---> SERVICE CREATE");
    };

    destroyService(name: string): void {
        this.invokeConsoleMethod('info', name + " ---> SERVICE DESTROY");
    };
    constructor(private configService: ConfigService) {
        this.level = this.configService.getLogLevel();
    }
}