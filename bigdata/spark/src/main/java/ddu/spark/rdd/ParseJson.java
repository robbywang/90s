package ddu.spark.rdd;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by lideyou on 16/5/29.
 */
public class ParseJson implements FlatMapFunction<Iterator<String>,Person> {
    @Override
    public Iterator<Person> call(Iterator<String> stringIterator) throws Exception {
        ArrayList<Person> people = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();

        while (stringIterator.hasNext()){
            String line = stringIterator.next();

            try {
                people.add(mapper.readValue(line,Person.class));
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }
        return (Iterator<Person>) people;
    }
}
