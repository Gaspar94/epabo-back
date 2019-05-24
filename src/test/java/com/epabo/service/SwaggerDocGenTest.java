/**
 * 
 */
package com.epabo.service;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.epabo.EpaboApp;

import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;

/**
 * @author agustin
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EpaboApp.class)
public class SwaggerDocGenTest {
	
	
	Path localSwaggerFile = Paths.get("/home/agustin/EPABO/swagger.yaml");
	Path outputDirectory = Paths.get("/home/agustin/EPABO/asciidoc");
	
	@Test
	public void generateDoc() {
		Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder() 
		        .withMarkupLanguage(MarkupLanguage.MARKDOWN) 
		        .withOutputLanguage(Language.EN) 
		        .withPathsGroupedBy(GroupBy.TAGS) 
		        .build(); 

		Swagger2MarkupConverter converter = Swagger2MarkupConverter.from(localSwaggerFile)
		        .withConfig(config) 
		        .build();
		converter.from(localSwaggerFile).build().toFolder(outputDirectory);
	}

}
