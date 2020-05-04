package GreenArea;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

public class GreenMapper extends MapReduceBase implements Mapper<LongWritable, Text, Text, FloatWritable> {
	//private final static IntWritable one = new IntWritable(1);

	public void map(LongWritable key, Text value, OutputCollector<Text, FloatWritable> output, Reporter reporter) throws IOException {
		
		String valueString = value.toString();
		String[] SingleAreaData = valueString.split(", ");
		FloatWritable percentage= new FloatWritable(Float.parseFloat(SingleAreaData[1]));
		output.collect(new Text(SingleAreaData[0]+"   "+SingleAreaData[2]+"   "+SingleAreaData[3]+"   "+SingleAreaData[4]+"   "+SingleAreaData[5]+"   "+SingleAreaData[6]+"   "+SingleAreaData[7]+"   "+SingleAreaData[8]+"   "+SingleAreaData[9]+"   "+SingleAreaData[10]+"   "+SingleAreaData[11]),percentage);
	}
}
