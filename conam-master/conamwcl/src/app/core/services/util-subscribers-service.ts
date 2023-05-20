import { Injectable } from "@angular/core";
import { LoggerService } from "./logger/logger.service";
import { ifError } from "assert";





@Injectable()
export class UtilSubscribersService {

    public unsbscribeByName(subscribers: any, key: string) {
        for (let subscriberKey in subscribers) {
            if (subscriberKey == key) {
                let subscriber = subscribers[subscriberKey];
                subscriber.unsubscribe();
                delete subscribers[key];
                this.logger.info(UtilSubscribersService.name + " delete key with name : " + key);
                break;
            }
        }

    }

    constructor(private logger: LoggerService) {
    }
}