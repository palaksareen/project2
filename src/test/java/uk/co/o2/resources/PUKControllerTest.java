package uk.co.o2.resources;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.ModelAndView;

import uk.co.o2.DynamicProperties;
import uk.co.o2.facade.ModelAndViewFacade;
import uk.co.o2.facade.PUKFacade;
import uk.co.o2.utility.ErrorCode;
import uk.co.o2.utility.Validator;
import uk.co.o2.utility.exception.InvalidMPNException;
import uk.co.o2.utility.exception.NotO2CustomerException;
import uk.co.o2.utility.exception.PUKNotFoundException;
import uk.co.o2.utility.exception.SOAException;


@RunWith(PowerMockRunner.class)
@SpringApplicationConfiguration(classes = Validator.class)
@WebAppConfiguration
@PrepareForTest(DynamicProperties.class) 
public class PUKControllerTest {
	@Mock
	ModelAndViewFacade modelAndView;

	@Mock
	PUKFacade facade;

	@Mock
	Environment env;
	
	@Mock
	HttpServletRequest mockHttpServletRequest;
	
	@Mock
	Request request;
	
	@Mock
	Content content;
	
	@Mock
	DynamicProperties mockDynamicProperty;
	@Test
	public void getPUKTest() throws InvalidMPNException, PUKNotFoundException, SOAException, ClientProtocolException, IOException, NotO2CustomerException {
		MockitoAnnotations.initMocks(this);

		
		PowerMockito.mockStatic(DynamicProperties.class);
		when( DynamicProperties.getProperty("sitekey")).thenReturn("some key");
		
		PUKController pukController =new PUKController();
		pukController.facade=facade;
		pukController.modelAndView=modelAndView;
		String result="no puk found";
		ModelAndView mav=new ModelAndView();
		
		when(facade.getPuk("some mpn")).thenReturn(result);
		when(modelAndView.forSuccessPage(result,"some mpn")).thenReturn(mav);

		ModelAndView actual=pukController.getPUK("some mpn","some captch");
		assertEquals(mav, actual);
		
		InvalidMPNException exception=new InvalidMPNException(ErrorCode.INVALID_MPN.getMessage(), ErrorCode.INVALID_MPN);
		doThrow(exception).when(facade).getPuk("some mpn");
		when(modelAndView.forWelcomePagewithErorr(Arrays.asList(exception.getErrorCode()))).thenReturn(mav);
		actual=pukController.getPUK("some mpn","some captch");
		assertEquals(mav, actual);
		
		PUKNotFoundException pukNotFoundException=new PUKNotFoundException("PUK Not found");
		doThrow(pukNotFoundException).when(facade).getPuk("some mpn");
		when(modelAndView.forWelcomePagewithErorr(Arrays.asList(ErrorCode.PUKNOTFOUND))).thenReturn(mav);
		actual=pukController.getPUK("some mpn",null);
		assertEquals(mav, actual);
		
	}   
}
