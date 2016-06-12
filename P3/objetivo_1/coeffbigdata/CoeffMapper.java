package oldapi;
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
public class CoeffMapper extends MapReduceBase implements Mapper<LongWritable, Text, VariablePairWritable, VariableValueWritable> {
        private static final int MISSING = 9999;
		VariablePairWritable emitKey = new VariablePairWritable();
		VariableValueWritable emitValue = new VariableValueWritable();

        public void map(LongWritable key, Text value, OutputCollector<VariablePairWritable, VariableValueWritable> output, Reporter reporter) throws IOException {
                String line = value.toString();
                String[] parts = line.split(",");
                double[] pairs = toDouble(Arrays.copyOf(parts, parts.length-1));
            
                for (int i = 0; i < pairs.length; i++) {
				    for (int j = i; j < pairs.length; j++) {
					    emitKey.setI(i);
					    emitKey.setJ(j);
					    emitValue.setI(pairs[i]);
					    emitValue.setJ(pairs[j]);
					    output.collect(emitKey, emitValue);
				    }
			    }
        }

		public double[] toDouble(String[] tokens) {
			double[] myArray = new double[tokens.length];
			for (int i = 0; i < tokens.length; i++) {
				if (i == 24) {
					myArray[i] = Double.parseDouble("0.1");
				} else {
					myArray[i] = Double.parseDouble(tokens[i]);
				}
			}

			return myArray;
		}
}

