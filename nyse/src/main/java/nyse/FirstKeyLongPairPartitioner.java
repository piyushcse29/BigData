package nyse;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

public class FirstKeyLongPairPartitioner extends Partitioner<LongPairPrimitive, Text>{

	@Override
	public int getPartition(LongPairPrimitive key, Text value, int numPartitions) {
		long partValue = new Long(new Long(key.getFirst()).toString().substring(0, 6)).longValue();

		return (int) partValue % numPartitions;
	}

}
