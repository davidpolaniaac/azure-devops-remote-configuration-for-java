package com.github.davidpolaniaac.remote.configuration.azure.devops.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.davidpolaniaac.remote.configuration.azure.devops.exception.RemoteConfigurationException;

@RunWith(MockitoJUnitRunner.class)
public class RemoteConfigurationExceptionTest {

	private RemoteConfigurationException remoteConfigurationException;

	@Test
	public void testRemoteConfigurationException() throws Exception {
		String message = "test";
		remoteConfigurationException = new RemoteConfigurationException(message);
		remoteConfigurationException.getMessage();
		assertEquals(remoteConfigurationException.getMessage(), message);
	}

}
