/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.scheduled;

/**
 * @author riccardo.bova
 * @date 14 mar 2019
 */

public interface NodeService {

	void pingNode();

	void checkLeaderShip();

	boolean isLeader();

}
