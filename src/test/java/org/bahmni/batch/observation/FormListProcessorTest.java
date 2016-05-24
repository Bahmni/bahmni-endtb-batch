package org.bahmni.batch.observation;

import org.bahmni.batch.form.BahmniFormFactory;
import org.bahmni.batch.form.domain.BahmniForm;
import org.bahmni.batch.form.domain.ObsService;
import org.bahmni.batch.observation.domain.Concept;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class FormListProcessorTest {

	@Mock
	private BahmniFormFactory bahmniFormFactory;

	@Mock
	private ObsService obsService;

	private FormListProcessor formListProcessor;

	@Before
	public void setup(){
		initMocks(this);
		formListProcessor = new FormListProcessor();
		formListProcessor.setObsService(obsService);
		formListProcessor.setBahmniFormFactory(bahmniFormFactory);

	}

	@Test
	public void shouldRetrieveAllForms(){
		Concept conceptA = new Concept(1, "a", 1);
		Concept conceptB = new Concept(1, "Bacteriology", 1);

		List<Concept> allForms = new ArrayList<>();
		allForms.add(conceptA);
		when(obsService.getChildConcepts(FormListProcessor.ALL_FORMS)).thenReturn(allForms);
		when(obsService.getConceptsByNames(FormListProcessor.BACTERIOLOGY_CONCEPT_SET)).thenReturn(Arrays.asList(conceptB));

		BahmniForm a11 = new BahmniFormBuilder().withName("a11").build();
		BahmniForm a12 = new BahmniFormBuilder().withName("a12").build();
		BahmniForm a13 = new BahmniFormBuilder().withName("a13").build();


		BahmniForm b11 = new BahmniFormBuilder().withName("b11").build();
		BahmniForm b12 = new BahmniFormBuilder().withName("b12").build();
		BahmniForm b13 = new BahmniFormBuilder().withName("b13").build();

		BahmniForm a1 = new BahmniFormBuilder().withName("a1").withChild(a11).withChild(a12).withChild(a13).build();
		BahmniForm b1 = new BahmniFormBuilder().withName("b1").withChild(b11).withChild(b12).withChild(b13).build();

		BahmniForm a = new BahmniFormBuilder().withName("a").withChild(a1).withChild(b1).build();

		when(bahmniFormFactory.createForm(conceptA,null)).thenReturn(a);

		List<BahmniForm> expected = Arrays.asList(a,a1,b1,a11,a12,a13,b11,b12,b13);


		List<BahmniForm> actual= formListProcessor.retrieveAllForms();

		assertEquals(expected,actual);

	}

}
