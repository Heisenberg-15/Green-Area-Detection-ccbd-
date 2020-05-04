package GreenArea;

import java.io.IOException;
import java.util.*;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.*;

public class GreenReducer extends MapReduceBase implements Reducer<Text, FloatWritable, Text, FloatWritable> {

	public void reduce(Text t_key,Iterator<FloatWritable> values, OutputCollector<Text,FloatWritable> output, Reporter reporter) throws IOException {
		Text key = t_key;
                FloatWritable min = new FloatWritable(Float.parseFloat("0.75"));
		//while (values.hasNext()) {
			// replace type of value with the actual type of our value
			//IntWritable value = (IntWritable) values.next();
			//frequencyForCountry += value.get();
			
		//}
		FloatWritable value=(FloatWritable) values.next();
		FloatWritable fin=new FloatWritable(value.get());
		if(fin.compareTo(min)>0){
                    output.collect(key,fin);
               }
	}
}
