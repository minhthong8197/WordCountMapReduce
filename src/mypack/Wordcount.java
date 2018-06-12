package mypack;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.jasper.tagplugins.jstl.core.ForEach;

public class Wordcount {
	public static void Config(ArrayList<String> input, String output) throws Exception {
		Configuration conf = new Configuration();
		Job job = new Job(conf, "wordcount");
		job.setJarByClass(Wordcount.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		job.setMapperClass(Map.class);
		job.setReducerClass(Reduce.class);
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		job.setNumReduceTasks(1);

		for (int i = 0; i < input.size(); i++) {
			MultipleInputs.addInputPath(job, new Path(input.get(i)), TextInputFormat.class, Map.class);
		}
		FileOutputFormat.setOutputPath(job, new Path(output));

		boolean success = job.waitForCompletion(true);
		if (success)
			System.out.println("Đã chạy xong");
		else
			System.out.println("Có lỗi");
	}
}
