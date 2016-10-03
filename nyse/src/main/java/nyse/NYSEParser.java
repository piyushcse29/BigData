package nyse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NYSEParser {
	private String stockTicker;
	private String tradeDate;
	private Float  openPrice;
	private Float  highPrice;
	private Float  lowPrice;
	private Float closePrice;
	private Long volume;
	
	public void parse(String record){
	  String[] fields = record.split(",");
	  stockTicker = fields[0];
	  tradeDate = fields[1];
	  openPrice = new Float(fields[2]);
	  highPrice = new Float(fields[3]);
	  lowPrice = new Float(fields[4]);
	  closePrice = new Float(fields[5]);
	  volume= new Long(fields[6]);
	}
	
	
	public String getTradeMonth(){
		SimpleDateFormat origTradeDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat tgtTradeMonthFormat = new SimpleDateFormat("yyyy-MM");
		Date origDate = new Date();
		try{
			origDate = origTradeDateFormat.parse(this.tradeDate);
			System.out.println(tgtTradeMonthFormat.format(origDate));
		}catch(ParseException e){
			e.printStackTrace();
		}
		String tgtTradeMonth = tgtTradeMonthFormat.format(origDate);
		return tgtTradeMonth;
	}
	
	public Long getTradeDateNumeric() {
		SimpleDateFormat origTradeDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
		SimpleDateFormat tgtTradeMonthFormat = new SimpleDateFormat("yyyyMMdd");

		Date origDate = new Date();
		try {
			origDate = origTradeDateFormat.parse(this.tradeDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Long tgtTradeMonth = new Long(tgtTradeMonthFormat.format(origDate));
		return tgtTradeMonth;
	}

	public String getStockTicker() {
		return stockTicker;
	}
	public void setStockTicker(String stockTicker) {
		this.stockTicker = stockTicker;
	}
	public String getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}
	public Float getOpenPrice() {
		return openPrice;
	}
	public void setOpenPrice(Float openPrice) {
		this.openPrice = openPrice;
	}
	public Float getHighPrice() {
		return highPrice;
	}
	public Float getLowPrice() {
		return lowPrice;
	}

	public void setLowPrice(Float lowPrice) {
		this.lowPrice = lowPrice;
	}

	public void setHighPrice(Float highPrice) {
		this.highPrice = highPrice;
	}
	public Float getClosePrice() {
		return closePrice;
	}
	public void setClosePrice(Float closePrice) {
		this.closePrice = closePrice;
	}
	public Long getVolume() {
		return volume;
	}
	public void setVolume(Long volume) {
		this.volume = volume;
	}
	
	

}
