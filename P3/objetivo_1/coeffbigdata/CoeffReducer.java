package oldapi;
import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
public class CoeffReducer extends MapReduceBase implements Reducer<VariablePairWritable, VariableValueWritable, VariablePairWritable, DoubleWritable> {
		DoubleWritable emitValue = new DoubleWritable();

        public void reduce(VariablePairWritable key, Iterator<VariableValueWritable> values, OutputCollector<VariablePairWritable, VariableValueWritable> output, Reporter reporter) throws IOException {
                double x = 0.0d;
			    double y = 0.0d;
			    double xx = 0.0d;
			    double yy = 0.0d;
			    double xy = 0.0d;
			    double n = 0.0d;
                double corr = 0.0d;

                while (values.hasNext()) {
                    VariableValueWritable value = values.next().get();
				    x += value.getI();
				    y += value.getJ();
				    xx += Math.pow(value.getI(), 2.0d);
				    yy += Math.pow(value.getJ(), 2.0d);
				    xy += value.getI() * value.getJ();
				    n += 1.0d;
                }

		        if (0.0d != n){
		            double numerator = x / n;
		            numerator = numerator * y;
		            numerator = xy - numerator;

		            double denom1 = Math.pow(x, 2.0d) / n;
		            denom1 = xx - denom1;

		            double denom2 = Math.pow(y, 2.0d) / n;
		            denom2 = yy - denom2;

		            double denom = denom1 * denom2;
		            denom = Math.sqrt(denom);

		            corr = numerator / denom;
                }

                emitValue.set(corr);
                output.collect(key, emitValue);
        }
}
