package com.pccw.wq.schema.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class RemarkDTO implements Serializable, Comparable<RemarkDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5801293483605125543L;

	private String remarkSequence;
	private String remarkContent;
	private String remarkDate;
	private String remarkNatureId;
	private String createBy;

	public String getRemarkSequence() {
		return remarkSequence;
	}

	public void setRemarkSequence(String remarkSequence) {
		this.remarkSequence = remarkSequence;
	}

	public String getRemarkContent() {
		return remarkContent;
	}

	public String getRemarkContentHtml() {
		return escapeHTML(remarkContent);
	}
	
	public void setRemarkContent(String remarkContent) {
		this.remarkContent = remarkContent;
	}

	@Override
	public int compareTo(RemarkDTO pRemark) {
		return StringUtils.leftPad(this.remarkSequence, 10).compareTo(
				StringUtils.leftPad(pRemark.getRemarkSequence(), 10));
	}

	public String getRemarkDate() {
		return remarkDate;
	}

	public void setRemarkDate(String remarkDate) {
		this.remarkDate = remarkDate;
	}

	public String getRemarkNatureId() {
		return remarkNatureId;
	}

	public void setRemarkNatureId(String remarkNatureId) {
		this.remarkNatureId = remarkNatureId;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}
	
	public static final String escapeHTML(String s) {
		if (StringUtils.isBlank(s)) {
			return s;
		}
		
		StringBuffer sb = new StringBuffer();
		int n = s.length();
		for (int i = 0; i < n; i++) {
			char c = s.charAt(i);
			switch (c) {
	         case '<': sb.append("&lt;"); break;
	         case '>': sb.append("&gt;"); break;
	         case '\n': sb.append("<br/>"); break;
	         case '\r': break;
	         case '&': sb.append("&amp;"); break;
	         case '"': sb.append("&quot;"); break;
	         case '\u00A0': sb.append("&nbsp;"); break;
	         case '\u00A1': sb.append("&iexcl;"); break;
	         case '\u00A2': sb.append("&cent;"); break;
	         case '\u00A3': sb.append("&pound;"); break;
	         case '\u00A4': sb.append("&curren;"); break;
	         case '\u00A6': sb.append("&brvbar;"); break;
	         case '\u00A9': sb.append("&copy;"); break;
	         case '\u00AA': sb.append("&ordf;"); break;
	         case '\u00AB': sb.append("&laquo;"); break;
	         case '\u00AC': sb.append("&not;"); break;
	         case '\u00AF': sb.append("&macr;"); break;
	         case '\u00B0': sb.append("&deg;"); break;
	         case '\u00B1': sb.append("&plusmn;"); break;
	         case '\u00B4': sb.append("&acute;"); break;
	         case '\u00B5': sb.append("&micro;"); break;
	         case '\u00B6': sb.append("&para;"); break;
	         case '\u00B7': sb.append("&middot;"); break;
	         case '\u00B8': sb.append("&cedil;"); break;
	         case '\u00BA': sb.append("&ordm;"); break;
	         case '\u00BC': sb.append("&frac14;"); break;
	         case '\u00BD': sb.append("&frac12;"); break;
	         case '\u00BE': sb.append("&frac34;"); break;
	         case '\u00BF': sb.append("&iquest;"); break;
	         case '\u00C0': sb.append("&Agrave;"); break;
	         case '\u00C1': sb.append("&Aacute;"); break;
	         case '\u00C2': sb.append("&Acirc;"); break;
	         case '\u00C3': sb.append("&Atilde;"); break;
	         case '\u00C4': sb.append("&Auml;"); break;
	         case '\u00C5': sb.append("&Aring;"); break;
	         case '\u00C6': sb.append("&AElig;"); break;
	         case '\u00C7': sb.append("&Ccedil;"); break;
	         case '\u00C8': sb.append("&Egrave;"); break;
	         case '\u00C9': sb.append("&Eacute;"); break;
	         case '\u00CA': sb.append("&Ecirc;"); break;
	         case '\u00CB': sb.append("&Euml;"); break;
	         case '\u00CC': sb.append("&Igrave;"); break;
	         case '\u00CD': sb.append("&Iacute;"); break;
	         case '\u00CE': sb.append("&Icirc;"); break;
	         case '\u00CF': sb.append("&Iuml;"); break;
	         case '\u00D0': sb.append("&ETH;"); break;
	         case '\u00D1': sb.append("&Ntilde;"); break;
	         case '\u00D2': sb.append("&Ograve;"); break;
	         case '\u00D3': sb.append("&Oacute;"); break;
	         case '\u00D4': sb.append("&Ocirc;"); break;
	         case '\u00D5': sb.append("&Otilde;"); break;
	         case '\u00D6': sb.append("&Ouml;"); break;
	         case '\u00D8': sb.append("&Oslash;"); break;
	         case '\u00E0': sb.append("&agrave;"); break;
	         case '\u00E1': sb.append("&aacute;"); break;
	         case '\u00E2': sb.append("&acirc;"); break;
	         case '\u00E3': sb.append("&atilde;"); break;
	         case '\u00E4': sb.append("&auml;"); break;
	         case '\u00E5': sb.append("&aring;"); break;
	         case '\u00E6': sb.append("&aelig;"); break;
	         case '\u00E7': sb.append("&ccedil;"); break;
	         case '\u00E8': sb.append("&egrave;"); break;
	         case '\u00E9': sb.append("&eacute;"); break;
	         case '\u00EA': sb.append("&ecirc;"); break;
	         case '\u00EB': sb.append("&euml;"); break;
	         case '\u00EC': sb.append("&igrave;"); break;
	         case '\u00ED': sb.append("&iacute;"); break;
	         case '\u00EE': sb.append("&icirc;"); break;
	         case '\u00EF': sb.append("&iuml;"); break;
	         case '\u00F0': sb.append("&eth;"); break;
	         case '\u00F1': sb.append("&ntilde;"); break;
	         case '\u00F2': sb.append("&ograve;"); break;
	         case '\u00F3': sb.append("&oacute;"); break;
	         case '\u00F4': sb.append("&ocirc;"); break;
	         case '\u00F5': sb.append("&otilde;"); break;
	         case '\u00F6': sb.append("&ouml;"); break;
	         case '\u00F7': sb.append("&divide;"); break;
	         case '\u00F8': sb.append("&oslash;"); break;
	         case '\u0152': sb.append("&OElig;"); break;
	         case '\u0153': sb.append("&oelig;"); break;
	         case '\u0192': sb.append("&fnof;"); break;
	         case '\u02C6': sb.append("&circ;"); break;
	         case '\u0391': sb.append("&Alpha;"); break;
	         case '\u0392': sb.append("&Beta;"); break;
	         case '\u0393': sb.append("&Gamma;"); break;
	         case '\u0394': sb.append("&Delta;"); break;
	         case '\u0395': sb.append("&Epsilon;"); break;
	         case '\u0397': sb.append("&Eta;"); break;
	         case '\u0399': sb.append("&Iota;"); break;
	         case '\u039A': sb.append("&Kappa;"); break;
	         case '\u039B': sb.append("&Lambda;"); break;
	         case '\u039C': sb.append("&Mu;"); break;
	         case '\u039D': sb.append("&Nu;"); break;
	         case '\u039F': sb.append("&Omicron;"); break;
	         case '\u03A0': sb.append("&Pi;"); break;
	         case '\u03A6': sb.append("&Phi;"); break;
	         case '\u03A7': sb.append("&Chi;"); break;
	         case '\u03A8': sb.append("&Psi;"); break;
	         case '\u03A9': sb.append("&Omega;"); break;
	         case '\u03B1': sb.append("&alpha;"); break;
	         case '\u03B2': sb.append("&beta;"); break;
	         case '\u03B3': sb.append("&gamma;"); break;
	         case '\u03B4': sb.append("&delta;"); break;
	         case '\u03B5': sb.append("&epsilon;"); break;
	         case '\u03B7': sb.append("&eta;"); break;
	         case '\u03B9': sb.append("&iota;"); break;
	         case '\u03BA': sb.append("&kappa;"); break;
	         case '\u03BB': sb.append("&lambda;"); break;
	         case '\u03BC': sb.append("&mu;"); break;
	         case '\u03BD': sb.append("&nu;"); break;
	         case '\u03BF': sb.append("&omicron;"); break;
	         case '\u03C0': sb.append("&pi;"); break;
	         case '\u03C6': sb.append("&phi;"); break;
	         case '\u03C7': sb.append("&chi;"); break;
	         case '\u03C8': sb.append("&psi;"); break;
	         case '\u03C9': sb.append("&omega;"); break;
	         case '\u03D6': sb.append("&piv;"); break;
	         case '\u2002': sb.append("&ensp;"); break;
	         case '\u2003': sb.append("&emsp;"); break;
	         case '\u200E': sb.append("&lrm;"); break;
	         case '\u2013': sb.append("&ndash;"); break;
	         case '\u2014': sb.append("&mdash;"); break;
	         case '\u2018': sb.append("&lsquo;"); break;
	         case '\u201C': sb.append("&ldquo;"); break;
	         case '\u201E': sb.append("&bdquo;"); break;
	         case '\u2020': sb.append("&dagger;"); break;
	         case '\u2021': sb.append("&Dagger;"); break;
	         case '\u2022': sb.append("&bull;"); break;
	         case '\u2026': sb.append("&hellip;"); break;
	         case '\u2030': sb.append("&permil;"); break;
	         case '\u2032': sb.append("&prime;"); break;
	         case '\u2033': sb.append("&Prime;"); break;
	         case '\u2039': sb.append("&lsaquo;"); break;
	         case '\u203E': sb.append("&oline;"); break;
	         case '\u2044': sb.append("&frasl;"); break;
	         case '\u20AC': sb.append("&euro;"); break;
	         case '\u2111': sb.append("&image;"); break;
	         case '\u2135': sb.append("&alefsym;"); break;
	         case '\u2190': sb.append("&larr;"); break;
	         case '\u2193': sb.append("&darr;"); break;
	         case '\u2194': sb.append("&harr;"); break;
	         case '\u21B5': sb.append("&crarr;"); break;
	         case '\u21D0': sb.append("&lArr;"); break;
	         case '\u21D3': sb.append("&dArr;"); break;
	         case '\u21D4': sb.append("&hArr;"); break;
	         case '\u2200': sb.append("&forall;"); break;
	         case '\u2202': sb.append("&part;"); break;
	         case '\u2203': sb.append("&exist;"); break;
	         case '\u2205': sb.append("&empty;"); break;
	         case '\u2207': sb.append("&nabla;"); break;
	         case '\u2208': sb.append("&isin;"); break;
	         case '\u2209': sb.append("&notin;"); break;
	         case '\u220B': sb.append("&ni;"); break;
	         case '\u220F': sb.append("&prod;"); break;
	         case '\u2212': sb.append("&minus;"); break;
	         case '\u2217': sb.append("&lowast;"); break;
	         case '\u221D': sb.append("&prop;"); break;
	         case '\u221E': sb.append("&infin;"); break;
	         case '\u2220': sb.append("&ang;"); break;
	         case '\u2227': sb.append("&and;"); break;
	         case '\u2228': sb.append("&or;"); break;
	         case '\u2229': sb.append("&cap;"); break;
	         case '\u222A': sb.append("&cup;"); break;
	         case '\u222B': sb.append("&int;"); break;
	         case '\u2245': sb.append("&cong;"); break;
	         case '\u2248': sb.append("&asymp;"); break;
	         case '\u2260': sb.append("&ne;"); break;
	         case '\u2261': sb.append("&equiv;"); break;
	         case '\u2264': sb.append("&le;"); break;
	         case '\u2265': sb.append("&ge;"); break;
	         case '\u2284': sb.append("&nsub;"); break;
	         case '\u2295': sb.append("&oplus;"); break;
	         case '\u2297': sb.append("&otimes;"); break;
	         case '\u22A5': sb.append("&perp;"); break;
	         case '\u2308': sb.append("&lceil;"); break;
	         case '\u230A': sb.append("&lfloor;"); break;
	         case '\u2329': sb.append("&lang;"); break;
	         case '\u25CA': sb.append("&loz;"); break;
	         case '\u2663': sb.append("&clubs;"); break;
	         case '\u2665': sb.append("&hearts;"); break;
	         case '\u2666': sb.append("&diams;"); break;
	         // be carefull with this one (non-breaking whitee space)
	         case ' ': sb.append("&nbsp;");break;         
	         
	         default:  sb.append(c); break;
			}
		}
		return sb.toString();
	}

}