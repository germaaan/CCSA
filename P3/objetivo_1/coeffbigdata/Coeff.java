package oldapi;
import java.io.IOException;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
public class Coeff {
	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.err.println("Usage: CorrelationCoeff <input path> <output path>");
			System.exit(-1);
		}
		JobConf conf = new JobConf(Coeff.class);
		conf.setJobName("Correlation Coefficient");
		FileInputFormat.addInputPath(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));
		conf.setMapperClass(CoeffMapper.class);
		conf.setReducerClass(CoeffReducer.class);
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(DoubleWritable.class);
		JobClient.runJob(conf);
	}
}

