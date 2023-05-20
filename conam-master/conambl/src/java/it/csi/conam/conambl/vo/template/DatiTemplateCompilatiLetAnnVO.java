/*******************************************************************************
 * SPDX-License-Identifier: EUPL-1.2
 * Copyright Regione Piemonte - 2020
 ******************************************************************************/
package it.csi.conam.conambl.vo.template;

import it.csi.conam.conambl.vo.ParentVO;

// 20210312_LC
public class DatiTemplateCompilatiLetAnnVO extends ParentVO {

	private static final long serialVersionUID = -4306637286526021245L;

	private String direzioneLettera;
	private String settoreLettera;
	private String mailLettera;

	private String dataLettera;	
	
	private String salutiLettera;
	
	private String dirigenteLettera;
	private String bloccoFirmaOmessa;
	private String inizialiLettera;
	
	private String sedeEnteRiga1;
	private String sedeEnteRiga2;
	private String sedeEnteRiga3;
	
	// 20210401_LC a livello superiore (DatiCOmpilatiVO)
//	private String indirizzoOrganoAccertatoreRiga1;
//	private String indirizzoOrganoAccertatoreRiga2;
//	private String indirizzoOrganoAccertatoreRiga3;	
//	private String testoLibero;	
//	private String oggettoLettera;	
//	private String corpoLettera;

	
	public String getDataLettera() {
		return dataLettera;
	}
	public void setDataLettera(String dataLettera) {
		this.dataLettera = dataLettera;
	}


	public String getDirigenteLettera() {
		return dirigenteLettera;
	}
	public void setDirigenteLettera(String dirigenteLettera) {
		this.dirigenteLettera = dirigenteLettera;
	}

	public String getSedeEnteRiga1() {
		return sedeEnteRiga1;
	}
	public void setSedeEnteRiga1(String sedeEnteRiga1) {
		this.sedeEnteRiga1 = sedeEnteRiga1;
	}
	public String getSedeEnteRiga2() {
		return sedeEnteRiga2;
	}
	public void setSedeEnteRiga2(String sedeEnteRiga2) {
		this.sedeEnteRiga2 = sedeEnteRiga2;
	}
	public String getSedeEnteRiga3() {
		return sedeEnteRiga3;
	}
	public void setSedeEnteRiga3(String sedeEnteRiga3) {
		this.sedeEnteRiga3 = sedeEnteRiga3;
	}
	public String getBloccoFirmaOmessa() {
		return bloccoFirmaOmessa;
	}
	public void setBloccoFirmaOmessa(String bloccoFirmaOmessa) {
		this.bloccoFirmaOmessa = bloccoFirmaOmessa;
	}
	public String getDirezioneLettera() {
		return direzioneLettera;
	}
	public void setDirezioneLettera(String direzioneLettera) {
		this.direzioneLettera = direzioneLettera;
	}
	public String getSettoreLettera() {
		return settoreLettera;
	}
	public void setSettoreLettera(String settoreLettera) {
		this.settoreLettera = settoreLettera;
	}
	public String getMailLettera() {
		return mailLettera;
	}
	public void setMailLettera(String mailLettera) {
		this.mailLettera = mailLettera;
	}
	public String getInizialiLettera() {
		return inizialiLettera;
	}
	public void setInizialiLettera(String inizialiLettera) {
		this.inizialiLettera = inizialiLettera;
	}

	public String getSalutiLettera() {
		return salutiLettera;
	}
	public void setSalutiLettera(String salutiLettera) {
		this.salutiLettera = salutiLettera;
	}

	
	

}
