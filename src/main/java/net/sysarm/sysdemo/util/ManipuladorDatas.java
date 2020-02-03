package net.sysarm.sysdemo.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Classe que manipula transformações de datas
 */
public class ManipuladorDatas {	
	/**
	 * Retorna a data e tempo corrente
	 * @param format formato da data exemplo "yyyy/MM/dd HH:mm:ss"
	 * @return String contento da data corrente exemplo 2016/11/16 12:08:43
	 */
	public static String getCurrentDataTime(String padrao) {
		DateFormat dateFormat = new SimpleDateFormat(padrao);
		Date date = new Date();
		
		return dateFormat.format(date); 
	}

	public static String getFormattedDataTime(String padrao, Date date) {
		DateFormat dateFormat = new SimpleDateFormat(padrao);
		
		return dateFormat.format(date); 
	}

	
	public static Date getDate() {
		return (new Date()); // NOPMD by armandosoaressousa on 1/30/20 5:49 PM
	}
	
	public boolean dateIsBetweenDates(Date d, Date min, Date max) {
		return d.compareTo(min) >= 0 && d.compareTo(max) <= 0;
	}
	
}
