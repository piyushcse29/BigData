package nyse;

import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FileUtil;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapred.lib.InputSampler;
import org.apache.hadoop.mapred.lib.TotalOrderPartitioner;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class AverageStockVolumePerMonthDriver extends Configured implements Tool {

	public static class MonthPartitioner extends Partitioner<TextPair, LongPair>{
		@Override
		public int getPartition(TextPair key, LongPair value, int numPartitions){
			String tradeMonth = key.getFirst().toString().replace("-", "");
			return new Integer(tradeMonth) % numPartitions;
		}
	}
	
	@Override
	public int run(String[] args) throws Exception {
		Configuration conf = getConf();
		Job job = Job.getInstance(conf);
		job.setJarByClass(getClass());
		
		FileSystem fs = FileSystem.get(URI.create(args[0]), conf);
		System.out.println("Path is "+args[0] + args[1]);
		Path path = new Path(args[0] + args[1]); // args[1] NYSE_201[2-3]
		FileStatus[] status = fs.globStatus(path);
		

		Path[] paths = FileUtil.stat2Paths(status);
		for (Path p : paths) {
			System.out.println(p.toString());
			FileInputFormat.addInputPath(job, p);
		}
		
		
	    job.setInputFormatClass(CombineTextInputFormat.class);
 		CombineTextInputFormat.setMaxInputSplitSize(job, 3200000);
		
		job.setMapperClass(AvgStockVolPerMonthMapper.class);

		job.setMapOutputKeyClass(TextPair.class);
		job.setMapOutputValueClass(LongPair.class);
		
		//job.setPartitionerClass(MonthPartitioner.class);
		//job.setPartitionerClass(TotalOrderPartitioner.class);
		
//		Path inputDir = new Path(args[3]);
//		
//		Path partitionFile = new Path(inputDir, "partitioning");
//		TotalOrderPartitioner.setPartitionFile(job.getConfiguration(), partitionFile);
//		
//		double pcnt = 0.10;
		int numReduceTasks = 10;
//		int numSamples = 10000;
//		int maxSplits = numReduceTasks -1;
//		if(0 >= maxSplits)
//			 maxSplits = Integer.MAX_VALUE;
//		InputSampler.Sampler<TextPair, LongPair> sampler = new InputSampler.RandomSampler<TextPair, LongPair>(pcnt, numSamples);
//		InputSampler.writePartitionFile(job, sampler);

		job.setCombinerClass(AvgStockVolPerMonthCombiner.class);
		job.setReducerClass(AvgStockVolPerMonthReducer.class);
		
		job.setNumReduceTasks(numReduceTasks);
		
		job.setOutputKeyClass(TextPair.class);
		job.setOutputValueClass(LongPair.class);
		
		FileOutputFormat.setOutputPath(job, new Path(args[2]));
		
		
		return job.waitForCompletion(true) ? 0:1;
	}
	
	public static void main(String[] args) throws Exception {
		System.exit(ToolRunner.run(new AverageStockVolumePerMonthDriver(), args));

	}

	

}
