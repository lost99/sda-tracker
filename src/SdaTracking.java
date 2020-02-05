package sdaTracking;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class SdaTracking {
	/**
	 * Constructor
	 * Initialize class variables and set the webClient
	 */
	public SdaTracking() {
		System.setProperty("Dhttps.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		wc = new WebClient(BrowserVersion.CHROME);
		wc.getOptions().setDownloadImages(false);
		wc.getOptions().setJavaScriptEnabled(true);
		wc.getOptions().setCssEnabled(false);
		wc.getOptions().setThrowExceptionOnScriptError(false);
	}
	/**
	 * Constructor
	 * Initialize class variables and set the webClient, search for the shipment corresponding to the code
	 * @param code
	 */
	public SdaTracking(String code) {
		System.setProperty("Dhttps.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		this.code = code;
		wc = new WebClient(BrowserVersion.CHROME);
		wc.getOptions().setDownloadImages(false);
		wc.getOptions().setJavaScriptEnabled(true);
		wc.getOptions().setCssEnabled(false);
		wc.getOptions().setThrowExceptionOnScriptError(false);
		try {
			HtmlPage page = wc.getPage("https://www.sda.it/wps/portal/Servizi_online/ricerca_spedizioni?locale=it&tracing.letteraVettura="+this.code);
			wc.waitForBackgroundJavaScript(1000);
			list = page.getElementsByTagName( "span" );
		} catch (Exception e) {
			e.printStackTrace();
		}
		consegnata=false;
	}
	/**
	 * Change the shipment's code and restart the search
	 * @param code
	 */
	public void changeCode(String code) {
		this.code = code;
		try {
			HtmlPage page = wc.getPage("https://www.sda.it/wps/portal/Servizi_online/ricerca_spedizioni?locale=it&tracing.letteraVettura="+this.code);
			wc.waitForBackgroundJavaScript(1000);
			list = page.getElementsByTagName( "span" );
		} catch (Exception e) {
			e.printStackTrace();
		}
		consegnata=false;
	}
	/**
	 * Return the status of shipment
	 * @return result
	 */
	
	public String getStatus() {
		String result="";
			for( DomElement domElement : list )
		    {
		        if( domElement.getAttribute( "data-bind" ).contains( "text: descrizioneTracing" ) )
		        {
		          result = domElement.getTextContent();
		        }
		    }
			if(result.contains("consegnata")) {
				consegnata=true;
			}
		return result;
	}
	/**
	 * Return the person who signed for the delivery(use only after getStatus())
	 * @return result
	 */
	public String getSign() {
		String result="";
		if(consegnata) {
			for( DomElement domElement : list )
		    {
		        if( domElement.getAttribute( "id" ).contains( "span.firma" ) )
		        {
		          result = domElement.getTextContent();
		        }
		    }
		}
		return result;
	}
	/**
	 * Return the actual shipment's code
	 * @return this.code
	 */
	public String getCode() {
		return this.code;
	}
	
	//Variables
	boolean consegnata;
	DomNodeList< DomElement > list;
	private String code; 
	private WebClient wc;
}
