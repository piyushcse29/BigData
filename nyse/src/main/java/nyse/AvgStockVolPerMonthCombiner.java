package nyse;

import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Reducer.Context;

public class AvgStockVolPerMonthCombiner extends Reducer<TextPair, LongPair, TextPair, LongPair> {
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
		
		
		
		result.setFirst(new LongWritable(totalVolume));
		result.setSecond(new LongWritable(noOfrecords));
		context.write(key, result);
		
	}
}
