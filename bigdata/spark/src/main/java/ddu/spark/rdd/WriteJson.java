package ddu.spark.rdd;

import org.apache.spark.api.java.function.FlatMapFunction;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by lideyou on 16/5/29.
 */
public class WriteJson implements FlatMapFunction<Iterator<Person>, String> {
    @Override
    public Iterator<String> call(Iterator<Person> personIterator) throws Exception {
        ArrayList<String> text = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        while (personIterator.hasNext()) {
            Person person = new Person();
            text.add(mapper.writeValueAsString(person));
        }

        return (Iterator<String>) text;
    }
}
