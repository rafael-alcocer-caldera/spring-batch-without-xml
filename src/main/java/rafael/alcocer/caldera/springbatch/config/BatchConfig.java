/**
 * Copyright [2017] [RAFAEL ALCOCER CALDERA]
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * 	http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package rafael.alcocer.caldera.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import rafael.alcocer.caldera.springbatch.step.Processor;
import rafael.alcocer.caldera.springbatch.step.Reader;
import rafael.alcocer.caldera.springbatch.step.Writer;

/**
 * This class replaces the XML configuration file.
 * 
 * @author Rafael Alcocer Caldera
 * @version 1.0
 *
 */
@Configuration
public class BatchConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("${chunk-size}")
    private int chunkSize;

    @Bean
    public Job job() {
	return jobBuilderFactory.get("job").incrementer(new RunIdIncrementer()).flow(step1()).end().build();
    }

    @Bean
    public Step step1() {
	return stepBuilderFactory.get("step1").<String, String>chunk(chunkSize).reader(new Reader())
		.processor(new Processor()).writer(new Writer()).build();
    }
}