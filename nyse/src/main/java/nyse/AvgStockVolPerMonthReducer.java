package nyse;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;

public class AvgStockVolPerMonthReducer extends Reducer<TextPair, LongPair, TextPair, LongPair> {

	private Long totalVolume = new Long(0);
	private Long noOfrecords = new Long(0);
	private long avgVolume = new Long(0);
	private static LongPair result = new LongPair();

	public void reduce(TextPair key, Iterable<LongPair> values, Context context) throws IOException, InterruptedException{
		totalVolume = new Long(0);
		noOfrecords = new Long(0);
		
		for(LongPair value : values){
			totalVolume += value.getFirst().get();
			noOfrecords += value.getSecond().get();
		}
		
		avgVolume  = totalVolume / noOfrecords;
		
		result.setFirst(new LongWritable(avgVolume));
		result.setSecond(new LongWritable(noOfrecords));
		context.write(key, result);
		
	}
}
