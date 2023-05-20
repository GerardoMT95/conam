/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.response;

import it.csi.conam.conambl.vo.ParentVO;

/**
 * @author riccardo.bova
 * @date 21 nov 2018
 */
public class DocumentResponse extends ParentVO {

	private static final long serialVersionUID = 5502699540493590617L;

	private String file;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}
