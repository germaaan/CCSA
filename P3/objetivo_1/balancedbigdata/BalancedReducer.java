package oldapi;
import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
public class BalancedReducer extends MapReduceBase implements Reducer<Text, DoubleWritable, Text, DoubleWritable> {
	

	public void reduce(Text key, Iterator<DoubleWritable> values, OutputCollector<Text, DoubleWritable> output, Reporter reporter) throws IOException {
		int count_0 = 0;
		int count_1 = 0;
		Double ratio;
				
		while (values.hasNext()) {
			if(values.next().get() == 0){
				count_0++;
			} else {
				count_1++;
			}
		}

		if (count_0 > count_1){
			ratio = (double) (count_0 / count_1);
		} else {
			ratio = (double) (count_1 / count_0);
		}

                output.collect(new Text("Ratio:"), new DoubleWritable(ratio));
	}
}

