package dev;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import posl.engine.Interpreter;
import posl.engine.core.Context;
import posl.engine.error.PoslException;
import posl.engine.provider.PoslProvider;

public class BirthdayParadox {

	private Context context;
	private String filename = "./sample_scripts/birthday_paradox.po";
	
	
	@Before
	public void setUp() throws Exception {
		context = PoslProvider.getContext("posl.lang.core");
	}

	@Test
	public void test() throws PoslException {
		try {
			assertEquals(23D, Interpreter.process(context, new File(filename)));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
