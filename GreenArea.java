package hadoop; 

import java.util.*; 

import java.io.IOException; 
import java.io.IOException; 

import org.apache.hadoop.fs.Path; 
import org.apache.hadoop.conf.*; 
import org.apache.hadoop.io.*; 
import org.apache.hadoop.mapred.*; 
import org.apache.hadoop.util.*; 

public class ProcessUnits {
   //Mapper class 
   public static class E_EMapper extends MapReduceBase implements 
   Mapper<String,
   Float,
   String,
   Float>       
   {
      //Map function 
      public void map(String key, Float value, 
      OutputCollector<String, Float> output,   
      
      Reporter reporter) throws IOException { 
         String line = value.toString(); 
         String lasttoken = null; 
         StringTokenizer s = new StringTokenizer(line,", "); 
         String image_name = s.nextToken(); 
         
         /*
         while(s.hasMoreTokens()) {
            lasttoken = s.nextToken();
         }
         */

         String percentage_green = s.nextToken(); 
         int percentage_green = Integer.parseInt(percentage_green); //
         output.collect(new String(image_name), new Float(percentage_green)); 
      } 
   }
   
   //Reducer class 
   public static class E_EReduce extends MapReduceBase implements Reducer< Text, IntWritable, Text, IntWritable > {
   
      //Reduce function 
      public void reduce( String key, Iterator <Float> values, 
      OutputCollector<String, Float> output, Reporter reporter) throws IOException { 
         int min_percentage = 0.75; 
         int val = Integer.MIN_VALUE; 
            
         while (values.hasNext()) { 
            if((val = values.next().get())>min_percentage) { 
               output.collect(key, new Float(val)); 
            } 
         }
      } 
   }

   //Main function 
   public static void main(String args[])throws Exception { 
      JobConf conf = new JobConf(GreenArea.class); 
      
      conf.setJobName("min_greenpercentage"); 
      conf.setOutputKeyClass(Text.class);
      conf.setOutputValueClass(IntWritable.class); 
      conf.setMapperClass(E_EMapper.class); 
      conf.setCombinerClass(E_EReduce.class); 
      conf.setReducerClass(E_EReduce.class); 
      conf.setInputFormat(TextInputFormat.class); 
      conf.setOutputFormat(TextOutputFormat.class); 
      
      FileInputFormat.setInputPaths(conf, new Path(args[0])); 
      FileOutputFormat.setOutputPath(conf, new Path(args[1])); 
      
      JobClient.runJob(conf); 
   } 
} 