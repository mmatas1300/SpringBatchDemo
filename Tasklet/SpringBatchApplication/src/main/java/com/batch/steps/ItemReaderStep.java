package com.batch.steps;

import com.batch.entities.Person;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ItemReaderStep implements Tasklet {

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {//StepContribution reporta estado, ChunckContext accede al contexto de ejecución
        log.info("---------ItemReader--------------");

        Reader reader = new FileReader(resourceLoader.getResource("classpath:files/destination/persons.csv").getFile()); //leer el csv

        CSVParser parser = new CSVParserBuilder().withSeparator(',').build();

        CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parser).withSkipLines(1).build();

        List<Person> persons = new ArrayList<>();
        String[] actualLine;

        //Leyendo el doc y creando personas en una lista
        while((actualLine = csvReader.readNext())!=null){
            Person person = new Person();
            person.setName(actualLine[0]);
            person.setLastName(actualLine[1]);
            person.setAge(Integer.parseInt(actualLine[2]));
            persons.add(person);
        }
        csvReader.close();
        reader.close();

        log.info("---------Fin ItemReader--------------");

        //Enviando la lista al contexto
        chunkContext.getStepContext().getStepExecution().getJobExecution().getExecutionContext().put("PersonList",persons);
        return RepeatStatus.FINISHED;
    }
}
