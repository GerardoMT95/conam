import { Injectable } from '@angular/core';

/**
 * Servizio di configurazione
 */
@Injectable()
export class ConfigService {

    /**
     * server del backend nel formato http://server:port
     */
    getBEServer(): string {
        return ENV_PROPERTIES.beServer;
    }
    /**
     * Url di logout da SSO
     */
    getSSOLogoutURL(): string {
        return ENV_PROPERTIES.shibbolethSSOLogoutURL;
    }

    getInfoLink(): string {
        return ENV_PROPERTIES.infoLink;
    }

    getTimeout(): number {
        return ENV_PROPERTIES.timeout;
    }

    getLogLevel(): string {
        return ENV_PROPERTIES.logLevel;
    }

    getTimeoutMessagge(): number {
        return 60;
    }
}