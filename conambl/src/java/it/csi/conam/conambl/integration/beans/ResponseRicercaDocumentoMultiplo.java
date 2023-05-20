/**
 * ResponseRicercaDocumento.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.csi.conam.conambl.integration.beans;

import java.util.List;

public class ResponseRicercaDocumentoMultiplo  extends ResponseRicercaDocumento implements java.io.Serializable {


	private static final long serialVersionUID = 1L;
	
    public ResponseRicercaDocumentoMultiplo() {
    }
    
	private List<Documento> sottoDocumenti;
	
	

	public List<Documento> getSottoDocumenti() {
		return sottoDocumenti;
	}

	public void setSottoDocumenti(List<Documento> sottoDocumenti) {
		this.sottoDocumenti = sottoDocumenti;
	}

	public ResponseRicercaDocumentoMultiplo(List<Documento> sottoDocumenti) {
		super();
		this.sottoDocumenti = sottoDocumenti;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((sottoDocumenti == null) ? 0 : sottoDocumenti.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponseRicercaDocumentoMultiplo other = (ResponseRicercaDocumentoMultiplo) obj;
		if (sottoDocumenti == null) {
			if (other.sottoDocumenti != null)
				return false;
		} else if (!sottoDocumenti.equals(other.sottoDocumenti))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ResponseRicercaDocumentoMultiplo [sottoDocumenti=");
		builder.append(sottoDocumenti);
		builder.append("]");
		return builder.toString();
	}



}
